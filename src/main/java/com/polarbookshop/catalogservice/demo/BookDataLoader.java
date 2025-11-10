package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("testdata")
@RequiredArgsConstructor
public class BookDataLoader {

    private final BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        Book book1 = new Book("1234567891", "Northern Lights", "Lyra Silverstar", new BigDecimal("9.90"));
        Book book2 = new Book("1234567892", "Polar Journey", "Iorek Polarson", new BigDecimal("12.90"));
        bookRepository.saveAll(List.of(book1, book2));
    }
}
