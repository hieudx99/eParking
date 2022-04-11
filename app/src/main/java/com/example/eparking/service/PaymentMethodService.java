package com.example.eparking.service;

import com.example.eparking.model.PaymentMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentMethodService {

    PaymentMethodService PAYMENT_METHOD_SERVICE = RetrofitService.retrofit.create(PaymentMethodService.class);

    @GET("payment-method/get-list-payment-method")
    Call<List<PaymentMethod>> getListPaymentMethod();

}
