package com.polarbookshop.catalogservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Getter
@ToString
@Table("book")
public class Book {

    @Id
    private final Long id;

    @Version
    private final int version;

    @NotBlank(message = "The Book ISBN must be defined.")
    @Pattern(
            regexp = "^([0-9]{10}|[0-9]{13})$",
            message = "The ISBN format must be valid"
    )
    private final String isbn;

    @NotBlank(message = "The Book title must be defined.")
    private final String title;

    @NotBlank(message = "The Book author must be defined.")
    private final String author;

    @NotNull(message = "The Book price must be defined.")
    @Positive(
            message = "The Book price must be greater than zero."
    )
    private final BigDecimal price;

    private final String publisher;

    @CreatedDate
    private final Instant createdDate;

    @LastModifiedDate
    private final Instant lastModifiedDate;

    public static Book of(
            String isbn, String title, String author, BigDecimal price, String publisher
    ) {
        return new Book(null,0, isbn, title, author, price, publisher, null, null);
    }

    // 데이터 로더용 생성자 (id, version 자동 생성)
    public Book(String isbn, String title, String author, BigDecimal price, String publisher) {
        this(null, 0, isbn, title, author, price, publisher, null, null);
    }

    // REST API/전체 필드용 생성자
    @JsonCreator
    @PersistenceCreator
    public Book(@JsonProperty(value = "id", required = false) Long id,
                @JsonProperty(value = "version", required = false) Integer version,
                @JsonProperty(value = "isbn", required = true) String isbn,
                @JsonProperty(value = "title", required = true) String title,
                @JsonProperty(value = "author", required = true) String author,
                @JsonProperty(value = "price", required = true) BigDecimal price,
                @JsonProperty(value = "publisher", required = true) String publisher,
                @JsonProperty(value = "createdDate", required = false) Instant createdDate,
                @JsonProperty(value = "lastModifiedDate", required = false) Instant lastModifiedDate) {

        if(isbn == null || title == null || author == null || price == null) {
            throw new IllegalArgumentException("모든 입력값이 빈값이면 안됩니다.");
        }
        this.id = id;
        this.version = version != null ? version : 0;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, price);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Book other = (Book) obj;

        return Objects.equals(isbn, other.isbn)
                && Objects.equals(title, other.title)
                && Objects.equals(author, other.author)
                && Objects.equals(price, other.price);
    }


}
