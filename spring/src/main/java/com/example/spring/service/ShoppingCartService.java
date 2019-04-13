package com.example.spring.service;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ShoppingCartService {

    void addProduct(ListingOffer item);
    void removeProduct(ListingOffer item);

    Set<ListingOffer> getProductsInCart();

}
