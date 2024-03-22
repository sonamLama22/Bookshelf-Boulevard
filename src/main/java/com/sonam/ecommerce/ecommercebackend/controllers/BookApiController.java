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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookApiController {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    GenreServiceImpl genreService;

    @PostMapping(value = "/admin/addBook/genreId={genreId}", headers = ("content-type=multipart/*"))
    public ResponseEntity<?> addBook(@RequestPart("file") MultipartFile file, @PathVariable int genreId,
                                     @RequestPart("data") BookDto bookDto) throws Exception {
        genreService.getGenre(genreId); //check if genre exists.
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        Book bookAdded = bookService.addBook(book, file);
        return new ResponseEntity<>(bookAdded, HttpStatus.OK);
    }
}
