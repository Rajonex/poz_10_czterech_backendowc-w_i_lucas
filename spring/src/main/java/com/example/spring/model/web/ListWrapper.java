package com.example.spring.model.web;


import java.util.ArrayList;
import java.util.List;

public class ListWrapper<T> {
    private List<T> data;

    public ListWrapper() {this.data = new ArrayList<>();}

    public ListWrapper(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
