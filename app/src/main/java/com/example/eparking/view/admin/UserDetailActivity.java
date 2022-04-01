package com.example.eparking.view.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.User;

public class UserDetailActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private EditText edt_fullname;
    private EditText edt_identityCard;
    private EditText edt_telephone;
    private EditText edt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_detail_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        edt_fullname = findViewById(R.id.edt_fullname);
        edt_identityCard = findViewById(R.id.edt_identityCard);
        edt_telephone = findViewById(R.id.edt_telephone);
        edt_address = findViewById(R.id.edt_address);

        toolbar_title.setText("THÔNG TIN KHÁCH HÀNG");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackListener();
            }
        });
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        edt_fullname.setText(user.getFullname());
        edt_identityCard.setText(user.getIdentityCard());
        edt_telephone.setText(user.getTelephone());
        edt_address.setText(user.getAddress());

    }

    private void btnBackListener() {
        finish();
    }
}