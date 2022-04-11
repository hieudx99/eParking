package com.example.eparking.service;

import com.example.eparking.model.Bill;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BillService {
    BillService BILL_SERVICE = RetrofitService.retrofit.create(BillService.class);

    @POST("bill/create-bill")
    Call<Bill> createBill(@Body Bill bill);
}
