package com.example.databindingex;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class Food extends BaseObservable {
    private String name;
    private String price;
    public Food(String name, String price){
        this.name = name;
        this.price = price;
    }

    public Food(){

    }
    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
