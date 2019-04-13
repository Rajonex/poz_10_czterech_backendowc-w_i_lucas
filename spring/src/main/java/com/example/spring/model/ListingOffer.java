package com.example.spring.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

public class ListingOffer {
    String id;
    String name;
    OfferSeller seller;
    OfferDelivery delivery;
    OfferSellingMode sellingMode;
    OfferCategory category;

    public List<OfferImages> getImages() {
        return images;
    }

    public void setImages(List<OfferImages> images) {
        this.images = images;
    }

    List<OfferImages> images;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingOffer that = (ListingOffer) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
