package com.polarbookshop.catalogservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@ToString
public class Book {
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

    @JsonCreator
    public Book(@JsonProperty(value = "isbn",   required = true) String isbn,
                @JsonProperty(value = "title",   required = true) String title,
                @JsonProperty(value = "author",   required = true)String author,
                @JsonProperty(value = "price",   required = true)BigDecimal price) {

        if( isbn == null || title == null || author == null || price == null ) {
            throw new IllegalArgumentException("모든 입력값이 빈값이면 안됩니다.");
        }

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
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
