package com.example.eparking.view.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.eparking.view.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;

    private EditText edt_fullname;
    private EditText edt_identityCard;
    private EditText edt_telephone;
    private EditText edt_email;
    private EditText edt_address;
    private EditText edt_username;
    private EditText edt_password;
    private EditText edt_password2;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        edt_fullname = findViewById(R.id.edt_fullname);
        edt_identityCard = findViewById(R.id.edt_identityCard);
        edt_telephone = findViewById(R.id.edt_telephone);
        edt_email = findViewById(R.id.edt_email);
        edt_address = findViewById(R.id.edt_address);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_password2 = findViewById(R.id.edt_password2);
        btn_signup = findViewById(R.id.btn_signup);

        toolbar_title.setText("ĐĂNG KÝ TÀI KHOẢN");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackOnClick();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignupOnClick();
            }
        });
    }

    private void btnSignupOnClick() {
        String fullname = edt_fullname.getText().toString();
        String identityCard = edt_identityCard.getText().toString();
        String telephone = edt_telephone.getText().toString();
        String email = edt_email.getText().toString();
        String address = edt_address.getText().toString();
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        String password2 = edt_password2.getText().toString();

        if (fullname.equals("") || identityCard.equals("") || telephone.equals("") ||
                email.equals("") || address.equals("") || username.equals("") ||
                password.equals("") || password2.equals("")) {
            showAlert("Phải nhập tất cả các trường!");
            return;
        }

        if (!password.equals(password2)) {
            showAlert("Xác nhận mật khẩu không đúng");
            return;
        }
        User user = new User(username, password, fullname, identityCard, email, telephone, address);
        Call<User> regiser = UserService.USER_SERVICE.register(user);
        regiser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user1 = response.body();
                    if (user1 != null) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setTitle("Thông báo");
                        alert.setIcon(R.mipmap.ic_launcher);
                        alert.setMessage("Đăng ký thành công! ID = " + user1.getId());
                        alert.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setClass(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                        alert.create().show();
                    }
                } else if (response.code() == 401) {
                    showAlert("Unauthorized");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showAlert("Đăng ký không thành công");
            }
        });
    }


    private void btnBackOnClick() {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void showAlert(String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage(msg);
        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }


}
