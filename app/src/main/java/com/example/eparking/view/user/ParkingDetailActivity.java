package com.example.eparking.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.Bill;
import com.example.eparking.view.AppConfig;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ParkingDetailActivity extends AppCompatActivity {

    private final double UNIT_PRICE = 50000;
    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private TextView txtBillID;
    private TextView txtLicensePlate;
    private TextView txtParkingTime;
    private TextView txtStartTime;
    private TextView txtEndTime;
    private TextView txtParkingSlot;
    private TextView txtUnitPrice;
    private TextView txtTotal;
    private TextView txtPaymentMethod;
    private Button btnFinish;

    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_detail_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        txtBillID = findViewById(R.id.txt_parking_detail_bill_id);
        txtLicensePlate = findViewById(R.id.txt_parking_detail_license_plate);
        txtParkingTime = findViewById(R.id.txt_parking_detail_parking_time);
        txtStartTime = findViewById(R.id.txt_parking_detail_start_time);
        txtEndTime = findViewById(R.id.txt_parking_detail_end_time);
        txtParkingSlot = findViewById(R.id.txt_parking_detail_parking_slot);
        txtUnitPrice = findViewById(R.id.txt_parking_detail_unit_price);
        txtTotal = findViewById(R.id.txt_parking_detail_total);
        txtPaymentMethod = findViewById(R.id.txt_parking_detail_payment_method);
        btnFinish = findViewById(R.id.btn_parking_detail_finish);

        //set activity title
        toolbar_title.setText("CHI TIẾT ĐẶT CHỖ");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // end

        Intent intent = getIntent();
        bill = (Bill) intent.getSerializableExtra("responseBill");

        txtBillID.setText(String.valueOf(bill.getId()));
        txtLicensePlate.setText(bill.getCar().getLicensePlate());
        double parkingTime = 0;
        try {
            parkingTime = getParkingTime(bill.getStartTime(), bill.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtParkingTime.setText(String.valueOf(parkingTime) + " giờ");
        txtStartTime.setText(formatDate(bill.getStartTime()));
        txtEndTime.setText(formatDate(bill.getEndTime()));
        txtParkingSlot.setText(bill.getParkingSlot().getName());
        txtUnitPrice.setText(AppConfig.toVND(UNIT_PRICE));
        txtTotal.setText(AppConfig.toVND(bill.getTotal()));
        txtPaymentMethod.setText(bill.getPaymentMethod().getName());

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFinishListener();
            }
        });
    }

    private void btnFinishListener() {
        Intent intent = new Intent();
        intent.setClass(ParkingDetailActivity.this, UserHomeActivity.class);
        intent.putExtra("user", bill.getUser());
        startActivity(intent);
    }

    private double getParkingTime(String startTime, String endTime) throws ParseException {
        double parkingTime = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = simpleDateFormat.parse(startTime);
        Date end = simpleDateFormat.parse(endTime);
        Duration diff = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            diff = Duration.between(start.toInstant(), end.toInstant());
            parkingTime = Double.valueOf(decimalFormat.format(diff.getSeconds()/3600.0));
        }
        return parkingTime;
    }

    private String formatDate(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = sdf1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String formatedDate = sdf2.format(parsedDate);
        return formatedDate;
    }
}