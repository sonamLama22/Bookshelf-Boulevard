package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.Genre;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.GenreRepo;
import com.sonam.ecommerce.ecommercebackend.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepo genreRepo;

    @Override
    public Genre addGenre(Genre genre) {
        return genreRepo.save(genre);
    }

    @Override
    public Genre getGenre(int genreId) throws ResourceNotFoundException {
        Genre genre = genreRepo.findById(genreId).orElseThrow(()-> new ResourceNotFoundException("Genre not found for this id::"+genreId));
        return genre;
    }

    @Override
    public String deleteGenre(int genreId) throws ResourceNotFoundException {
        Genre genre = genreRepo.findById(genreId).orElseThrow(()-> new ResourceNotFoundException("Genre not found for this id::"+genreId));
        genreRepo.delete(genre);
        return genre.getGenreName() + " genre has been deleted.";
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepo.findAll();
    }
}
