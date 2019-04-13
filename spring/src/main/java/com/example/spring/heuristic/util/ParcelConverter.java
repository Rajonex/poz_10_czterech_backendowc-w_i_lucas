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

    public static List<Basket> convertSellersToParcels(List<Map<OfferSeller, OfferSellerParcel>> sellersParcelsPropositions){
        Integer mockId = 0;
        List<Basket> result = new LinkedList<>();
        for(Map<OfferSeller, OfferSellerParcel> sellersParcels: sellersParcelsPropositions) {
            Map<Parcel, List<ListingOffer>> parcels = new HashMap<>();
            for (Map.Entry<OfferSeller, OfferSellerParcel> sellerParcel : sellersParcels.entrySet()) {
                parcels.put(new Parcel(sellerParcel.getValue().getPrice(), mockId), sellerParcel.getValue().getListingOffers());
                mockId = mockId + 1;
            }
            result.add(new Basket(parcels));
        }
        return result;
    }


}
