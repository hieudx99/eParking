package com.example.eparking.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.Bill;
import com.example.eparking.model.UserStat;
import com.example.eparking.model.dto.StartEndDateDTO;
import com.example.eparking.service.BillService;
import com.example.eparking.view.AppConfig;
import com.example.eparking.view.adapter.UserParkingHistoryAdapter;
import com.example.eparking.view.user.ParkingDetailHistoryActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserParkingHistoryActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private TextView txtFullname;
    private TextView txtIdentity;
    private TextView txtTelephone;
    private TextView txtTotal;
    private RecyclerView rcv_parking_history;
    private UserParkingHistoryAdapter userParkingHistoryAdapter;

    private UserStat userStat;
    private String startDate;
    private String endDate;
    private StartEndDateDTO dateDTO;
    private List<Bill> listBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_parking_history_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        txtFullname = findViewById(R.id.txt_user_parking_history_fullname);
        txtIdentity = findViewById(R.id.txt_user_parking_history_identityCard);
        txtTelephone = findViewById(R.id.txt_user_parking_history_telephone);
        txtTotal = findViewById(R.id.txt_user_parking_history_total);
        rcv_parking_history = findViewById(R.id.rcv_admin_user_parking_history);

        toolbar_title.setText("LỊCH SỬ ĐỖ XE");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userParkingHistoryAdapter = new UserParkingHistoryAdapter(new UserParkingHistoryAdapter.ParkingHistoryItemOnClickListener() {
            @Override
            public void onClick(Bill bill) {
                itemUserParkingHistoryListener(bill);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                UserParkingHistoryActivity.this, LinearLayoutManager.VERTICAL, false
        );
        rcv_parking_history.setLayoutManager(linearLayoutManager);

        getData();
    }

    private void itemUserParkingHistoryListener(Bill bill) {
        if (bill != null) {
            Intent intent = new Intent();
            intent.setClass(UserParkingHistoryActivity.this,
                    ParkingDetailHistoryActivity.class);
            intent.putExtra("bill", bill);
            startActivity(intent);
        }
    }

    private void getData() {
        Intent intent = getIntent();
        userStat = (UserStat) intent.getSerializableExtra("userStat");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        dateDTO = new StartEndDateDTO(startDate, endDate);

        txtFullname.setText(userStat.getFullname());
        txtIdentity.setText(userStat.getIdentityCard());
        txtTelephone.setText(userStat.getTelephone());
        txtTotal.setText(AppConfig.toVND(userStat.getRentTotal()));
        Call<List<Bill>> getUserParkingHistory = BillService.BILL_SERVICE.getUserParkingHistory(
                        dateDTO, userStat.getId());
        getUserParkingHistory.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                listBill = response.body();
                if (listBill != null) {
                    userParkingHistoryAdapter.setData(listBill);
                    rcv_parking_history.setAdapter(userParkingHistoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                
            }
        });

    }
}