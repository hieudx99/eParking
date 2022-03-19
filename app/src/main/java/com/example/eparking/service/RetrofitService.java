package com.example.eparking.service;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public interface RetrofitService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.9:8080/") //IP LAN
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

}
