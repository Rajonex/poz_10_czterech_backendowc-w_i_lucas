package com.example.spring.service;


import com.example.spring.model.ListingOffer;
import com.example.spring.model.SearchOffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimilaritySearcherService {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    public SimilaritySearcherService(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<ListingOffer, List<ListingOffer>> searchSimilaryOffers(List<ListingOffer> offers) {
        Map<ListingOffer, List<ListingOffer>> similaryOffers = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Header", "value");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);

        for (ListingOffer offer : offers) {
            ResponseEntity<SearchOffers> response = restTemplate.exchange(
                    "https://api.allegro.pl/offers/listing?phrase=" + offer.getName() + "&category.id=" + offer.getCategory().getId(), HttpMethod.GET, entity, SearchOffers.class);
            similaryOffers.put(offer, new ArrayList<>());

            if (response.hasBody()) {
                similaryOffers.get(offer).addAll(response.getBody().getItems().getRegular());
                similaryOffers.get(offer).addAll(response.getBody().getItems().getPromoted());
            }


        }
        return similaryOffers;
    }
}
