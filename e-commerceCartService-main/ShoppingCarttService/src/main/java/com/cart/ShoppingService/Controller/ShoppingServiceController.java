package com.cart.ShoppingService.Controller;

import javax.validation.constraints.Min;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cart.ShoppingService.Dto.AddProductToCartDTO;
import com.cart.ShoppingService.Dto.UpdateProductToCartDTO;
import com.cart.ShoppingService.Dto.UserCartDTO;
import com.cart.ShoppingService.Exception.DataNotDeletedException;
import com.cart.ShoppingService.Exception.DataNotFoundException;
import com.cart.ShoppingService.Exception.DataNotSavedException;
import com.cart.ShoppingService.Service.ShoppingCartService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/myCart")
public class ShoppingServiceController {
	private static Logger log = LogManager.getLogger(ShoppingServiceController.class);
	
	@Autowired
	public ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<String> addProductsToCart(@Validated @RequestBody AddProductToCartDTO addProductToCartDTO) throws DataNotSavedException, DataNotFoundException{
        log.info("Request to add product to cart: "+ addProductToCartDTO.toString());
        String msg = shoppingCartService.addProductToCart(addProductToCartDTO);
        log.info("Added product to cart");
        return new ResponseEntity<String>(msg, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> addToCart(@Validated @RequestBody UpdateProductToCartDTO updateProductToCartDTO) throws DataNotFoundException, DataNotSavedException, DataNotDeletedException{
        log.info("Request to add product to cart: "+updateProductToCartDTO.toString());
        String msg = shoppingCartService.updateProductToCart(updateProductToCartDTO);
        log.info(msg);
        return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED) ;
    }

    @GetMapping("/view/{userId}")
    public ResponseEntity<UserCartDTO> viewUserCart(@PathVariable @Min(value = 1,
    message = "User id cannot be 0 or negative.") Integer userId){
        log.info("Request to view user cart for user id: "+ userId);
        UserCartDTO myCart = shoppingCartService.viewUserCart(userId);
       // log.info("Retrieved user cart for user id: "+ userId);
        return new ResponseEntity<UserCartDTO>(myCart, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(@RequestParam("p") @Min(value = 1, message = "Product id cannot be 0 or negative.") Integer productId,  @RequestParam("u")
    @Min(value = 1, message = "User id cannot be 0 or negative.") Integer userId) throws DataNotFoundException, DataNotDeletedException{
        log.info("Request to delete product with id: "+productId+" for user id: "+userId+" from cart");
        shoppingCartService.removeProductFromCart(productId, userId);
        log.info("Deleted product with id: "+productId+" for user id: "+userId+" from cart");
        return new ResponseEntity<String>("Product removed from cart successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/removeCart/{cartId}")
    private ResponseEntity<String> removeCart(@PathVariable @Min(value = 1, message = "Cart id cannot be 0 or negative.") Integer cartId) throws DataNotFoundException{
        log.info("Request to delete cart with is: "+cartId);
        shoppingCartService.removeCart(cartId);
        log.info("Deleted cart with is: "+cartId);
        return new ResponseEntity<String>("Cart deleted successfully.", HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/removeAll/{userId}")
    private ResponseEntity<String> removeAllProducts(@PathVariable @Min(value = 1, message = "User id cannot be 0 or negative.") Integer userId) throws DataNotDeletedException{
        log.info("Request to delete products for user id: "+userId);
        shoppingCartService.removeAllProductsByUserId(userId);
        log.info("Deleted products for user id: "+userId);
        return new ResponseEntity<String>("Products removed successfully.", HttpStatus.ACCEPTED);
    }
}
