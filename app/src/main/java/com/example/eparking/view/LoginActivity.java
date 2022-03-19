package com.example.eparking.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eparking.R;
import com.example.eparking.service.CustomerService;
import com.example.eparking.model.Customer;
import com.example.eparking.model.dto.Credential;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_username;
    private EditText edt_password;
    private TextView txt_message;
    private Button btn_login;
    private Button btn_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        txt_message = findViewById(R.id.txt_message);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginListener();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignupListener();
            }
        });

    }

    private void btnSignupListener() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void btnLoginListener() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        Credential credential = new Credential(username, password);
        txt_message.setVisibility(View.VISIBLE);
        Call<Customer> checkLogin = CustomerService.customerService.checkLogin(credential);
        checkLogin.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer = response.body();
                txt_message.setText("Login success");
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                txt_message.setText("Invalid username or password");
            }
        });
    }
}
