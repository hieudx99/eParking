package com.example.eparking.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eparking.R;
import com.example.eparking.service.UserService;
import com.example.eparking.model.User;
import com.example.eparking.model.dto.Credential;
import com.example.eparking.view.user.SignUpActivity;
import com.example.eparking.view.user.UserHomeActivity;

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
        Call<User> checkLogin = UserService.USER_SERVICE.checkLogin(credential);
        checkLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user == null) {
                    txt_message.setText("Invalid username or password");
                } else {
                    String role = user.getRole().getName();
                    if (role.equalsIgnoreCase("user")) {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, UserHomeActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else if (role.equalsIgnoreCase("admin")) {
                        txt_message.setText("Logged in as admin");
                    }

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                txt_message.setText("Error");
            }
        });
    }
}
