package com.chukwuemeka.simple_store.controllers;

import com.chukwuemeka.simple_store.dtos.Bookdto;
import com.chukwuemeka.simple_store.models.Book;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Bookdto bookdto;
    private Book book;

    @BeforeEach
    public void setUp() {
        bookdto = new Bookdto("The Alchemist", "Paulo Coelho", 10.99,true, 5);
        book = new Book();
        book.setTitle("The Alchemist");
        book.setAuthor("Paulo Coelho");
        book.setPrice(10.99);
        book.setAvailable(true);
        book.setQuantity(5);
    }

    @Test
    public void testAddBook_Success() {
        ApiResponse<String> apiResponse = new ApiResponse<>("Book added", HttpStatus.OK);
        when(bookService.addBook(any(Bookdto.class))).thenReturn(ResponseEntity.ok(apiResponse));

        ResponseEntity<ApiResponse> response = bookController.addBook(bookdto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book added", response.getBody().getMessage());
        verify(bookService, times(1)).addBook(any(Bookdto.class));
    }

    @Test
    public void testUpdateBookDetails_Success() {
        ApiResponse<String> apiResponse = new ApiResponse<>("Book Updated", HttpStatus.OK);
        when(bookService.updateBookDetails(eq(bookdto.title()), any(Bookdto.class))).thenReturn(ResponseEntity.ok(apiResponse));

        ResponseEntity<ApiResponse> response = bookController.updateBookDetails(bookdto, bookdto.title());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Updated", response.getBody().getMessage());
        verify(bookService, times(1)).updateBookDetails(eq(bookdto.title()), any(Bookdto.class));
    }

    @Test
    public void testDeleteBook_Success() {
        ApiResponse<String> apiResponse = new ApiResponse<>("Book deleted", HttpStatus.OK);
        when(bookService.deleteBook(eq(bookdto.title()))).thenReturn(ResponseEntity.ok(apiResponse));

        ResponseEntity<ApiResponse> response = bookController.deleteBook(bookdto.title());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted", response.getBody().getMessage());
        verify(bookService, times(1)).deleteBook(eq(bookdto.title()));
    }

    @Test
    public void testViewAvailableBooks() {
        when(bookService.viewAvailableBooks()).thenReturn(Collections.singletonList(book));

        List<Book> availableBooks = bookController.viewAvailableBooks();

        assertEquals(1, availableBooks.size());
        assertEquals(book, availableBooks.get(0));
        verify(bookService, times(1)).viewAvailableBooks();
    }
}
