package com.example.eparking.service;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public interface RetrofitService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://e-parking-api.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

}
