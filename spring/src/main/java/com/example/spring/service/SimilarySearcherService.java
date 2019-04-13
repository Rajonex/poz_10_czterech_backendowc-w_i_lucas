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

    public Map<ListingOffer, List<ListingOffer>> searchSimilaryOffers(List<String> offers) {
        Map<ListingOffer, List<ListingOffer>> similaryOffers = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Header", "value");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);

        ListingOffer key;

        for (String offer : offers) {
            ResponseEntity<SearchOffers> response = restTemplate.exchange(
                    "https://api.allegro.pl/offers/listing?phrase=" + offer, HttpMethod.GET, entity, SearchOffers.class);
            try{
                key = response.getBody().getItems().getRegular().get(0);
                similaryOffers.put(key, response.getBody().getItems().getRegular());
                similaryOffers.get(key).addAll(response.getBody().getItems().getPromoted());
            }catch (NullPointerException ex){
                System.out.println("nic nie znalazłem");
            }

        }
        return similaryOffers;
    }
}
