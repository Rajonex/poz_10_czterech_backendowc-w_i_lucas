package com.example.spring.model.web;


import java.util.ArrayList;
import java.util.List;

public class ListWrapperS {
    private List<MapWrapper> data;

    public ListWrapperS() {this.data = new ArrayList<>();}

    public ListWrapperS(List<MapWrapper> data) {
        this.data = data;
    }

    public List<MapWrapper> getData() {
        return data;
    }

    public void setData(List<MapWrapper> data) {
        this.data = data;
    }
}
