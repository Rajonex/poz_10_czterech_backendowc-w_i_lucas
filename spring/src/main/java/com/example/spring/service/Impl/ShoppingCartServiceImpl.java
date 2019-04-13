package com.example.spring.service.Impl;

import com.example.spring.model.ListingOffer;
import com.example.spring.service.ShoppingCartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@Service
@Scope(value= WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ArrayList<String> products;

    public ShoppingCartServiceImpl() {
        this.products = new ArrayList<>();
    }

    @Override
    public void addProduct(String listingOffer) {
        this.products.add(listingOffer);
    }

    @Override
    public void removeProduct(String listingOffer) {
        this.products.remove(listingOffer);
    }

    @Override
    public ArrayList<String> getProductsInCart() {
        return this.products;
    }
}
