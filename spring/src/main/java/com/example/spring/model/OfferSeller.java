package com.example.spring.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;


public class OfferSeller {
    String id;
    Boolean company;
    Boolean superSeller;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getCompany() {
        return company;
    }

    public void setCompany(Boolean company) {
        this.company = company;
    }

    public Boolean getSuperSeller() {
        return superSeller;
    }

    public void setSuperSeller(Boolean superSeller) {
        this.superSeller = superSeller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferSeller that = (OfferSeller) o;
        return id.equals(that.id) &&
                Objects.equals(company, that.company) &&
                Objects.equals(superSeller, that.superSeller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, superSeller);
    }
}
