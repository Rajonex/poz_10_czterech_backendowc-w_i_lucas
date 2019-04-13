package com.example.spring.heuristic;

import com.example.spring.heuristic.model.OfferSellerParcel;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferSeller;
import com.example.spring.service.LowestMaxMinSearcherService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class GreedyHeuristic implements InitialHeuristic {

    @Autowired
    private LowestMaxMinSearcherService lowestPrice;

    private boolean cacheChosenSimilarities;
    private Map<ListingOffer, ListingOffer> cachedSimilaritiesConverts;

    public GreedyHeuristic(boolean cacheChosenSimilarities) {
        this.cacheChosenSimilarities = cacheChosenSimilarities;
        this.cachedSimilaritiesConverts = new HashMap<>();

    }

    @Override
    public List<Map<OfferSeller, OfferSellerParcel>> run(Map<ListingOffer, List<ListingOffer>> productsAndSimilarities, Integer maxParcels) {
        Map<OfferSeller, OfferSellerParcel> sellersParcels = new HashMap<>();




        for(Map.Entry<ListingOffer, List<ListingOffer>> productAndSimilarities: productsAndSimilarities.entrySet()){
            List<ListingOffer> similarities = productAndSimilarities.getValue();

            if(sellersParcels.size() >= maxParcels){
                List<ListingOffer> allowedSimilarities = getAllowedSimilarities(sellersParcels.keySet(), similarities);
                similarities = (allowedSimilarities == null || allowedSimilarities.size() < 1) ?
                        similarities : allowedSimilarities;
            }

            Double price = Double.MAX_VALUE;
            ListingOffer product = null;

            for(ListingOffer similarity: similarities){
                Double similarityPrice = similarity.getSellingMode().getPrice().getAmount();
                if(sellersParcels.containsKey(similarity.getSeller())){
                    OfferSellerParcel offerSellerParcel = sellersParcels.get(similarity.getSeller());
                    int sellerParcelSize = offerSellerParcel.getListingOffers().size();
                    if(!offerSellerParcel.getFree() || !similarity.getDelivery().getAvailableForFree()){
                        similarityPrice += lowestPrice.searchLowestDeliveryPrice(similarity.getSeller()).getAmount(); // maxSellerLowestPrice()

                    }
//                    if(sellerParcelSize == 1){
//
//                    } else if(sellerParcelSize > 1){
//
//                    }

                } else{
                    similarityPrice += similarity.getSellingMode().getPrice().getAmount();
                }

                if(price > similarityPrice){
                    price = similarityPrice;
                    product = similarity;
                }
            }

            if(sellersParcels.containsKey(product.getSeller())){
                OfferSellerParcel offerSellerParcel = sellersParcels.get(product.getSeller());
                offerSellerParcel.addListingOffer(product);
                if(offerSellerParcel.getFree() && !product.getDelivery().getAvailableForFree()){
                    offerSellerParcel.setPrice(lowestPrice.searchLowestDeliveryPrice(product.getSeller()).getAmount()); // maxSellerLowestPrice()
                    offerSellerParcel.setFree(false);
                }
            } else{
                OfferSellerParcel offerSellerParcel = new OfferSellerParcel();
                offerSellerParcel.setFree(product.getDelivery().getAvailableForFree());
                offerSellerParcel.setPrice(product.getDelivery().getLowestPrice().getAmount());
                offerSellerParcel.addListingOffer(product);
                sellersParcels.put(product.getSeller(), offerSellerParcel);
            }

            cachedSimilaritiesConverts.put(productAndSimilarities.getKey(), product);
        }

        List<Map<OfferSeller, OfferSellerParcel>> result = new LinkedList<>();
        result.add(sellersParcels);
        return result;

    }

    public Map<ListingOffer, ListingOffer> getCachedSimilaritiesConverts() {
        if(cacheChosenSimilarities) {
            return cachedSimilaritiesConverts;
        }
        throw new IllegalStateException("Cache not declared");
    }

    List<ListingOffer> getAllowedSimilarities(Set<OfferSeller> sellers, List<ListingOffer> similarities){
        List<ListingOffer> allowedSimilarities = new LinkedList<>();
        for(ListingOffer similarity: similarities){
            if(sellers.contains(similarity.getSeller())){
                allowedSimilarities.add(similarity);
            }
        }

        return allowedSimilarities;
    }
}
