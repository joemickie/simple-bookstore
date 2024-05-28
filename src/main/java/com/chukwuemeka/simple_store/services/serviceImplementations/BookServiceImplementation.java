package com.chukwuemeka.simple_store.services.serviceImplementations;

import com.chukwuemeka.simple_store.dtos.Bookdto;
import com.chukwuemeka.simple_store.exceptions.SimpleBookstoreException;
import com.chukwuemeka.simple_store.models.Book;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.repositories.BookRepository;
import com.chukwuemeka.simple_store.repositories.UserRepository;
import com.chukwuemeka.simple_store.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<ApiResponse> addBook(Bookdto bookdto) {
        Optional<Book> book = bookRepository.findByTitle(bookdto.title());
        if (!book.isPresent()){
            Book newBook = new Book();
            newBook.setTitle(bookdto.title());
            newBook.setAuthor(bookdto.author());
            newBook.setPrice(bookdto.price());
            newBook.setAvailable(true);
            newBook.setQuantity(bookdto.quantity());
            Book savedBook = bookRepository.save(newBook);
            ApiResponse<String> response = new ApiResponse<>("Book added", HttpStatus.OK);
            return ResponseEntity.ok(response);
        }
        else {
            Book bookToUpdate = book.get();
            bookToUpdate.setQuantity(bookToUpdate.getQuantity() + bookdto.quantity());
            bookRepository.save(bookToUpdate);
            ApiResponse response = new ApiResponse<>("Book Already exist and quantity increased", HttpStatus.OK);
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateBookDetails(String title, Bookdto bookdto) {
        Optional<Book> bookOptional = bookRepository.findByTitleContainingIgnoreCase(title);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setPrice(bookdto.price());
            book.setQuantity(bookdto.quantity());
            book.setAvailable(true);
            book.setTitle(bookdto.title());
            book.setAuthor(bookdto.author());
            bookRepository.save(book);
            ApiResponse<String> response = new ApiResponse<>("Book Updated", HttpStatus.OK);
            return ResponseEntity.ok(response);
        }
        throw new SimpleBookstoreException("Book doesn't exist");
    }


    @Override
    public ResponseEntity<ApiResponse> deleteBook(String title) {
        var book = bookRepository.findByTitle(title).orElseThrow(() -> new SimpleBookstoreException("Book doesn't exist, check title"));
        if (book.isAvailable()){
            bookRepository.delete(book);
            ApiResponse<String> response = new ApiResponse<>("Book deleted", HttpStatus.OK);
            return ResponseEntity.ok(response);
        }
        throw new SimpleBookstoreException("Can't delete Unavailable or not existing book");
    }

    @Override
    public List<Book> viewAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
}
