package com.example.spring.model;

import java.util.ArrayList;
import java.util.List;

public class ListingResponseCategories {
    List<ListingCategory> subcategories;
    List<ListingCategory> path;

    public List<ListingCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<ListingCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public List<ListingCategory> getPath() {
        return path;
    }

    public void setPath(List<ListingCategory> path) {
        this.path = path;
    }
}
