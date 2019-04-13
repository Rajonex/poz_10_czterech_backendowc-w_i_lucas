package com.example.spring.service;

import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferPrice;
import com.example.spring.model.OfferSeller;
import com.example.spring.model.SearchOffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

@Component
public class LowestMaxMinSearcherService {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    public LowestMaxMinSearcherService(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OfferPrice searchLowestDeliveryPrice(OfferSeller seller) {
        OfferPrice maxLowestPrice = new OfferPrice();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Header", "value");
        headers.set(HttpHeaders.ACCEPT, "application/vnd.allegro.public.v1+json");

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<SearchOffers> response = restTemplate.exchange(
                "https://api.allegro.pl/offers/listing?seller.id=" + seller.getId(), HttpMethod.GET, entity, SearchOffers.class);
        for(ListingOffer offer : response.getBody().getItems().getRegular()) {
            if(maxLowestPrice != null){
                if(offer.getDelivery().getLowestPrice().getAmount() > maxLowestPrice.getAmount()){
                    maxLowestPrice = offer.getDelivery().getLowestPrice();
                }
            }else {
                maxLowestPrice = offer.getDelivery().getLowestPrice();
            }
        }
        for(ListingOffer offer : response.getBody().getItems().getPromoted()) {
            if(maxLowestPrice.getAmount() != null){
                if(offer.getDelivery().getLowestPrice().getAmount() > maxLowestPrice.getAmount())
                    maxLowestPrice = offer.getDelivery().getLowestPrice();
            }else {
                maxLowestPrice = offer.getDelivery().getLowestPrice();
            }
        }

        return maxLowestPrice;
    }
}
