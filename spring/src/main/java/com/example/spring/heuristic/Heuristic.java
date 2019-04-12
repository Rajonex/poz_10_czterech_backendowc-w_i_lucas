package com.example.spring.heuristic;

import com.example.spring.model.ListingOffer;
import com.example.spring.model.web.Basket;

import java.util.List;
import java.util.Map;

public interface Heuristic {

    List<Basket> run(Map<ListingOffer, List<ListingOffer>> productsAndSimilarities, Integer maxParcels);
}
