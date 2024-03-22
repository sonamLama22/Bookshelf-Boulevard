package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import com.sonam.ecommerce.ecommercebackend.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    // BUSINESS LOGIC
    public Book addBook(Book book, MultipartFile file) throws Exception;
    public Book getBook(int bookId) throws ResourceNotFoundException;
    public String deleteBook(int bookId) throws ResourceNotFoundException;
    public Book updateBook(Book book, MultipartFile file) throws Exception;
    public List<Book> getAllBooks() throws ResourceNotFoundException;
    public int copiesAvailable(int bookId) throws ResourceNotFoundException;

    public Book bookExists(int bookId) throws ResourceNotFoundException;

    public Book findBookByTitle(String title) throws ResourceNotFoundException;
    public List<Book> findBooksByAuthor(String author) throws ResourceNotFoundException;
    public List<Book> findBooksByGenreId(int genreId) throws ResourceNotFoundException;
    public List<Book> findBooksByTitleContaining(String titleKeyword) throws ResourceNotFoundException;

}
