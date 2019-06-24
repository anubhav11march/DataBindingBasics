package com.example.mvvm;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mvvm.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Context ctx;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private RecyclerViewAdapter rva;
    private List<FoodList> orders;
    private int itemCount =0;
    private ActivityMainBinding binding;



    private MutableLiveData<String> stringLd;

    private static final String URL_DATA = "https://api.github.com/search/users?q=language:java+location:lagos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
//        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        loadJsondata();
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
//        rva = new RecyclerViewAdapter(orders, this);
//        vm model = ViewModelProviders.of(this).get(vm.class);
//        model.getFood().observe(this, new Observer<List<FoodList>>() {
//            @Override
//            public void onChanged(@Nullable List<FoodList> foodLists) {
//                adapter = new RVA(MainActivity.this, foodLists);
//                recyclerView.setAdapter(adapter);
//            }
//        });







//
        orders = new ArrayList<>();
        initializeAdapter();
        loadJsondata();
        recyclerView.addOnScrollListener(new recyclerViewOnClickListener(linearLayoutManager) {
            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public void loadMoreItems() {
                isLoading = true;
                currentPage++;
                loadJsondata();
            }

            @Override
            public boolean isloading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
//        loadJsondata();
//        initializeData();


        stringLd = new MutableLiveData<>();


        stringLd.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.setTitle(s);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stringLd.setValue("abcde");
            }
        }, 5000);

 new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stringLd.setValue("adsad");
            }
        }, 6000);


    }




//    public void initializeData(){
//        orders = new ArrayList<>();
//        orders.add(new Order("Rajma Chaawal", "Rs. 100", R.drawable.rajma));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//        orders.add(new Order("Chhole Bhature", "Rs. 40", R.drawable.cb));
//
//
//
//    }

    public void initializeAdapter(){
         rva = new RecyclerViewAdapter(orders, this, binding);
        recyclerView.setAdapter(rva);
    }

    public static int x=0;

    public void loadJsondata(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Log.v("AAA", "Load");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    Log.v("AAA", "Trying");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("items");
                    int i;
                    for (i = x; i < x+10; i++) {
                        JSONObject jo = array.getJSONObject(i);
                        Log.v("AAAA", ""+i);
                        FoodList food = new FoodList(jo.getString("login"), jo.getString("type"), jo.getInt("id"));
                        orders.add(food);
                    }
                    x=i;
                    adapter = new RecyclerViewAdapter(orders, getApplicationContext(), binding);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.v("AAA", "Caught");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAA", "Error");
                Toast.makeText(ctx, "Error in loading", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = 1;
        isLastPage = false;
        rva.clear();
        loadJsondata();
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
