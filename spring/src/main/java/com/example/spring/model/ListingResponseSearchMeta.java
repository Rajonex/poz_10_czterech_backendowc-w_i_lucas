package com.example.spring.model;

public class ListingResponseSearchMeta {
    Integer availableCount;
    Integer totalCount;
    Boolean fallback;

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getFallback() {
        return fallback;
    }

    public void setFallback(Boolean fallback) {
        this.fallback = fallback;
    }
}
