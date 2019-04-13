package com.example.spring.heuristic;

import com.example.spring.model.ListingOffer;

import java.util.Map;

public interface InitialHeuristic extends Heuristic {
    Map<ListingOffer, ListingOffer> getCachedSimilaritiesConverts();
}
