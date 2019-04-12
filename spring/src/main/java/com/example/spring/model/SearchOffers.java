package com.example.spring.model;

public class SearchOffers {
    ListingResponseOffers items;
    ListingResponseCategories categories;
    ListingResponseSearchMeta searchMeta;

    public ListingResponseOffers getItems() {
        return items;
    }

    public void setItems(ListingResponseOffers items) {
        this.items = items;
    }

    public ListingResponseCategories getCategories() {
        return categories;
    }

    public void setCategories(ListingResponseCategories categories) {
        this.categories = categories;
    }

    public ListingResponseSearchMeta getSearchMeta() {
        return searchMeta;
    }

    public void setSearchMeta(ListingResponseSearchMeta searchMeta) {
        this.searchMeta = searchMeta;
    }
}
