package com.example.eparking.view.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;

public class SearchUserInfoActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_search_user_info_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("THÔNG TIN KHÁCH HÀNG");
    }
}