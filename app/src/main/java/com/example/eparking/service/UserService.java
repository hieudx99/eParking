package com.example.eparking.service;

import com.example.eparking.model.User;
import com.example.eparking.model.dto.UserCredential;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
//    baseURL = "http://192.168.1.9:8080/eParking"

    UserService USER_SERVICE = RetrofitService.retrofit.create(UserService.class);

//    http://192.168.1.9:8080/eParking/customer/login
    @POST("user/auth/login")
    Call<User> checkLogin(@Body UserCredential userCredential);

    @POST("user/register")
    Call<User> register(@Body User user);

    @GET("user/search-user")
    Call<List<User>> searchUser(@Query("kw") String kw);

    @PUT("user/update-info")
    Call<User> updateUser(@Body User user);

    @POST("user/auth/google")
    Call<User> google(@Body String googleAccessToken);


}
