package com.example.spring.controller;

import com.example.spring.heuristic.GreedyHeuristic;
import com.example.spring.heuristic.Heuristic;
import com.example.spring.heuristic.LocalSearch;
import com.example.spring.heuristic.util.ParcelConverter;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.ListingResponseOffers;
import com.example.spring.model.SearchOffers;
import com.example.spring.model.web.BasketWrapper;
import com.example.spring.model.web.ListWrapper;
import com.example.spring.model.web.Basket;
import com.example.spring.model.web.MapWrapper;
import com.example.spring.service.ShoppingCartService;
import com.example.spring.service.SimilaritySearcherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/cart")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
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
                "https://api.allegro.pl/offers/listing?include=sort&sort=-price&phrase=" + phrase, HttpMethod.GET, entity, SearchOffers.class);

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
    public ResponseEntity<ListWrapper<BasketWrapper>> getProposeOffers(@RequestParam String algorithm, Integer maxPacket) {
        Map<ListingOffer, List<ListingOffer>> data = similaritySearcherService.searchSimilaryOffers(
                new ArrayList<>(shoppingCartService.getProductsInCart()));

        ArrayList<MapWrapper> wrappedMap = new ArrayList<>();

        for (Map.Entry<ListingOffer, List<ListingOffer>> e: data.entrySet()) {
            wrappedMap.add(new MapWrapper(e.getKey(), e.getValue()));
        }

        ListWrapper<MapWrapper> listWrapper = new ListWrapper<>(wrappedMap);

        RestTemplate rest = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("algorithm", algorithm);

        HttpEntity<ListWrapper<MapWrapper>> request = new HttpEntity<>(listWrapper);
        listWrapper = rest.postForObject("http://localhost:9000/api/filter", request, ListWrapper.class, params);

        Map<ListingOffer, List<ListingOffer>> newData = new HashMap<>();
        for (MapWrapper mapWrapper: listWrapper.getData()) {
            newData.put(mapWrapper.getKey(), mapWrapper.getValue());
        }

//        Heuristic heuristic = new GreedyHeuristic(false);
        Heuristic heuristic = new LocalSearch(new GreedyHeuristic(true),true);
        List<Basket> b = ParcelConverter.convertSellersToParcels(heuristic.run(newData, 1));
        List<BasketWrapper> wrappedBaskets = b.stream().map(BasketWrapper::from).collect(Collectors.toList());
        ListWrapper<BasketWrapper> response = new ListWrapper<>(wrappedBaskets);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Set<ListingOffer>> getCartItems() {
        Set<ListingOffer> response = shoppingCartService.getProductsInCart();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItem(@RequestBody ListingOffer item) {
        shoppingCartService.removeProduct(item);
        return ResponseEntity.ok().build();
    }


}
