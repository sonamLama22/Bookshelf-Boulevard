package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Data access and persistence
@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    // Query methods
    public Book findBookByTitle(String title);
    public List<Book> findBooksByAuthor(String author);
    public List<Book> findBooksByGenres_genreId(int genreId);
    public List<Book> findBooksByTitleContaining(String titleKeyword);

}
