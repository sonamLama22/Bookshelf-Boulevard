package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.entity.Genre;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.implementation.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenreApiController {

    @Autowired
    GenreServiceImpl genreService;

    // localhost:8080/api/admin/addGenre
    @PostMapping("/admin/addGenre")
    public ResponseEntity<?> addGenre(@RequestBody Genre genre){
        Genre genreAdded = genreService.addGenre(genre);
        return new ResponseEntity<>(genreAdded, HttpStatus.OK);
    }

    @GetMapping("/admin/getGenre/{genreId}")
    public ResponseEntity<?> getGenre(@PathVariable int genreId) throws ResourceNotFoundException {
        return new ResponseEntity<>(genreService.getGenre(genreId), HttpStatus.OK);
    }

    @DeleteMapping("/admin/deleteGenre/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable int genreId) throws ResourceNotFoundException {
        return new ResponseEntity<>(genreService.deleteGenre(genreId), HttpStatus.OK);
    }

    // localhost:8080/api/admin/genres
    @GetMapping("/admin/genres")
    public ResponseEntity<?> getGenres(){
        List<Genre> list = genreService.findAllGenres();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
