package com.cart.ShoppingService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.ShoppingService.Model.Apparal;

@Repository
public interface ApparalRepository extends JpaRepository<Apparal, Integer> {
}
