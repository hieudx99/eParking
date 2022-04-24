package com.example.eparking.service;

import com.example.eparking.model.Car;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CarService {

    CarService CAR_SERVICE = RetrofitService.retrofit.create(CarService.class);

    @POST("car/add-car")
    Call<Car> addCar(@Body Car car, @Query("userid") int userid);

    @POST("car/update-car")
    Call<Car> updateCar(@Body Car car, @Query("userid") int userid);

}
