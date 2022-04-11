package com.example.eparking.view.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.User;
import com.example.eparking.view.LoginActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private ImageView toolbar_logout_icon;
    private TextView toolbar_title;
    private TextView txt_fullname;
    private ImageButton btn_parking_info;
    private ImageButton btn_user_info;
    private ImageButton btn_statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_activity);

        toolbar_logout_icon = findViewById(R.id.toolbar_logout_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        txt_fullname = findViewById(R.id.txt_fullname);
        btn_parking_info = findViewById(R.id.btn_parking_info);
        btn_user_info = findViewById(R.id.btn_user_info);
        btn_statistic = findViewById(R.id.btn_statistic);

        toolbar_title.setText("MÀN HÌNH CHÍNH");

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        txt_fullname.setText(user.getFullname());

        toolbar_logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogoutListener();
            }
        });

        btn_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUserInfoListener();
            }
        });

        btn_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStatisticListener();
            }
        });

        btn_parking_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnParkingInfoListener();
            }
        });


    }

    private void btnParkingInfoListener() {
        Intent intent = new Intent();
        intent.setClass(AdminHomeActivity.this, ParkingInfoActivity.class);
        startActivity(intent);
    }

    private void btnUserInfoListener() {
        Intent intent = new Intent();
        intent.setClass(AdminHomeActivity.this, SearchUserInfoActivity.class);
        startActivity(intent);
    }

    private void btnStatisticListener() {
        Intent intent = new Intent();
        intent.setClass(AdminHomeActivity.this, StatisticActivity.class);
        startActivity(intent);
    }

    private void btnLogoutListener() {
        Intent intent = new Intent();
        intent.setClass(AdminHomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}