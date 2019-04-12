package com.example.spring.service;


import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferPrice;
import com.example.spring.model.SearchOffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimilarySearcherService {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    public SimilarySearcherService(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, List<ListingOffer>> searchSimilaryOffers(List<String> offers) {
        Map<String, List<ListingOffer>> similaryOffers = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Header", "value");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);

        for (String offer : offers) {
            ResponseEntity<SearchOffers> response = restTemplate.exchange(
                    "https://api.allegro.pl/offers/listing?phrase=" + offer, HttpMethod.GET, entity, SearchOffers.class);
            similaryOffers.put(offer, response.getBody().getItems().getRegular());
            similaryOffers.get(offer).addAll(response.getBody().getItems().getPromoted());
        }
        return similaryOffers;
    }
}
