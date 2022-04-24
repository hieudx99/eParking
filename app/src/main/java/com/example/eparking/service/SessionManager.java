package com.example.eparking.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eparking.view.AppConfig;
import com.example.eparking.view.MyApplication;

public class SessionManager {
    private SharedPreferences sharedPreferences;

    public SessionManager() {
//        sharedPreferences = mContext.getSharedPreferences(AppConfig.MyPREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences = MyApplication.getAppContext().getSharedPreferences(AppConfig.MyPREFERENCES, Context.MODE_PRIVATE);

    }

    public void saveJwtToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConfig.TOKEN, token);
        editor.commit();
    }

    public String fetchJwtToken() {
        return sharedPreferences.getString(AppConfig.TOKEN, "");
    }

    public void clearJwtToken() {
        sharedPreferences.edit().remove(AppConfig.TOKEN).commit();
    }
}
