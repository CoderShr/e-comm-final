package com.cart.ShoppingService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProductToCartDTO {

    @NotNull(message = "Product Id cannot be null.")
    @Min(value = 1, message = "Product Id cannot be less than 1")
    private Integer productId;
    @NotNull(message = "User Id cannot be null.")
    @Min(value = 1, message = "User id cannot be less than 1")
    private Integer userId;
    @NotNull(message = "Product quantity cannot be null.")
    @Min(value = 0, message = "Product quantity cannot be less than 0")
    private int quantity;
}
