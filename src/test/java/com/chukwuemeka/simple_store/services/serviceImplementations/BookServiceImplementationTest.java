package com.chukwuemeka.simple_store.services.serviceImplementations;

import com.chukwuemeka.simple_store.dtos.Bookdto;
import com.chukwuemeka.simple_store.exceptions.SimpleBookstoreException;
import com.chukwuemeka.simple_store.models.Book;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.repositories.BookRepository;
import com.chukwuemeka.simple_store.repositories.UserRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplementationTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookServiceImplementation bookService;

    private Bookdto bookdto;
    private Book book;

    @BeforeEach
    public void setUp() {
        bookdto = new Bookdto("The Alchemist", "Paulo Coelho", 10.99, true, 5);
        book = new Book();
        book.setTitle("The Alchemist");
        book.setAuthor("Paulo Coelho");
        book.setPrice(10.99);
        book.setAvailable(true);
        book.setQuantity(5);
    }

    @Test
    public void testAddBook_Success() {
        when(bookRepository.findByTitle(bookdto.title())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        ResponseEntity<ApiResponse> response = bookService.addBook(bookdto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book added", response.getBody().getMessage());
        verify(bookRepository, times(1)).findByTitle(bookdto.title());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testAddBook_BookAlreadyExists() {
        when(bookRepository.findByTitle(bookdto.title())).thenReturn(Optional.of(book));

        ResponseEntity<ApiResponse> response = bookService.addBook(bookdto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Already exist and quantity increased", response.getBody().getMessage());
        verify(bookRepository, times(1)).findByTitle(bookdto.title());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBookDetails_Success() {
        when(bookRepository.findByTitleContainingIgnoreCase(bookdto.title())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        ResponseEntity<ApiResponse> response = bookService.updateBookDetails(bookdto.title(), bookdto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book Updated", response.getBody().getMessage());

        // Verify that the existing book instance is updated
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase(bookdto.title());
        verify(bookRepository, times(1)).save(book);

        // Verify that the book's properties have been updated correctly
        assertEquals(bookdto.title(), book.getTitle());
        assertEquals(bookdto.author(), book.getAuthor());
        assertEquals(bookdto.price(), book.getPrice());
        assertEquals(bookdto.quantity(), book.getQuantity());
        assertTrue(book.isAvailable());
    }

    @Test
    public void testUpdateBookDetails_BookDoesNotExist() {
        when(bookRepository.findByTitleContainingIgnoreCase(bookdto.title())).thenReturn(Optional.empty());

        assertThrows(SimpleBookstoreException.class, () -> bookService.updateBookDetails(bookdto.title(), bookdto));

        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase(bookdto.title());
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    public void testDeleteBook_Success() {
        when(bookRepository.findByTitle(bookdto.title())).thenReturn(Optional.of(book));

        ResponseEntity<ApiResponse> response = bookService.deleteBook(bookdto.title());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted", response.getBody().getMessage());
        verify(bookRepository, times(1)).findByTitle(bookdto.title());
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    public void testDeleteBook_BookDoesNotExist() {
        when(bookRepository.findByTitle(bookdto.title())).thenReturn(Optional.empty());

        assertThrows(SimpleBookstoreException.class, () -> bookService.deleteBook(bookdto.title()));

        verify(bookRepository, times(1)).findByTitle(bookdto.title());
        verify(bookRepository, times(0)).delete(any(Book.class));
    }

    @Test
    public void testDeleteBook_BookUnavailable() {
        book.setAvailable(false);
        when(bookRepository.findByTitle(bookdto.title())).thenReturn(Optional.of(book));

        assertThrows(SimpleBookstoreException.class, () -> bookService.deleteBook(bookdto.title()));

        verify(bookRepository, times(1)).findByTitle(bookdto.title());
        verify(bookRepository, times(0)).delete(book);
    }

    @Test
    public void testViewAvailableBooks() {
        when(bookRepository.findByAvailableTrue()).thenReturn(Collections.singletonList(book));

        List<Book> availableBooks = bookService.viewAvailableBooks();

        assertEquals(1, availableBooks.size());
        assertEquals(book, availableBooks.get(0));
        verify(bookRepository, times(1)).findByAvailableTrue();
    }
}
