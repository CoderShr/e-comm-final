package com.cart.ShoppingService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.ShoppingService.Model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
