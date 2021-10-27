package com.github.aleffalves.bookservice.controller;

import com.github.aleffalves.bookservice.model.Book;
import com.github.aleffalves.bookservice.proxy.CambioProxy;
import com.github.aleffalves.bookservice.repository.BookRepository;
import com.github.aleffalves.bookservice.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private Environment environment;
    @Autowired
    private CambioProxy proxy;

    @Operation(summary = "Find a specific book by your ID")
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        Book book = bookRepository.getById(id);
        if (book == null) throw new RuntimeException("Book not Found");


        Cambio cambio = proxy.getCambio(book.getPrice(),"USD" ,currency);

        String property = environment.getProperty("local.server.port");
        book.setEnvironment("Book port" + property + " Cambio Port" + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());

        return book;
    }
}
