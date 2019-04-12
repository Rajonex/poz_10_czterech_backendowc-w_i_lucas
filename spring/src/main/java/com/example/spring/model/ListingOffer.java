package com.example.spring.model;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
=======
import java.util.Objects;

>>>>>>> 4bd44e7621301c3666d46b8eb6bb32936db67fe6
public class ListingOffer {
    String id;
    String name;
    OfferSeller seller;
    OfferDelivery delivery;
    OfferSellingMode sellingMode;
    OfferCategory category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfferSeller getSeller() {
        return seller;
    }

    public void setSeller(OfferSeller seller) {
        this.seller = seller;
    }

    public OfferDelivery getDelivery() {
        return delivery;
    }

    public void setDelivery(OfferDelivery delivery) {
        this.delivery = delivery;
    }

    public OfferSellingMode getSellingMode() {
        return sellingMode;
    }

    public void setSellingMode(OfferSellingMode sellingMode) {
        this.sellingMode = sellingMode;
    }

    public OfferCategory getCategory() {
        return category;
    }

    public void setCategory(OfferCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingOffer that = (ListingOffer) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
