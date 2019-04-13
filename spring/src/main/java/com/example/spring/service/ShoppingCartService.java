package com.example.spring.service;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;

public interface ShoppingCartService {

    void addProduct(String phrase);
    void removeProduct(String phrase);
    ArrayList<String> getProductsInCart();

}
