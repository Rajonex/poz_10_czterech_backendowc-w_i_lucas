package com.example.spring.model;

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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }
}
