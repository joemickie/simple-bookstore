package com.chukwuemeka.simple_store.repositories;

import com.chukwuemeka.simple_store.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainingIgnoreCase(String title);

    Optional<Book> findByTitle(String title);

    List<Book> findByAvailableTrue();
}
