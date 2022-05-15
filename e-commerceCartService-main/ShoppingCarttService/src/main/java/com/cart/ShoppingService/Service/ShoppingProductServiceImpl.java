package com.cart.ShoppingService.Service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.ShoppingService.Exception.ProductNotFoundException;
import com.cart.ShoppingService.Model.Product;
import com.cart.ShoppingService.Repository.ProductRepository;

@Service
public class ShoppingProductServiceImpl implements ShoppingProductService{
	static Logger logger = LogManager.getLogger(ShoppingProductServiceImpl.class);
	@Autowired
	public ProductRepository productRepository;

	@Override
	public List<Product> getProducts() throws ProductNotFoundException{
	    logger.info("Get all products - started to find products from repo");
	    try{
	    	return productRepository.findAll();
	    
	    } catch(Exception e){
		logger.error("Some exception occured while fetch product by Id");
		throw new ProductNotFoundException(e.getMessage());
	    }
	}
	@Override
	public Product getProductById(Integer productId ) throws ProductNotFoundException{
		Optional<Product> productDto = null;
		try {
		logger.info("Find product by id: {}, started to find product from repo", productId);
	        productDto = productRepository.findByProductId(productId);
	    	if(!productDto.isPresent()) {
	    	logger.error("Product with product id = {} not found", productId);
		    
	    	}
	    } catch(Exception e){
		    logger.error("Some exception occured while fetch product by Id");
		    throw new ProductNotFoundException(e.getMessage());
	    }
		return productDto.get();	
	}
	
	@Override
	public Product getProductsByName(String productName) throws ProductNotFoundException {
		try { 
		    logger.info("Find product by name: {}, started to find product from repo", productName);
		    Optional<Product> product = productRepository.findByProductName(productName);
		    return getProduct(product);
	    	} catch (Exception e) {
	    		 logger.error("Product not found exception occured while fetch product by name : {}", productName);
	    		 throw new ProductNotFoundException("Product not found") ;
	 	    	
			}
	}

	@Override
	public List<Product> getProductsByCatagory(String productCatagory) throws ProductNotFoundException {
		List<Product> productList = null;
		try {	
		logger.info("Find product by catagory : {}, started to find product from repo", productCatagory);
		productList = productRepository.findByCatagory(productCatagory);
		if(productList.isEmpty() || productList.size() == 0) {
			throw new ProductNotFoundException("product not found");
		}
	    } catch(Exception e){
	    	logger.error("Some exception occured while fetch product by catagory");
	        throw new ProductNotFoundException(e.getMessage());
	    }
	    return productList;
	}
	
	private Product getProduct(Optional<Product> product) throws Exception {
		if(!product.isPresent()) {
			throw new ProductNotFoundException("product not found");
		}
		return product.get();
	}
	
}
