package com.example.spring.model;

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
