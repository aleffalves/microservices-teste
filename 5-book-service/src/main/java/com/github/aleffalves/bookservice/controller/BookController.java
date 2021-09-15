package com.github.aleffalves.bookservice.controller;

import com.github.aleffalves.bookservice.model.Book;
import com.github.aleffalves.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("book-service")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private Environment environment;

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        Book book = bookRepository.getById(id);
        if (book == null) throw new RuntimeException("Book not Found");

        String property = environment.getProperty("local.server.port");
        book.setEnvironment(property);

        return book;
    }
}
