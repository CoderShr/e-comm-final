package com.cart.ShoppingService.Model;


import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Apparal extends Product {

    private String type;

    private String brand;

    private String design;
}
	