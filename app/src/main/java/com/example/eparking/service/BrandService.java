package com.example.eparking.service;

import com.example.eparking.model.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BrandService {
    BrandService BRAND_SERVICE = RetrofitService.retrofit.create(BrandService.class);

    @GET("brand/get-all")
    Call<List<Brand>> findAll();

}
