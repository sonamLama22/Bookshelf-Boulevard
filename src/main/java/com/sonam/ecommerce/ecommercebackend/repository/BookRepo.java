package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Data access and persistence
@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    // Query methods
    public Book findByTitle(String title);
    public List<Book> findByAuthorContaining(String author);

    @Query("SELECT b FROM Book b WHERE b.genre.genreName = :genreName")
    public List<Book> findByGenreName(@Param("genreName") String genreName);
    public List<Book> findByTitleContaining(String titleKeyword);

}
