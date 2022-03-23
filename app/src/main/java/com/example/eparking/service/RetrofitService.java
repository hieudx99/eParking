package com.example.eparking.service;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public interface RetrofitService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.104:8080/eParking/") //IP LAN
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

}
