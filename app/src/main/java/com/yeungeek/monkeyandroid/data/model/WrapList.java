package com.yeungeek.monkeyandroid.data.model;

import java.util.List;

/**
 * Created by yeungeek on 2016/4/3.
 */
public class WrapList<T> {
    private List<T> items;
    private int total_count;
    private boolean incomplete_results;

    public List<T> getItems() {
        return items;
    }

    public WrapList setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public int getTotal_count() {
        return total_count;
    }

    public WrapList setTotal_count(int total_count) {
        this.total_count = total_count;
        return this;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public WrapList setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
        return this;
    }
}
