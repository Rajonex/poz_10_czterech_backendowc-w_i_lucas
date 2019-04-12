package com.example.spring.model;

public class OfferSeller {
    String id;
    boolean company;
    boolean superSeller;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

    public boolean isSuperSeller() {
        return superSeller;
    }

    public void setSuperSeller(boolean superSeller) {
        this.superSeller = superSeller;
    }
}
