package com.example.spring.service;

import com.example.spring.OAuth2.ResourceConfiguration;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferCategory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SimilarySearcherServiceTest {

    SimilarySearcherService searcherService;
    List<String> offers;
    ResourceConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ResourceConfiguration();
        searcherService = new SimilarySearcherService(configuration.restTemplate());
        offers = new ArrayList<>();
        offers.add("xiaomi+pocophone+128GB");
    }

    @Test
    public void searchSimilaryOffers() {
        Map<String, List<ListingOffer>> similaryOffers = searcherService.searchSimilaryOffers(offers);
        assertEquals(60, similaryOffers.get(offers.get(0)).size());
    }
}