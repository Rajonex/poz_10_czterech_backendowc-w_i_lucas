package com.example.spring.model;

public class OfferDelivery {
    Boolean availableForFree;
    OfferPrice lowestPrice;

    public Boolean getAvailableForFree() {
        return availableForFree;
    }

    public void setAvailableForFree(Boolean availableForFree) {
        this.availableForFree = availableForFree;
    }

    public OfferPrice getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(OfferPrice lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
}
