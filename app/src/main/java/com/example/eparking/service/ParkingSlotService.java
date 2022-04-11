package com.example.eparking.service;

import com.example.eparking.model.ParkingSlot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ParkingSlotService {

    ParkingSlotService PARKING_SLOT_SERVICE = RetrofitService.retrofit.create(ParkingSlotService.class);

    @GET("parking-slot/find-all")
    Call<List<ParkingSlot>> findAll();
}
