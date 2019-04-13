package com.example.spring.model.web;

import com.example.spring.model.ListingOffer;
import com.example.spring.model.web.parts.Parcel;

import java.util.List;

public class ParcelWrapper {

    private Parcel parcel;
    private List<ListingOffer> items;

    public ParcelWrapper(Parcel parcel, List<ListingOffer> items) {
        this.parcel = parcel;
        this.items = items;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public List<ListingOffer> getItems() {
        return items;
    }

    public void setItems(List<ListingOffer> items) {
        this.items = items;
    }
}
