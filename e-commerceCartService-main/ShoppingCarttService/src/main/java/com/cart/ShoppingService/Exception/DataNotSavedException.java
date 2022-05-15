package com.cart.ShoppingService.Exception;

public class DataNotSavedException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataNotSavedException(String message){
        super(message);
    }
}
