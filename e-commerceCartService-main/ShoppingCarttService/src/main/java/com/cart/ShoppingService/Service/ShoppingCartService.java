package com.cart.ShoppingService.Service;

import org.springframework.stereotype.Service;

import com.cart.ShoppingService.Dto.AddProductToCartDTO;
import com.cart.ShoppingService.Dto.UpdateProductToCartDTO;
import com.cart.ShoppingService.Dto.UserCartDTO;
import com.cart.ShoppingService.Exception.DataNotDeletedException;
import com.cart.ShoppingService.Exception.DataNotFoundException;
import com.cart.ShoppingService.Exception.DataNotSavedException;
import com.cart.ShoppingService.Model.Cart;

@Service
public interface ShoppingCartService {

	public String addProductToCart(AddProductToCartDTO addProductToCartDTO) throws DataNotSavedException, DataNotFoundException;
	
	public String updateProductToCart(UpdateProductToCartDTO updateProductToCartDTO) throws DataNotFoundException, DataNotSavedException, DataNotDeletedException;
	
    public UserCartDTO viewUserCart(Integer userId);   
	
    public void removeProductFromCart(Integer productId, Integer userId) throws DataNotFoundException, DataNotDeletedException ;

	public void removeCart(Integer cartId) throws DataNotFoundException;

	public void removeAllProductsByUserId(Integer userId) throws DataNotDeletedException; 
  
    public void saveCart(Cart cart) throws DataNotSavedException;

	
	
}
