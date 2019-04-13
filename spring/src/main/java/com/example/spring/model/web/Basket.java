package com.example.spring.model.web;

import com.example.spring.model.ListingOffer;
import com.example.spring.model.web.parts.Parcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    Map<Parcel, List<ListingOffer>> parcels;

    public Basket() {
        parcels = new HashMap<>();
    }

    public Basket(Map<Parcel, List<ListingOffer>> parcels) {
        this.parcels = parcels;
    }

    public Map<Parcel, List<ListingOffer>> getParcels() {
        return parcels;
    }

    public void addParcel(Parcel parcel, List<ListingOffer> listingOffers){
        parcels.put(parcel, listingOffers);
    }
}
