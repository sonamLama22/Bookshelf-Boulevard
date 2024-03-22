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
        return null;
    }

    @Override
    public String deleteBook(int bookId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Book updateBook(Book book) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public int copiesAvailable(int bookId) {
        return 0;
    }
}
