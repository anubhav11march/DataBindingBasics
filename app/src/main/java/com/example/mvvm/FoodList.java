package com.example.mvvm;

public class FoodList {
    private String name, price;
    private int picId;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getPicId() {
        return picId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public void setPrice(String price) {
        this.price = "Rs. "+price;
    }

    public FoodList(String name, String price, int picId){
        this.name = name;
        this.price = price;
        this.picId = picId;
    }
}
