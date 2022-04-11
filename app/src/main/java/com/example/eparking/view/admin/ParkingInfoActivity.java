package com.example.eparking.view.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eparking.R;
import com.example.eparking.model.ParkingSlot;
import com.example.eparking.service.ParkingSlotService;
import com.example.eparking.view.adapter.ParkingSlotAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingInfoActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private RecyclerView rcvParkingSlot;
    private ParkingSlotAdapter parkingSlotAdapter;
    private TextView txtFreeSlot;
    private Button btnViewParkingSlotDetail;
    private List<ParkingSlot> listParkingSlot;
    private ParkingSlot selectedParkingSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_parking_info_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        rcvParkingSlot = findViewById(R.id.rcv_parking_slot);
        txtFreeSlot = findViewById(R.id.txt_freeSlot);
        btnViewParkingSlotDetail = findViewById(R.id.btn_view_parking_slot_detail);

        toolbar_title.setText("THÔNG TIN BÃI");

        parkingSlotAdapter = new ParkingSlotAdapter(this, new ParkingSlotAdapter.ParkingSlotItemOnClickListener() {
            @Override
            public void onClick(ParkingSlot parkingSlot) {
                parkingSlotItemListener(parkingSlot);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rcvParkingSlot.setLayoutManager(gridLayoutManager);
        getListParkingSlot();
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        btnViewParkingSlotDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnViewParkingSlotDetailListener();
            }
        });
    }

    private void btnViewParkingSlotDetailListener() {
        System.out.println(this.selectedParkingSlot.getName());
    }

    private void parkingSlotItemListener(ParkingSlot parkingSlot) {
//        if (parkingSlot.getStatus().equalsIgnoreCase("O")) {
//            view.setBackgroundResource(R.drawable.rounded_textview_with_border_selected);
//        } else {
//            view.setBackgroundResource(R.drawable.rounded_textview_with_border_disable);
//            AlertDialog.Builder alert = new AlertDialog.Builder(ParkingInfoActivity.this);
//            alert.setTitle("Thông báo");
//            alert.setIcon(R.mipmap.ic_launcher);
//            alert.setMessage("\nÔ bạn chọn đã được đặt!\n");
//            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            alert.create().show();
//        }
        this.selectedParkingSlot = parkingSlot;
    }

    private void getListParkingSlot() {
        Call<List<ParkingSlot>> getAll = ParkingSlotService.PARKING_SLOT_SERVICE.findAll();
        getAll.enqueue(new Callback<List<ParkingSlot>>() {
            @Override
            public void onResponse(Call<List<ParkingSlot>> call, Response<List<ParkingSlot>> response) {
                listParkingSlot = response.body();
                if (listParkingSlot == null || listParkingSlot.isEmpty()) {
                    Toast.makeText(ParkingInfoActivity.this, "Không tải được thông tin bãi đỗ", Toast.LENGTH_SHORT).show();
                    return;
                }
                setListParkingSlot(listParkingSlot);
                countFreeSlot();
                parkingSlotAdapter.setData(listParkingSlot);
                rcvParkingSlot.setAdapter(parkingSlotAdapter);

            }

            @Override
            public void onFailure(Call<List<ParkingSlot>> call, Throwable t) {
                Toast.makeText(ParkingInfoActivity.this, "Không tải được thông tin bãi đỗ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListParkingSlot(List<ParkingSlot> listParkingSlot) {
        this.listParkingSlot = listParkingSlot;
    }

    private void countFreeSlot() {
        int freeSlot = 0;
        for (ParkingSlot p : listParkingSlot) {
            if (p.getStatus().equalsIgnoreCase("O")) {
                freeSlot++;
            }
        }
        txtFreeSlot.setText(String.valueOf(freeSlot)+"/"+listParkingSlot.size());
    }
}