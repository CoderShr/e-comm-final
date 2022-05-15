package com.cart.ShoppingService.Model;

import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book extends Product{

    private String genre;

    private String author;

    private String publications;


    //TODO: Check if equals and hashcode is needed for all models
}