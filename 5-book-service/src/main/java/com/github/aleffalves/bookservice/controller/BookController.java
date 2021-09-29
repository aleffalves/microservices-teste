package com.github.aleffalves.bookservice.controller;

import com.github.aleffalves.bookservice.model.Book;
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

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        Book book = bookRepository.getById(id);
        if (book == null) throw new RuntimeException("Book not Found");

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);


        ResponseEntity<Cambio> response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
                Cambio.class,
                params);
        Cambio cambio = response.getBody();

        String property = environment.getProperty("local.server.port");
        book.setEnvironment(property);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }
}
