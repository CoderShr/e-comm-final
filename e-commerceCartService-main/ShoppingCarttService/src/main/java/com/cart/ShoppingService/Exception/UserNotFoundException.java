package com.cart.ShoppingService.Exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
        super(String.format("User with Id %d not found", id));
    }

	public UserNotFoundException(String string) {
		 super(String.format("User with Id %d not found"));
	}
}