package com.example.eparking.service;

import com.example.eparking.model.Bill;
import com.example.eparking.model.dto.StartEndDateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BillService {
    BillService BILL_SERVICE = RetrofitService.retrofit.create(BillService.class);

    @POST("bill/create-bill")
    Call<Bill> createBill(@Body Bill bill);

    @POST("bill/parking-history")
    Call<List<Bill>> getUserParkingHistory(@Body StartEndDateDTO dateDTO, @Query("userid") int userid);

    @GET("bill/get-bill-by-parking-slot")
    Call<Bill> getBillByParkingSlot(@Query("parkingSlotId") int parkingSlotId);
}
