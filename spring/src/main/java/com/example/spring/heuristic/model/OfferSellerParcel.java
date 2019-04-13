package com.example.spring.heuristic.model;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;
import java.util.List;

public class OfferSellerParcel {
    private List<ListingOffer> listingOffers;
    private Double price;
    private Boolean isFree;

    public OfferSellerParcel() {
        this.isFree = true;
        this.price = 0.0;
        this.listingOffers = new ArrayList<>();
    }

    public List<ListingOffer> getListingOffers() {
        return listingOffers;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public void addListingOffer(ListingOffer listingOffer){
        listingOffers.add(listingOffer);
    }
}
