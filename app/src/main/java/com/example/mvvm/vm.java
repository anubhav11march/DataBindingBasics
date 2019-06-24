package com.example.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class vm extends ViewModel {

    private MutableLiveData<List<FoodList>> foodList;
     public LiveData<List<FoodList>> getFood(){
         if(foodList == null){
             foodList = new MutableLiveData<List<FoodList>>();
             loadFoood();
         }
         return foodList;
     }

     private void loadFoood(){
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Api.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         Api api = retrofit.create(Api.class);
         Call<List<FoodList>> call = api.getFoods();

         call.enqueue(new Callback<List<FoodList>>() {
             @Override
             public void onResponse(Call<List<FoodList>> call, Response<List<FoodList>> response) {
                 foodList.setValue(response.body());
             }

             @Override
             public void onFailure(Call<List<FoodList>> call, Throwable t) {

             }
         });
     }
}
