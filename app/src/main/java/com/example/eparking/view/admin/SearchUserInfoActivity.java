package com.example.eparking.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.User;
import com.example.eparking.service.UserService;
import com.example.eparking.view.adapter.UserInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserInfoActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private Button btn_search;
    private EditText edt_kw;
    private List<User> listUser;
    private RecyclerView rcv_user_info;
    private UserInfoAdapter userInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_search_user_info_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        btn_search = findViewById(R.id.btn_search);
        edt_kw = findViewById(R.id.edt_kw);
        rcv_user_info = findViewById(R.id.rcv_user_info);

        toolbar_title.setText("THÔNG TIN KHÁCH HÀNG");

        userInfoAdapter = new UserInfoAdapter(new UserInfoAdapter.UserInfoItemOnClickListener() {
            @Override
            public void onClick(User user) {
                userInfoItemListener(user);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchUserInfoActivity.this,
                RecyclerView.VERTICAL, false);
        rcv_user_info.setLayoutManager(linearLayoutManager);
        rcv_user_info.addItemDecoration(new DividerItemDecoration(rcv_user_info.getContext(), DividerItemDecoration.VERTICAL));

        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackListener();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSearchListener();
            }
        });
    }

    private void btnSearchListener() {
        String kw = edt_kw.getText().toString().trim().toLowerCase();
        Call<List<User>> searchUser = UserService.USER_SERVICE.searchUser(kw);
        searchUser.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listUser = response.body();
                if (!listUser.isEmpty()) {
                    userInfoAdapter.setData(listUser);
                    rcv_user_info.setAdapter(userInfoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    private void userInfoItemListener(User user) {
        Intent intent = new Intent();
        intent.setClass(this, UserDetailActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void btnBackListener() {
        finish();
    }
}