package com.example.databindingex;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databindingex.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ClickHandler clickHandler;
    private TextView name, price;
    private Food food = new Food("Chhole Bhature", "Rs. 80");
    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setFood(food);

        //Button
        clickHandler = new ClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);
    }

    public class ClickHandler{
        Context ctx;

        public ClickHandler(Context ctx){
            this.ctx = ctx;
        }

        public void onBuyClicked(View view){
            Toast.makeText(ctx, "Bought!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this , Main2Activity.class));
        }

        public void onCancelClicked(View view){
            Toast.makeText(ctx, "Cancelled!", Toast.LENGTH_SHORT).show();
        }


    }
}
