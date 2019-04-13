package com.example.spring.service;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;

public interface ShoppingCartService {

    void addProduct(String listingOffer);
    void removeProduct(String listingOffer);
    ArrayList<String> getProductsInCart();

}
