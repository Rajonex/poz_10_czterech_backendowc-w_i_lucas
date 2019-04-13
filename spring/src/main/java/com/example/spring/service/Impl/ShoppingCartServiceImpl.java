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

    private ArrayList<ListingOffer> products;

    public ShoppingCartServiceImpl() {
        this.products = new ArrayList<>();
    }

    @Override
    public void addProduct(ListingOffer item) {
        this.products.add(item);
    }

    @Override
    public void removeProduct(ListingOffer item) {
        this.products.remove(item);
    }

    @Override
    public ArrayList<ListingOffer> getProductsInCart() {
        return this.products;
    }
}
