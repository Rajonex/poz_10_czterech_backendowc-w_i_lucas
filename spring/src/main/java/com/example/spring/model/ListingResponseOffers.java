package com.example.spring.model;

import java.util.List;

public class ListingResponseOffers {
    List<ListingOffer> promoted;
    List<ListingOffer> regular;

    public List<ListingOffer> getPromoted() {
        return promoted;
    }

    public void setPromoted(List<ListingOffer> promoted) {
        this.promoted = promoted;
    }

    public List<ListingOffer> getRegular() {
        return regular;
    }

    public void setRegular(List<ListingOffer> regular) {
        this.regular = regular;
    }
}
