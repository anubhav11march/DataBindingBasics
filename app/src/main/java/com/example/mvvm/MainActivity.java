package com.example.mvvm;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mvvm.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import android.support.v7.widget.helper.ItemTouchHelper;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
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

    private static final String URL_DATA = "https://api.androidhive.info/json/menu.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

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
        orders = new ArrayList<>();
        initializeAdapter();
        loadJsonData1();
        ItemTouchHelper.SimpleCallback itemtouchhelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemtouchhelperCallback).attachToRecyclerView(recyclerView);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |  ItemTouchHelper.UP) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                rva.removeItem(i);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);



//

//        recyclerView.addOnScrollListener(new recyclerViewOnClickListener(linearLayoutManager) {
//            @Override
//            public int getTotalPageCount() {
//                return 0;
//            }
//
//            @Override
//            public void loadMoreItems() {
//                isLoading = true;
//                currentPage++;
//                loadJsondata();
//            }
//
//            @Override
//            public boolean isloading() {
//                return isLoading;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//        });
//        loadJsondata();
//        initializeData();


//        stringLd = new MutableLiveData<>();
//
//
//        stringLd.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                binding.setTitle(s);
//            }
//        });
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                stringLd.setValue("abcde");
//            }
//        }, 5000);
//
// new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                stringLd.setValue("adsad");
//            }
//        }, 6000);


    }

//    private void prepareCart() {
//        JsonArrayRequest request = new JsonArrayRequest(URL,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        if (response == null) {
//                            Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                        List<FoodList> items = new Gson().fromJson(response.toString(), new TypeToken<List<FoodList>>() {
//                        }.getType());
//
//                        // adding items to cart list
//                        cartList.clear();
//                        cartList.addAll(items);
//
//                        // refreshing recycler view
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // error in getting json
//                Log.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        MyApplication.getInstance().addToRequestQueue(request);
//    }





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

    public void loadJsonData1(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(URL_DATA,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(ctx, "Failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<FoodList> foodList = new Gson().fromJson(response.toString(), new
                                TypeToken<List<FoodList>>() {

                                }.getType()
                        );
                        orders.clear();
                        orders.addAll(foodList);
                        rva.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "Errorrrrr", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }


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
                    for (i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        Log.v("AAAA", ""+i);
                        FoodList food = new FoodList(jo.getString("login"), jo.getString("type"), jo.getInt("id"));
                        orders.add(food);
                    }

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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerViewAdapter.viewHolder) {
            // get the removed item name to display it in snack bar
            String name = orders.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final FoodList deletedItem = orders.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            rva.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // undo is selected, restore the deleted item
//                    mAdapter.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
        }
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
