package com.example.mvvm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context ctx;
    private List<Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(llm);
        RecyclerViewAdapter rva = new RecyclerViewAdapter(orders);
        initializeData();
        initializeAdapter();
    }
    public void initializeData(){
        orders = new ArrayList<>();
        orders.add(new Order("Rajma Chaawal", "Rs. 100", R.drawable.rajma));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));



    }

    public void initializeAdapter(){
        RecyclerViewAdapter rva = new RecyclerViewAdapter(orders);
        recyclerView.setAdapter(rva);
    }

    class Order{
        String name, price;
        int picId;
        Order(String name, String price, int picId){
            this.name = name;
            this. price = price;
            this.picId = picId;
        }
    }
}
