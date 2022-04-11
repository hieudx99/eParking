package com.example.eparking.view.user;

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
import com.example.eparking.view.admin.AdminHomeActivity;

public class UserHomeActivity extends AppCompatActivity {

    private ImageView toolbar_logout_icon;
    private TextView toolbar_title;
    private TextView txt_fullname;
    private TextView txt_identityCard;
    private TextView txt_telephone;
    private ImageButton btn_parking;
    private ImageButton btn_history;
    private ImageButton btn_info;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_activity);

        toolbar_logout_icon = findViewById(R.id.toolbar_logout_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        txt_fullname = findViewById(R.id.txt_fullname);
        txt_identityCard = findViewById(R.id.txt_identityCard);
        txt_telephone = findViewById(R.id.txt_telephone);
        btn_parking = findViewById(R.id.btn_parking);
        btn_history = findViewById(R.id.btn_history);
        btn_info = findViewById(R.id.btn_info);

        toolbar_title.setText("MÀN HÌNH CHÍNH");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        txt_fullname.setText(user.getFullname());
        txt_identityCard.setText(user.getIdentityCard());
        txt_telephone.setText(user.getTelephone());

        toolbar_logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogoutListener();
            }
        });

        btn_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnParkingListener();
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHistoryListener();
            }
        });

    }

    private void btnHistoryListener() {
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, ParkingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void btnParkingListener() {
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, ParkingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void btnLogoutListener() {
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}