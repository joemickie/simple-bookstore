package com.chukwuemeka.simple_store.controllers;

import com.chukwuemeka.simple_store.dtos.Bookdto;
import com.chukwuemeka.simple_store.models.Book;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping("/addBook")
    public ResponseEntity<ApiResponse> addBook(@RequestBody Bookdto bookdto){
        return bookService.addBook(bookdto);
    }
    @PutMapping ("/updateBookDetails")
    public ResponseEntity<ApiResponse> updateBookDetails(@ RequestBody Bookdto bookdto, @Param("title") String title){
        return bookService.updateBookDetails(title,bookdto);
    }
    @DeleteMapping("/deleteBook")
    public ResponseEntity<ApiResponse> deleteBook(@Param("title") String title){
        return bookService.deleteBook(title);
    }

    @GetMapping("/viewAvailableBooks")
    public List<Book> viewAvailableBooks(){
        return bookService.viewAvailableBooks();
    }
}
