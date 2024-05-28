package com.chukwuemeka.simple_store.services;

import com.chukwuemeka.simple_store.dtos.Bookdto;
import com.chukwuemeka.simple_store.models.Book;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {

ResponseEntity<ApiResponse> addBook(Bookdto bookdto);

ResponseEntity<ApiResponse> updateBookDetails(String title,Bookdto bookdto);
ResponseEntity<ApiResponse> deleteBook(String title);
List<Book> viewAvailableBooks();

}
