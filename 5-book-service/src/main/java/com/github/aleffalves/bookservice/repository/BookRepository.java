package com.github.aleffalves.bookservice.repository;

import com.github.aleffalves.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
