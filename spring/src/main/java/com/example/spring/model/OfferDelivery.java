package com.example.spring.model;

public class OfferDelivery {
    Boolean availableForFree;
    OfferPrice lowestPrice;

    public boolean isAvailableForFree() {
        return availableForFree;
    }

    public void setAvailableForFree(boolean availableForFree) {
        this.availableForFree = availableForFree;
    }

    public OfferPrice getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(OfferPrice lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
}
