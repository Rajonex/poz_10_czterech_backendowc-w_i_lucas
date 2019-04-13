package com.example.spring.model.web;

import com.example.spring.model.ListingOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapWrapper {
    private ListingOffer key;
    private List<ListingOffer> value;

    public MapWrapper() {
        this.value = new ArrayList<>();
    }

    public MapWrapper(ListingOffer key, List<ListingOffer> value) {
        this.key = key;
        this.value = value;
    }

    public MapWrapper(Map.Entry<ListingOffer, List<ListingOffer>> entry) {
        this.key = entry.getKey();
        this.value = entry.getValue();
    }

    public ListingOffer getKey() {
        return key;
    }

    public void setKey(ListingOffer key) {
        this.key = key;
    }

    public List<ListingOffer> getValue() {
        return value;
    }

    public void setValue(List<ListingOffer> value) {
        this.value = value;
    }
}
