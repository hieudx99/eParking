package com.example.eparking.service;

import com.example.eparking.model.Customer;
import com.example.eparking.model.dto.Credential;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerService {
//    baseURL = "http://192.168.1.9:8080/eParking"

    CustomerService customerService = RetrofitService.retrofit.create(CustomerService.class);

//    http://192.168.1.9:8080/eParking/customer/login
    @POST("customer/login")
    Call<Customer> checkLogin(@Body Credential credential);

    @POST("customer/register")
    Call<Customer> register(@Body Customer customer);
}
