package com.example.spring.model;

public class ListingOffer {
    String id;
    String name;
    OfferSeller seller;
    OfferDelivery delivery;
    OfferSellingMode sellingMode;
    OfferCategory category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfferSeller getSeller() {
        return seller;
    }

    public void setSeller(OfferSeller seller) {
        this.seller = seller;
    }

    public OfferDelivery getDelivery() {
        return delivery;
    }

    public void setDelivery(OfferDelivery delivery) {
        this.delivery = delivery;
    }

    public OfferSellingMode getSellingMode() {
        return sellingMode;
    }

    public void setSellingMode(OfferSellingMode sellingMode) {
        this.sellingMode = sellingMode;
    }

    public OfferCategory getCategory() {
        return category;
    }

    public void setCategory(OfferCategory category) {
        this.category = category;
    }
}
