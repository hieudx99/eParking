package com.example.eparking.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eparking.R;

public class SignUpActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("ĐĂNG KÝ TÀI KHOẢN");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackOnClick();
            }
        });
    }

    private void btnBackOnClick() {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }




}
