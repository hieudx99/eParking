package com.example.eparking.view.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import com.example.eparking.view.AppConfig;
import com.example.eparking.view.adapter.UserCarAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private EditText edt_fullname;
    private EditText edt_identityCard;
    private EditText edt_telephone;
    private EditText edt_address;
    private TextView txt_view_list_car;
    private Button btn_edit;
    private Button btn_save;
//    private RecyclerView rcvUserCar;
//    private UserCarAdapter userCarAdapter;

    private User user;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        edt_fullname = findViewById(R.id.edt_user_info_fullname);
        edt_identityCard = findViewById(R.id.edt_user_info_identityCard);
        edt_telephone = findViewById(R.id.edt_user_info_telephone);
        edt_address = findViewById(R.id.edt_user_info_address);
        txt_view_list_car = findViewById(R.id.txt_user_info_view_list_car);
//        rcvUserCar = findViewById(R.id.rcv_user_info_list_car);
        btn_edit = findViewById(R.id.btn_user_info_edit);
        btn_save = findViewById(R.id.btn_user_info_save);

        toolbar_title.setText("THÔNG TIN KHÁCH HÀNG");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackListener();
            }
        });

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        edt_fullname.setText(user.getFullname());
        edt_identityCard.setText(user.getIdentityCard());
        edt_telephone.setText(user.getTelephone());
        edt_address.setText(user.getAddress());

        txt_view_list_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtViewListCarListener();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditListener();
            }
        });
        
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSaveListener();
            }
        });


    }

    private void btnBackListener() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user", user);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void txtViewListCarListener() {
        Intent intent = new Intent();
        intent.setClass(UserInfoActivity.this, ListCarActivity.class);
        intent.putExtra("user", user);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_USER) {
            if (resultCode == Activity.RESULT_OK) {
                this.user = (User) data.getSerializableExtra("user");
            }
        }
    }

    private void btnSaveListener() {
        String fullname = edt_fullname.getText().toString().trim();
        String identity = edt_identityCard.getText().toString().trim();
        String tel = edt_telephone.getText().toString().trim();
        String address = edt_address.getText().toString().trim();
        if (fullname.equals(user.getFullname()) && identity.equals(user.getIdentityCard())
                && tel.equals(user.getTelephone()) && address.equals(user.getAddress())) {
                showAlert("Không có thay đổi!");
                return;
        }
        user.setFullname(fullname);
        user.setIdentityCard(identity);
        user.setTelephone(tel);
        user.setAddress(address);
        Call<User> updateInfo = UserService.USER_SERVICE.updateUser(user);
        updateInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 401) {
                    showAlert("Unauthorized");
                }
                else if (response.code() == 200) {
                    user = response.body();
                    if (user != null) {
                        showAlert("Lưu thành công!");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showAlert("Lưu thất bại");
            }
        });

    }

    private void btnEditListener() {
        edt_fullname.setEnabled(true);
        edt_fullname.setBackground(edt_fullname.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        edt_identityCard.setEnabled(true);
        edt_identityCard.setBackground(edt_fullname.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        edt_telephone.setEnabled(true);
        edt_telephone.setBackground(edt_fullname.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        edt_address.setEnabled(true);
        edt_address.setBackground(edt_fullname.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        btn_edit.setEnabled(false);
        btn_edit.setTextColor(btn_edit.getResources().getColor(R.color.white));
        btn_edit.setBackgroundColor(btn_edit.getContext().getResources().getColor(R.color.button_disable_color));
        btn_save.setEnabled(true);
        btn_save.setBackgroundColor(btn_save.getContext().getResources().getColor(R.color.primary_color));

    }

    private void showAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(UserInfoActivity.this);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setTitle("Thông báo");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (message.equalsIgnoreCase("Lưu thành công!")) {
                    finish();
                    intent.setClass(UserInfoActivity.this, UserInfoActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
        alert.create().show();
    }
}