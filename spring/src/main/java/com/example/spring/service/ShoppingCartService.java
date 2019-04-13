package com.example.spring.service;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;
import java.util.HashSet;

public interface ShoppingCartService {

    void addProduct(ListingOffer item);
    void removeProduct(ListingOffer item);

    HashSet<ListingOffer> getProductsInCart();

}
