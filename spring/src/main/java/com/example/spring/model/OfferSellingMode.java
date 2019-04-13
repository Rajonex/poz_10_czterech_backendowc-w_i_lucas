package com.example.spring.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferSellingMode {
    String format;
    OfferPrice price;
    OfferPrice fixedPrice;
    Integer popularity;
    Integer bidCount;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public OfferPrice getPrice() {
        return price;
    }

    public void setPrice(OfferPrice price) {
        this.price = price;
    }

    public OfferPrice getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(OfferPrice fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }
}
