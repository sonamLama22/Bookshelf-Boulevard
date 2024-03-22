package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.Genre;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;

import java.util.List;

public interface GenreService {

    public Genre addGenre(Genre genre);
    public Genre getGenre(int genreId) throws ResourceNotFoundException;

    public String deleteGenre(int genreId) throws ResourceNotFoundException;

    public List<Genre> findAllGenres();

}
