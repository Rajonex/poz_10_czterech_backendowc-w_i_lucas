package com.example.spring.controller;

import com.example.spring.model.ListingOffer;
import com.example.spring.model.ListingResponseOffers;
import com.example.spring.model.SearchOffers;
import com.example.spring.model.web.Basket;
import com.example.spring.service.ShoppingCartService;
import com.example.spring.service.SimilarySearcherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        HttpHeaders headers = new HttpHeaders();
        headers.set("Header", "value");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<SearchOffers> response = restTemplate.exchange(
                String.format("https://api.allegro.pl/offers/listing?phrase=%s", phrase), HttpMethod.GET, entity, SearchOffers.class);


        if (response.hasBody()) {
            ListingResponseOffers items = Objects.requireNonNull(response.getBody()).getItems();
            if (items.getPromoted().size() > 0) {
                shoppingCartService.addProduct(items.getPromoted().get(0));
            } else if (items.getRegular().size() > 0) {
                shoppingCartService.addProduct(items.getRegular().get(0));
            } else {
                return ResponseEntity.notFound().build();
            }
        }

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

    }
}
