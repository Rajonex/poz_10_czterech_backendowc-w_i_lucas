package com.example.spring.heuristic.util;

import com.example.spring.heuristic.model.OfferSellerParcel;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferSeller;
import com.example.spring.model.web.Basket;
import com.example.spring.model.web.parts.Parcel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParcelConverter {

    public static List<Basket> convertSellersToParcels(Map<OfferSeller, OfferSellerParcel> sellersParcels){

        Map<Parcel, List<ListingOffer>> parcels = new HashMap<>();
        Integer mockId = 0;
        for(Map.Entry<OfferSeller, OfferSellerParcel> sellerParcel: sellersParcels.entrySet()){
            parcels.put(new Parcel(sellerParcel.getValue().getPrice(), mockId), sellerParcel.getValue().getListingOffers());
            mockId = mockId+1;
        }

        List<Basket> result = new LinkedList<>();
        result.add(new Basket(parcels));
        return result;
    }


}
