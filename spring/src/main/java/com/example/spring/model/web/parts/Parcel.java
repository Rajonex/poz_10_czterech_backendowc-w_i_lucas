package com.example.spring.model.web.parts;

import java.util.Objects;

public class Parcel {

    private Double price;
    private Integer id;

    public Parcel(Double price, Integer id) {
        this.price = price;
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcel parcel = (Parcel) o;
        return price.equals(parcel.price) &&
                id.equals(parcel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, id);
    }
}
