package com.cart.ShoppingService.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cart.ShoppingService.Exception.ProductNotFoundException;
import com.cart.ShoppingService.Model.Product;

@Service
public interface ShoppingProductService {

	public List<Product> getProducts() throws ProductNotFoundException ;
	
	public Product getProductById(Integer productId) throws ProductNotFoundException ;
	   
	public Product getProductsByName(String productName) throws ProductNotFoundException ;

	public List<Product> getProductsByCatagory(String productCatagory) throws ProductNotFoundException ;

	
}
