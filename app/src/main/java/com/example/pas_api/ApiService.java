package com.example.pas_api;

import com.example.pas_api.response.ResponseFoodDetail;
import com.example.pas_api.response.ResponseMealsItem;
import com.example.pas_api.response.ResponseSearchFood;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiService {
    @GET("filter.php?c=Seafood")
    Call<ResponseMealsItem> getListFood();

    @GET("lookup.php")
    Call<ResponseFoodDetail> getResepFood(@Query("i") String i);

    @GET("search.php")
    Call<ResponseSearchFood> getSearchFood(@Query("s") String s);
}