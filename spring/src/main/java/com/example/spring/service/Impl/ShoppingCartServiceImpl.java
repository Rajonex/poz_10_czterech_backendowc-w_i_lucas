package com.example.spring.service.Impl;

import com.example.spring.model.ListingOffer;
import com.example.spring.service.ShoppingCartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@Scope(value= WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private Set<ListingOffer> products;

    public ShoppingCartServiceImpl() {
        this.products = new HashSet<>();
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
    public Set<ListingOffer> getProductsInCart() {
        return this.products;
    }
}
