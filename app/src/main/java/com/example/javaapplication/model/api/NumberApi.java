package com.example.javaapplication.model.api;

import com.example.javaapplication.model.data.NumberData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NumberApi {
    @GET("{selectedNumber}?json")
    Call<NumberData> getNumberData(@Path("selectedNumber") int selectedNumber);

    @GET("random/math?json")
    Call<NumberData> getRandomNumberData();
}
