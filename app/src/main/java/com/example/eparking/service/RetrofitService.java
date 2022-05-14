package com.example.eparking.service;

import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public interface RetrofitService {

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            SessionManager sessionManager = new SessionManager();
            String token = sessionManager.fetchJwtToken();
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", token).build();

            return chain.proceed(newRequest);
        }
    }).build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
//            .baseUrl("https://e-parking-api.herokuapp.com/")
            .baseUrl("http://10.0.2.2:8080/eParking/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

}
