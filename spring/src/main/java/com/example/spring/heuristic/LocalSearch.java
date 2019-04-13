package com.example.spring.heuristic;

import com.example.spring.heuristic.model.OfferSellerParcel;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferSeller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalSearch implements Heuristic {

    boolean returnInitialResult;
    InitialHeuristic initialHeuristic;
    public LocalSearch(InitialHeuristic initialHeuristic, boolean returnInitialResult) {
        this.initialHeuristic = initialHeuristic;
        this.returnInitialResult = returnInitialResult;
    }

    @Override
    public List<Map<OfferSeller, OfferSellerParcel>> run(Map<ListingOffer, List<ListingOffer>> productsAndSimilarities, Integer maxParcels) {
        List<Map<OfferSeller, OfferSellerParcel>> result = new LinkedList<>();
        List<Map<OfferSeller, OfferSellerParcel>> initialHeuristicResult = initialHeuristic.run(productsAndSimilarities, maxParcels);
        if(returnInitialResult){
            result.addAll(initialHeuristicResult);
        }

        Map<OfferSeller, OfferSellerParcel> initialHeuristicParcels = initialHeuristicResult.get(0);

        Set<OfferSeller> sellers = initialHeuristicParcels.keySet();
        Map<ListingOffer, ListingOffer> originalToSimilarity = initialHeuristic.getCachedSimilaritiesConverts();

        for(Map.Entry<ListingOffer, List<ListingOffer>> productAndSimilarities: productsAndSimilarities.entrySet()){
            ListingOffer similarity = originalToSimilarity.get(productAndSimilarities.getKey());
            OfferSellerParcel similarityOfferSellerParcel = initialHeuristicParcels.get(similarity.getSeller());
            Double similarityPrice = similarity.getSellingMode().getPrice().getAmount();
            List<ListingOffer> mySellersNewSimilarities = filterListingOfferWithChosenSellers(productAndSimilarities.getValue(), sellers);

            // TO DO iterate by neighbourhood and change seller for profit and minimalize number of parcels (edit result from initialHeuristicResult - copy)
            if(similarityOfferSellerParcel.getFree()){

            }
        }


        return result;
    }

    private List<ListingOffer> filterListingOfferWithChosenSellers(List<ListingOffer> listingOffers, Set<OfferSeller> sellers){
        List<ListingOffer> mySellersListingOffers = new LinkedList<>();
        for(ListingOffer newSimilarity: listingOffers){
            if(sellers.contains(newSimilarity)){
                mySellersListingOffers.add(newSimilarity);
            }
        }
        return mySellersListingOffers;
    }
}
