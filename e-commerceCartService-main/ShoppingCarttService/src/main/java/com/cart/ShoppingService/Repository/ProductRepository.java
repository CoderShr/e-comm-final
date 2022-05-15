package com.cart.ShoppingService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.ShoppingService.Model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
	
	Optional<Product> findByProductName(String productName);
	Optional<Product> findByProductId(Integer id);
	List<Product> findByCatagory(String catagory);
}
