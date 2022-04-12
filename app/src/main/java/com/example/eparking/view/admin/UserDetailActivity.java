package com.example.eparking.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.User;
import com.example.eparking.view.adapter.UserCarAdapter;

public class UserDetailActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private EditText edt_fullname;
    private EditText edt_identityCard;
    private EditText edt_telephone;
    private EditText edt_address;
    private RecyclerView rcvUserCar;
    private UserCarAdapter userCarAdapter;

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

        rcvUserCar = findViewById(R.id.rcv_admin_user_detail_list_car);

        toolbar_title.setText("THÔNG TIN KHÁCH HÀNG");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        edt_fullname.setText(user.getFullname());
        edt_identityCard.setText(user.getIdentityCard());
        edt_telephone.setText(user.getTelephone());
        edt_address.setText(user.getAddress());

        //init recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserDetailActivity.this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcvUserCar.setLayoutManager(layoutManager);
        userCarAdapter = new UserCarAdapter();
        userCarAdapter.setData(user.getListCar());
        rcvUserCar.setAdapter(userCarAdapter);
        //

    }


}