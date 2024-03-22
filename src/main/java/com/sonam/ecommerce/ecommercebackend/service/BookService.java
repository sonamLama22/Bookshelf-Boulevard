package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import com.sonam.ecommerce.ecommercebackend.entity.Book;

import java.util.List;

public interface BookService {

    // BUSINESS LOGIC
    public Book addBook(Book book, MultipartFile file) throws Exception;
    public Book getBook(int bookId) throws ResourceNotFoundException;
    public String deleteBook(int bookId) throws ResourceNotFoundException;
    public Book updateBook(Book book) throws ResourceNotFoundException;
    public List<Book> getAllBooks();
    public int copiesAvailable(int bookId);

}
