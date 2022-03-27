package com.example.eparking.service;

import com.example.eparking.model.User;
import com.example.eparking.model.dto.Credential;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
//    baseURL = "http://192.168.1.9:8080/eParking"

    UserService USER_SERVICE = RetrofitService.retrofit.create(UserService.class);

//    http://192.168.1.9:8080/eParking/customer/login
    @POST("user/login")
    Call<User> checkLogin(@Body Credential credential);

    @POST("user/register")
    Call<User> register(@Body User user);
}