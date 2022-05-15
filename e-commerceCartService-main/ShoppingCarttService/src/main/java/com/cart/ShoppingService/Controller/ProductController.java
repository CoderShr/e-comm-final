package com.cart.ShoppingService.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.ShoppingService.Exception.ProductNotFoundException;
import com.cart.ShoppingService.Model.Product;
import com.cart.ShoppingService.Service.ShoppingProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductController {
	private static Logger log = LogManager.getLogger(ProductController.class);
	
	@Autowired
	public ShoppingProductService shoppingProductService;

	//show all products
	@GetMapping("/search")
	
    	public ResponseEntity<List<Product>> searchAllProducts() throws ProductNotFoundException{
	    log.info("Request to search all product items");
            List<Product> body = null;
			try {
				body = shoppingProductService.getProducts();
			} catch (ProductNotFoundException e) {
				new ProductNotFoundException("Product not found");
			} catch (Exception e) {
				log.error("Some exception occured while fetching for producs");
			}
	    log.info("All product item retrieved");
            return new ResponseEntity<List<Product>>(body, HttpStatus.OK);
   	}
	
	// search and show particular item by product ID
    	@GetMapping("/productsById/{productId}")
	public ResponseEntity<Product> showProductItemByID(@PathVariable(value = "productId") Integer productId) throws ProductNotFoundException {
    	    log.info("Request to search product item for product id ={} started", productId);
	    Product body;
		try {
			body = shoppingProductService.getProductById(productId);
		} catch (Exception e) {
			throw new ProductNotFoundException("Product with id :" + productId + " not found");
		}
	    log.info("Product item with product id ={} retrived", productId);
            return new ResponseEntity<Product>(body, HttpStatus.OK);
      
	}
    
    	// Get particular item by product category
   	@GetMapping("/productsByCatagory/{productCatagory}")
	public ResponseEntity<List<Product>> showProductItemByCatagory(@PathVariable String productCatagory) throws ProductNotFoundException {  
    	    log.info("Request to search product items for product Catagory={} started", productCatagory) ;
	    List<Product> body;
		try {
			body = shoppingProductService.getProductsByCatagory(productCatagory);
		} catch (Exception e) {
			throw new ProductNotFoundException("No Product with catagory :" + productCatagory + " found");
		}     
    	    log.info("Product items with product Catagory={} retrieved", productCatagory) ;
	    return new ResponseEntity<List<Product>>(body, HttpStatus.OK);	        	
	}	
    
    
	// Get particular item by product name
	@GetMapping("/productsByName/{productName}")
	public ResponseEntity<Product> showProductItemByName(@PathVariable String productName) throws ProductNotFoundException {
	    log.info("Request to search product item for product name={} started", productName);
	    Product body;
		try {
			body = shoppingProductService.getProductsByName(productName);
		} catch (Exception e) {
			throw new ProductNotFoundException("Product with product name :" + productName + "not found");
		}  
	    log.info("Product item with product name={} started", productName);  
   	    return new ResponseEntity<Product>(body, HttpStatus.OK);
	}
}
