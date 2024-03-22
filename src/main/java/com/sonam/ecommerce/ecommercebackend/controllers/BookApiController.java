package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.BookDto;
import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.implementation.BookServiceImpl;
import com.sonam.ecommerce.ecommercebackend.service.implementation.GenreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookApiController {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    GenreServiceImpl genreService;

    // localhost:8080/api/admin/addBook
    @PostMapping(value = "/admin/addBook", headers = ("content-type=multipart/*"))
    public ResponseEntity<?> addBook(@RequestPart("file") MultipartFile file,
                                     @RequestPart("data") BookDto bookDto) throws Exception {

        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        Book bookAdded = bookService.addBook(book, file);
        return new ResponseEntity<>(bookAdded, HttpStatus.OK);
    }

    @GetMapping("/admin/getBook/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable int bookId) throws ResourceNotFoundException {
        Book book = bookService.getBook(bookId);
        BookDto bookResponse = new BookDto();
        BeanUtils.copyProperties(book, bookResponse);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    // localhost:8080/api/admin/updateBook/4
    @PutMapping(value="/admin/updateBook/{bookId}", headers = ("content-type=multipart/*"))
    public ResponseEntity<?> updateBook(@RequestPart("file") MultipartFile file,
                                        @RequestPart("data") Book book, @PathVariable int bookId) throws Exception {
        Book b = bookService.bookExists(bookId);
        if(b != null){
            Book updateBook = Book.builder()
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .genre(book.getGenre())
                    .price(book.getPrice())
                    .description(book.getDescription())
                    .copiesAvailable(book.getCopiesAvailable())
                    .fileName(book.getFileName())
                    .fileType(book.getFileType())
                    .data(book.getData())
                    .build();
            bookService.deleteBook(bookId); // delete previous book.
            Book updatedBook = bookService.updateBook(updateBook, file);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        return new ResponseEntity<>("Book could not be updated.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/admin/deleteBook/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable int bookId) throws ResourceNotFoundException {
        Book book = bookService.bookExists(bookId);
        if(book != null){
            bookService.deleteBook(bookId);
            return new ResponseEntity<>(book.getTitle()+ " has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Book could not deleted", HttpStatus.OK);
    }

    @GetMapping("/admin/getAllBooks")
    public ResponseEntity<?> getAllBooks() throws ResourceNotFoundException {
        List<Book> bookList = bookService.getAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/admin/copiesAvailable/{bookId}")
    public ResponseEntity<?> getCopiesAvailable(@PathVariable int bookId) throws ResourceNotFoundException {
        int num = bookService.copiesAvailable(bookId);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping("/admin/search/by-title")
    public ResponseEntity<?> findBooksByTitleContaining(@RequestParam("title") String title) throws ResourceNotFoundException {
        List<Book> books = bookService.findByTitleContaining(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
