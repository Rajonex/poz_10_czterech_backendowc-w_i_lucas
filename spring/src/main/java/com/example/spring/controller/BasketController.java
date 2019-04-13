package com.example.spring.controller;

import com.example.spring.heuristic.GreedyHeuristic;
import com.example.spring.heuristic.Heuristic;
import com.example.spring.heuristic.util.ParcelConverter;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.ListingResponseOffers;
import com.example.spring.model.SearchOffers;
import com.example.spring.model.web.ArrayWrapper;
import com.example.spring.model.web.Basket;
import com.example.spring.model.web.MapWrapper;
import com.example.spring.service.ShoppingCartService;
import com.example.spring.service.SimilaritySearcherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api/cart")
public class BasketController {

    private ShoppingCartService shoppingCartService;

    private RestTemplate restTemplate;

    private SimilaritySearcherService similaritySearcherService;

    public BasketController(ShoppingCartService shoppingCartService, RestTemplate restTemplate, SimilaritySearcherService similaritySearcherService) {
        this.shoppingCartService = shoppingCartService;
        this.restTemplate = restTemplate;
        this.similaritySearcherService = similaritySearcherService;
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestParam String phrase) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<SearchOffers> response = restTemplate.exchange(
                "https://api.allegro.pl/offers/listing?phrase=" + phrase, HttpMethod.GET, entity, SearchOffers.class);

        ListingResponseOffers items = response.getBody().getItems();
        ListingOffer key;
        if (items.getPromoted().size() > 0) {
            key = items.getPromoted().get(0);
        } else if (items.getRegular().size() > 0) {
            key = items.getRegular().get(0);
        } else {
            return ResponseEntity.notFound().build();
        }

        key.setName(phrase);

        shoppingCartService.addProduct(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/proposes")
    public ResponseEntity<List<Basket>> getProposeOffers() {
        Map<ListingOffer, List<ListingOffer>> data = similaritySearcherService.searchSimilaryOffers(
                shoppingCartService.getProductsInCart());

        ArrayList<MapWrapper> wrappedMap = new ArrayList<>();

        for (Map.Entry<ListingOffer, List<ListingOffer>> e: data.entrySet()) {
            wrappedMap.add(new MapWrapper(e.getKey(), e.getValue()));
        }

        ArrayWrapper arrayWrapper = new ArrayWrapper(wrappedMap);

        RestTemplate rest = new RestTemplate();

        HttpEntity<ArrayWrapper> request = new HttpEntity<>(arrayWrapper);
        arrayWrapper = rest.postForObject("http://localhost:9000/api/filter", request, ArrayWrapper.class);

        Map<ListingOffer, List<ListingOffer>> newData = new HashMap<>();
        for (MapWrapper mapWrapper: arrayWrapper.getData()) {
            newData.put(mapWrapper.getKey(), mapWrapper.getValue());
        }
        
        Heuristic heuristic = new GreedyHeuristic();
        List<Basket> b = ParcelConverter.convertSellersToParcels(heuristic.run(newData, 1));
        return ResponseEntity.ok(b);
    }

    @GetMapping
    public ResponseEntity<ArrayList<ListingOffer>> getCartItems() {
        ArrayList<ListingOffer> response = shoppingCartService.getProductsInCart();
        return ResponseEntity.ok(response);
    }


}
