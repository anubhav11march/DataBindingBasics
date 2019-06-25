package com.example.mvvm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVA extends RecyclerView.Adapter<RVA.ViewHolderr> {

    Context ctx;
    List<FoodList> foodList;

    public RVA (Context ctx, List<FoodList> foodList){
        this.ctx = ctx;
        this.foodList = foodList;
    }



    @NonNull
    @Override
    public ViewHolderr onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.rvlayout, viewGroup, false);
        return new ViewHolderr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderr viewHolderr, int i) {
        FoodList food = foodList.get(i);
        viewHolderr.name.setText(food.getName());
        viewHolderr.name.setText(food.getPrice());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class ViewHolderr extends RecyclerView.ViewHolder {
        TextView name, price;
        public ViewHolderr(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
