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

    public FoodList(String name, String price, int picId){
        this.name = name;
        this.price = price;
        this.picId = picId;
    }
}
