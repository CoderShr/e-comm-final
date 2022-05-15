package com.cart.ShoppingService.Dto;

import java.util.ArrayList;
import java.util.List;

import com.cart.ShoppingService.Model.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserCartDTO {

	private float totalBill = 0;
    private List<Cart> cartItems = new ArrayList<>();
}

