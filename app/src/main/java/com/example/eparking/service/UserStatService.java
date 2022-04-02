package com.example.eparking.service;

import com.example.eparking.model.UserStat;
import com.example.eparking.model.dto.StartEndDateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserStatService {
    UserStatService USER_STAT_SERVICE = RetrofitService.retrofit.create(UserStatService.class);

    @POST("user-stat")
    Call<List<UserStat>> getListUserStat(@Body StartEndDateDTO dateDTO);
}
