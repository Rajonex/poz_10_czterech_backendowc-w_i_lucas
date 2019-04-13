package com.example.spring.service;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;

public interface ShoppingCartService {

    void addProduct(ListingOffer item);
    void removeProduct(ListingOffer item);

    ArrayList<ListingOffer> getProductsInCart();

}
