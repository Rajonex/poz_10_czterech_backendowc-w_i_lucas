package com.example.spring.heuristic;

import com.example.spring.heuristic.model.OfferSellerParcel;
import com.example.spring.model.ListingOffer;
import com.example.spring.model.OfferSeller;
import com.example.spring.service.LowestPriceSearcherService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class LocalSearch implements Heuristic {

    @Autowired
    private LowestPriceSearcherService lowestPrice;

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

        Map<OfferSeller, OfferSellerParcel> initialHeuristicParcelsOriginal = initialHeuristicResult.get(0);

        Map<OfferSeller, OfferSellerParcel> initialHeuristicParcels = new HashMap<>(initialHeuristicParcelsOriginal);


        Map<ListingOffer, ListingOffer> originalToSimilarity = initialHeuristic.getCachedSimilaritiesConverts();

        for(Map.Entry<ListingOffer, List<ListingOffer>> productAndSimilarities: productsAndSimilarities.entrySet()){
            Set<OfferSeller> sellers = initialHeuristicParcels.keySet();

            ListingOffer similarity = originalToSimilarity.get(productAndSimilarities.getKey());
            OfferSellerParcel similarityOfferSellerParcel = initialHeuristicParcels.get(similarity.getSeller());
            Double similarityPrice = similarity.getSellingMode().getPrice().getAmount();
            List<ListingOffer> mySellersNewSimilarities = filterListingOfferWithChosenSellers(productAndSimilarities.getValue(), sellers);


            // TO DO iterate by neighbourhood and change seller for profit and minimalize number of parcels (edit result from initialHeuristicResult - copy)
            if(similarityOfferSellerParcel.getFree()){
                Double newSimilarityPrice = similarityPrice;
                ListingOffer newSimilarity = similarity;

                for(ListingOffer mySellersNewSimilarity: mySellersNewSimilarities){
                    OfferSellerParcel mySellersNewSimilarityParcel = initialHeuristicParcels.get(mySellersNewSimilarity.getSeller());
                    if(mySellersNewSimilarityParcel.getFree() && mySellersNewSimilarity.getDelivery().getAvailableForFree()) {
                        if(mySellersNewSimilarity.getSellingMode().getPrice().getAmount() < newSimilarityPrice) {
                            newSimilarityPrice = mySellersNewSimilarity.getSellingMode().getPrice().getAmount();
                            newSimilarity = mySellersNewSimilarity;
                        }
                    }else if(mySellersNewSimilarityParcel.getFree() && !mySellersNewSimilarity.getDelivery().getAvailableForFree()){
                        if(mySellersNewSimilarityParcel.getListingOffers().size() > 0){
                            Double addingProductFromThisSellerDeliveryPrice = lowestPrice.searchLowestDeliveryPrice(mySellersNewSimilarity.getSeller()).getAmount();
                            if(newSimilarityPrice > mySellersNewSimilarity.getSellingMode().getPrice().getAmount() + addingProductFromThisSellerDeliveryPrice){
                                newSimilarityPrice = mySellersNewSimilarity.getSellingMode().getPrice().getAmount() + addingProductFromThisSellerDeliveryPrice;
                                newSimilarity = mySellersNewSimilarity;
                            }
                        }
                    }else if(!mySellersNewSimilarityParcel.getFree()){
                        if(mySellersNewSimilarityParcel.getListingOffers().size() == 1){
                            Double addingProductFromThisSellerDeliveryPrice = lowestPrice.searchLowestDeliveryPrice(mySellersNewSimilarity.getSeller()).getAmount();
                            if(newSimilarityPrice > addingProductFromThisSellerDeliveryPrice){
                                newSimilarityPrice = addingProductFromThisSellerDeliveryPrice;
                                newSimilarity = mySellersNewSimilarity;
                            }
                        }else {
                            Double addingProductFromThisSellerDeliveryPrice = mySellersNewSimilarity.getSellingMode().getPrice().getAmount();
                            if(newSimilarityPrice > addingProductFromThisSellerDeliveryPrice){
                                newSimilarityPrice = addingProductFromThisSellerDeliveryPrice;
                                newSimilarity = mySellersNewSimilarity;
                            }
                        }

                    }
                }
                if(!newSimilarity.equals(similarity)){
                    OfferSellerParcel oldParcel = initialHeuristicParcels.get(similarity.getSeller());
                    OfferSellerParcel newParcel = initialHeuristicParcels.get(newSimilarity.getSeller());
                    if(newParcel.getFree() && newSimilarity.getDelivery().getAvailableForFree()){
                        newParcel.addListingOffer(newSimilarity);
                    }else if(newParcel.getFree() && !newSimilarity.getDelivery().getAvailableForFree()){
                        if(newParcel.getListingOffers().size() >= 1){
                            newParcel.setPrice(lowestPrice.searchLowestDeliveryPrice(newSimilarity.getSeller()).getAmount());
                            newParcel.addListingOffer(newSimilarity);
                        }else{
                            newParcel.setPrice(newSimilarity.getDelivery().getLowestPrice().getAmount());
                            newParcel.addListingOffer(newSimilarity);
                        }
                    } if(newParcel.getListingOffers().size() == 1){
                        newParcel.setPrice(lowestPrice.searchLowestDeliveryPrice(similarity.getSeller()).getAmount());
                        newParcel.addListingOffer(newSimilarity);
                    }else{
                        newParcel.addListingOffer(newSimilarity);
                    }
                    checkOldParcel(oldParcel, similarity, initialHeuristicParcels);

                }
                //

            }
        }
        result.add(initialHeuristicParcels);

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

    private void checkOldParcel(OfferSellerParcel oldParcel, ListingOffer similarity, Map<OfferSeller, OfferSellerParcel> initialHeuristicParcels) {
        if(oldParcel.getListingOffers().size() == 1) {
            initialHeuristicParcels.remove(similarity.getSeller());
        } else if(oldParcel.getListingOffers().size() == 2) {
            oldParcel.getListingOffers().remove(similarity);
            oldParcel.setPrice(oldParcel.getListingOffers().get(0).getDelivery().getLowestPrice().getAmount());
        } else {
            oldParcel.getListingOffers().remove(similarity);
        }
    }
}
