package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.math.BigDecimal;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        Book book = new Book("1234567890", "Title", "Author", new BigDecimal("9.90"), "Publisher");
        JsonContent<Book> jsonContent = json.write(book);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.isbn").isEqualTo(book.getIsbn());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.title").isEqualTo(book.getTitle());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.author").isEqualTo(book.getAuthor());
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.price").isEqualTo(book.getPrice().doubleValue());
    }

    @Test
    void testDeserialize() throws Exception {
        String content = """
                {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90,
                "publisher": "Publisher"    
                }
                """;
        Assertions.assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book("1234567890", "Title", "Author", new BigDecimal("9.90"), "Publisher"));
    }
}

