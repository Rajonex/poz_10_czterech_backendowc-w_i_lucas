package com.example.spring.controller;

import com.example.spring.model.ListingOffer;
import com.example.spring.model.web.Basket;
import com.example.spring.service.ShoppingCartService;
import com.example.spring.service.SimilarySearcherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController("/api/cart")
public class BasketController {

    private ShoppingCartService shoppingCartService;

    private RestTemplate restTemplate;

    private SimilarySearcherService similarySearcherService;

    public BasketController(ShoppingCartService shoppingCartService, RestTemplate restTemplate, SimilarySearcherService similarySearcherService) {
        this.shoppingCartService = shoppingCartService;
        this.restTemplate = restTemplate;
        this.similarySearcherService = similarySearcherService;
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

    @GetMapping
    public ResponseEntity<ArrayList<Basket>> getProposeOffers(@RequestParam Integer maxParcels) {
        Map<ListingOffer, List<ListingOffer>> data = similarySearcherService.searchSimilaryOffers(shoppingCartService.getProductsInCart());
        return ResponseEntity.ok().build();
    }
}
