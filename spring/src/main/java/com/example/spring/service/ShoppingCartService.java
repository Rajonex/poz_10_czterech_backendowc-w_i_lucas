package com.example.spring.service;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;

public interface ShoppingCartService {

    void addProduct(ListingOffer listingOffer);
    void removeProduct(ListingOffer listingOffer);
    ArrayList<ListingOffer> getProductsInCart();

}
