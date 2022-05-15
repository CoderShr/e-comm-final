package com.cart.ShoppingService.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.ShoppingService.Dto.AddProductToCartDTO;
import com.cart.ShoppingService.Dto.UpdateProductToCartDTO;
import com.cart.ShoppingService.Dto.UserCartDTO;
import com.cart.ShoppingService.Exception.DataNotDeletedException;
import com.cart.ShoppingService.Exception.DataNotFoundException;
import com.cart.ShoppingService.Exception.DataNotSavedException;
import com.cart.ShoppingService.Exception.ProductNotFoundException;
import com.cart.ShoppingService.Model.Cart;
import com.cart.ShoppingService.Model.Product;
import com.cart.ShoppingService.Model.User;
import com.cart.ShoppingService.Repository.ShoppingCartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{
	private static Logger log = LogManager.getLogger(ShoppingCartServiceImpl.class);
   
	@Autowired
    private final ShoppingCartRepository cartRepository;
    @Autowired
    private final ShoppingProductService productService;
    @Autowired
    private final UserService userService;

    @Override
    public String addProductToCart(AddProductToCartDTO addProductToCartDTO) throws ProductNotFoundException, DataNotSavedException, DataNotFoundException {
        String msg = "Product added to cart.";
        if(checkProductExits(addProductToCartDTO.getProductId(), addProductToCartDTO.getUserId())){
            msg = "Product quantity incremented by 1 in cart";
            updateQuantityInCart(addProductToCartDTO.getProductId(), addProductToCartDTO.getUserId(), 1);
        }else
            addProductToCart(addProductToCartDTO.getUserId(), addProductToCartDTO.getProductId(), 1);
        return msg;
    }

    @Override
    public String updateProductToCart(UpdateProductToCartDTO updateProductToCartDTO) throws DataNotFoundException, DataNotSavedException, DataNotDeletedException {
        String msg = "Product quantity updated in cart.";
        if(updateProductToCartDTO.getQuantity() == 0L){
            log.info("Product's quantity is zero, deleting product from cart.");
            deleteProductFromCart(updateProductToCartDTO.getUserId(), updateProductToCartDTO.getProductId());
            msg = "Product deleted from cart.";
        }else{
            if(checkProductExits(updateProductToCartDTO.getProductId(), updateProductToCartDTO.getUserId())){
            log.info("Product id: {} exist for userId: {} updating quantity in cart by: {}",
                    updateProductToCartDTO.getUserId(), updateProductToCartDTO.getProductId(), updateProductToCartDTO.getQuantity());
                updateQuantityInCart(updateProductToCartDTO.getProductId(),
                        updateProductToCartDTO.getUserId(),
                        updateProductToCartDTO.getQuantity());
            }else {
                throw new DataNotFoundException("Product and user details are not present in cart.");
            }
        }
        return msg;
    }
  
    @Transactional
    @Override
    public UserCartDTO viewUserCart(Integer userId) {

        final List<Cart> userCartList = cartRepository.findAllByUserUserId(userId);
        Float totalBill = 0.0f;
        for(Cart cart : userCartList) {
        	totalBill += cart.getQuantity() * cart.getProduct().getPrice();
        }
        log.info("Total records fetched: "+userCartList.size());
        return new UserCartDTO(totalBill, userCartList);
        }


    @Override
    public void removeProductFromCart(Integer productId, Integer userId) throws DataNotFoundException, DataNotDeletedException {

        if(!checkProductExits(productId, userId)){
            log.error("Requested product with id: "+productId+" does not exist fro user: "+userId);
            throw new DataNotFoundException("Requested product to delete is not present in your cart.");
        }
        log.info("Deleting product with id: "+productId+" for user with id: "+userId);
        deleteProductFromCart(userId, productId);
    }

    @Override
    public void removeCart(Integer cartId) throws DataNotFoundException{
        if(!cartRepository.existsById(cartId))
            throw new DataNotFoundException("Requested cart to delete not found.");
        cartRepository.deleteById(cartId);
    }
    
    @Override
    public void removeAllProductsByUserId(Integer userId) throws DataNotDeletedException {
        try {
            cartRepository.deleteCartByUserId(userId);
        }catch (Exception ex){
            log.error("Error occurred while deleting cart for user id: "+userId, ex);
            throw new DataNotDeletedException("Sorry error occurred while deleting products... Please again later.");
        }
    }
    
    private boolean checkProductExits(Integer productId, Integer userId){
        return cartRepository.existsByProductProductIdAndUserUserId(productId, userId);
    }

    
    private void updateQuantityInCart(Integer productId, Integer userId, Integer quantity) throws DataNotSavedException {
        try {
            cartRepository.setQuantityByProductProductIdAndUserUserId(quantity, productId, userId);
        }catch (Exception ex){
            log.error("Error occurred while updating quantity: "+quantity+" for product id: "+productId+" and user Id: "+userId, ex);
            throw new DataNotSavedException("Some error occurred while updating quantity... Please try again later.");
        }

    }

    private void deleteProductFromCart(Integer userId, Integer productId) throws DataNotDeletedException {
        try {
            cartRepository.deleteCartByUserIdAndProductId(userId, productId);
        }catch (Exception exception){
           log.error("Error occurred while deleting cart with product id: "+productId+" user id: "+userId, exception);
           throw new DataNotDeletedException("Error occurred while deleting product from cart... Please try again later.");

        }

    }

    private void addProductToCart(Integer userId, Integer productId, Integer quantity) throws DataNotFoundException, DataNotSavedException{

            User userById = userService.getUserById(userId);
            log.info("User details for userId: "+userById+" are: "+userById.toString());

            Product product = productService.getProductById(productId);
            log.info("Product details for productId: {} are: {}", productId, product.toString());

            Cart cart = new Cart();
            cart.setUser(userById);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            log.info("Saving cart with details: {}", cart.toString());
            saveCart(cart);
    }

    @Transactional
    public void saveCart(Cart cart) throws DataNotSavedException{
        try {
            cartRepository.save(cart);
        }catch (Exception ex){
            log.error("Error while saving cart ", ex);
            throw new DataNotSavedException("Some error occurred while storing product to cart..." +
                    "Please try again later.");
        }

    }

}