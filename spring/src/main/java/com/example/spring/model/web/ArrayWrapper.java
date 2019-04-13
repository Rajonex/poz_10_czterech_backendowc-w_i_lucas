package com.example.spring.model.web;


import java.util.ArrayList;
import java.util.List;

public class ArrayWrapper {
    private List<MapWrapper> data;

    public ArrayWrapper() {this.data = new ArrayList<>();}

    public ArrayWrapper(List<MapWrapper> data) {
        this.data = data;
    }

    public List<MapWrapper> getData() {
        return data;
    }

    public void setData(List<MapWrapper> data) {
        this.data = data;
    }
}
