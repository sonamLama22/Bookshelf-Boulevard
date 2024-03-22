package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.BookRepo;
import com.sonam.ecommerce.ecommercebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;


    @Override
    public Book addBook(Book book, MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //handle validations
        try{
            if(fileName.contains("..")){
                throw new Exception("Filename contains invalid path sequence: "+fileName);
            }
            Book saveBook = Book.builder()
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .price(book.getPrice())
                    .copiesAvailable((book.getCopiesAvailable()))
                    .description(book.getDescription())
                    .genre(book.getGenre())
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();
            return bookRepo.save(saveBook);
        }catch (Exception e){
            throw new Exception("Failed to save file: "+fileName+e.getMessage());
        }
    }

    @Override
    public Book getBook(int bookId) throws ResourceNotFoundException {

        Book book = bookRepo.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found for this id::"+bookId));
        return book;
    }

    @Override
    public String deleteBook(int bookId) throws ResourceNotFoundException {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found for this id::"+bookId));
        bookRepo.delete(book);
        return "Book has been removed";
    }

    @Override
    public Book updateBook(Book book, MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //handle validations
        try{
            if(fileName.contains("..")){
                throw new Exception("Filename contains invalid path sequence: "+fileName);
            }
            Book saveBook = Book.builder()
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .price(book.getPrice())
                    .copiesAvailable((book.getCopiesAvailable()))
                    .description(book.getDescription())
                    .genre(book.getGenre())
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();
            return bookRepo.save(saveBook);
        }catch (Exception e){
            throw new Exception("Failed to save file: "+fileName+e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() throws ResourceNotFoundException {
        List<Book> books = bookRepo.findAll();
        if(books.isEmpty()) throw new ResourceNotFoundException("List is empty.");
        return books;
    }

    @Override
    public int copiesAvailable(int bookId) throws ResourceNotFoundException {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found for this id::"+bookId));
        return book.getCopiesAvailable();
    }

    @Override
    public Book bookExists(int bookId) throws ResourceNotFoundException {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found for this id::"+bookId));
        return book;
    }

    @Override
    public Book findByTitle(String title) throws ResourceNotFoundException {
        Book book = bookRepo.findByTitle(title);
        if(book != null){
            return book;
        }
        else{
            throw new ResourceNotFoundException("Book not found for this title::"+title);
        }
    }

    @Override
    public List<Book> findByAuthor(String author) throws ResourceNotFoundException {
        List<Book> books = bookRepo.findByAuthor(author);
        if(books.isEmpty()) throw new ResourceNotFoundException("List is empty.");
        return books;
    }

    @Override
    public List<Book> findByGenre(String genreName) throws ResourceNotFoundException {
        List<Book> books = bookRepo.findByGenreName(genreName);
        if(books.isEmpty()) throw new ResourceNotFoundException("List is empty");
        return books;
    }

    @Override
    public List<Book> findByTitleContaining(String titleKeyword) throws ResourceNotFoundException {
        List<Book> books = bookRepo.findByTitleContaining(titleKeyword);
        if(books.isEmpty()) throw new ResourceNotFoundException("No books found containing keyword::"+titleKeyword);
        return books;
    }
}
