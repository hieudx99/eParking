package com.example.eparking.view.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eparking.R;
import com.example.eparking.model.Car;
import com.example.eparking.model.User;
import com.example.eparking.view.AppConfig;
import com.example.eparking.view.adapter.ListCarAdapter;

import java.util.List;

public class ListCarActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private TextView txtFullname;
    private TextView txtIdentity;
    private TextView txtTelephone;
    private RecyclerView rcvListCar;
    private ListCarAdapter listCarAdapter;
    private Button btnAdd;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_car_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        txtFullname = findViewById(R.id.txt_list_car_fullname);
        txtIdentity = findViewById(R.id.txt_list_car_identityCard);
        txtTelephone = findViewById(R.id.txt_list_car_telephone);
        rcvListCar = findViewById(R.id.rcv_list_car);
        btnAdd = findViewById(R.id.btn_list_car_add);

        //set activity title
        toolbar_title.setText("DANH SÁCH XE");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackListener();
            }
        });
        // end

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        txtFullname.setText(user.getFullname());
        txtIdentity.setText(user.getIdentityCard());
        txtTelephone.setText(user.getTelephone());

        listCarAdapter = new ListCarAdapter(this, new ListCarAdapter.ItemListCarOnclickListener() {
            @Override
            public void onClick(Car car) {
                itemListCarListener(car);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvListCar.setLayoutManager(linearLayoutManager);
        listCarAdapter.setData(user.getListCar());
        rcvListCar.setAdapter(listCarAdapter);
        
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddListener();
            }
        });

    }

    private void btnBackListener() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user", user);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    private void btnAddListener() {
        Intent intent = new Intent();
        intent.setClass(ListCarActivity.this, CarInfoActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("isAddMode", true);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_ADD_CAR);

    }

    private void itemListCarListener(Car car) {
        Intent intent = new Intent();
        intent.setClass(ListCarActivity.this, CarInfoActivity.class);
        intent.putExtra("car", car);
        intent.putExtra("user", user);
        intent.putExtra("isAddMode", false);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_EDIT_CAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_ADD_CAR) {
            if (resultCode == Activity.RESULT_OK) {
                Car addedCar = (Car) data.getSerializableExtra("car");
                System.out.println(addedCar.getName());
                List<Car> listCar = user.getListCar();
                listCar.add(addedCar);
                user.setListCar(listCar);
                listCarAdapter.setData(listCar);
                rcvListCar.setAdapter(listCarAdapter);
            }
        }
        if (requestCode == AppConfig.REQUEST_CODE_EDIT_CAR) {
            if (resultCode == Activity.RESULT_OK) {
                Car editedCar = (Car) data.getSerializableExtra("car");
                for (Car c : user.getListCar()) {
                    if (c.getId() == editedCar.getId()) {
                        c.setBrand(editedCar.getBrand());
                        c.setColor(editedCar.getColor());
                        c.setSeatNumber(editedCar.getSeatNumber());
                        c.setName(editedCar.getName());
                        c.setLicensePlate(editedCar.getLicensePlate());
                        break;
                    }
                }
                listCarAdapter.setData(user.getListCar());
                rcvListCar.setAdapter(listCarAdapter);

            }
        }
    }
}