package com.example.spring.controller;

import com.example.spring.model.ListingOffer;
import com.example.spring.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController("/api/cart")
public class BasketController {

    private ShoppingCartService shoppingCartService;

    private RestTemplate restTemplate;

    public BasketController(ShoppingCartService shoppingCartService, RestTemplate restTemplate) {
        this.shoppingCartService = shoppingCartService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestParam String phrase) {
        shoppingCartService.addProduct(phrase);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ArrayList<String>> getCartItems() {
        ArrayList<String> response = shoppingCartService.getProductsInCart();
        return ResponseEntity.ok(response);
    }
}
