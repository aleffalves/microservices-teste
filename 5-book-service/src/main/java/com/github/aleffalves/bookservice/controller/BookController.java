package com.github.aleffalves.bookservice.controller;

import com.github.aleffalves.bookservice.model.Book;
import com.github.aleffalves.bookservice.proxy.CambioProxy;
import com.github.aleffalves.bookservice.repository.BookRepository;
import com.github.aleffalves.bookservice.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private Environment environment;
    @Autowired
    private CambioProxy proxy;

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        Book book = bookRepository.getById(id);
        if (book == null) throw new RuntimeException("Book not Found");


        Cambio cambio = proxy.getCambio(book.getPrice(),"USD" ,currency);

        String property = environment.getProperty("local.server.port");
        book.setEnvironment(property);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }
}
