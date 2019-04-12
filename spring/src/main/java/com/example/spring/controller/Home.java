package com.example.spring.controller;

import com.example.spring.model.SearchOffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class Home {

    @Autowired
    private OAuth2RestTemplate restTemplate;


    @GetMapping("/test")
    public ResponseEntity<?> getSomething() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Header", "value");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<SearchOffers> response = restTemplate.exchange(
                "https://api.allegro.pl/offers/listing?phrase=xiaomi+pocophone+128GB", HttpMethod.GET, entity, SearchOffers.class);

        return ResponseEntity.ok("tes");
    }
}
