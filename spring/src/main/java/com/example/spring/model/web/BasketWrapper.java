package com.example.spring.model.web;

import com.example.spring.model.web.parts.Parcel;
import com.example.spring.model.ListingOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasketWrapper {
    private List<ParcelWrapper> parcels;

    public BasketWrapper() {
    }

    public BasketWrapper(List<ParcelWrapper> parcels) {
        this.parcels = parcels;
    }

    public static BasketWrapper from(Basket basket) {
        ArrayList<ParcelWrapper> parcelWrappers = new ArrayList<>();
        for(Map.Entry<Parcel, List<ListingOffer>> entry: basket.getParcels().entrySet()) {
            parcelWrappers.add(new ParcelWrapper(entry.getKey(), entry.getValue()));
        }

        return new BasketWrapper(parcelWrappers);
    }

    public List<ParcelWrapper> getParcels() {
        return parcels;
    }

    public void setParcels(List<ParcelWrapper> parcels) {
        this.parcels = parcels;
    }
}
