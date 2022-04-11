package com.example.eparking.view.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.Bill;
import com.example.eparking.service.BillService;
import com.example.eparking.view.AppConfig;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingTicketActivity extends AppCompatActivity {
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
    private Button btnConfirm;

    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_ticket_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        txtBillID = findViewById(R.id.txt_parking_ticket_bill_id);
        txtLicensePlate = findViewById(R.id.txt_parking_ticket_license_plate);
        txtParkingTime = findViewById(R.id.txt_parking_ticket_parking_time);
        txtStartTime = findViewById(R.id.txt_parking_ticket_start_time);
        txtEndTime = findViewById(R.id.txt_parking_ticket_end_time);
        txtParkingSlot = findViewById(R.id.txt_parking_ticket_parking_slot);
        txtUnitPrice = findViewById(R.id.txt_parking_ticket_unit_price);
        txtTotal = findViewById(R.id.txt_parking_ticket_total);
        txtPaymentMethod = findViewById(R.id.txt_parking_ticket_payment_method);
        btnConfirm = findViewById(R.id.btn_parking_ticket_confirm);

        //set activity title
        toolbar_title.setText("PHIẾU ĐỖ XE");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // end

        Intent intent = getIntent();
        bill = (Bill) intent.getSerializableExtra("bill");
        txtBillID.setText("");
        txtLicensePlate.setText(bill.getCar().getLicensePlate());
        double parkingTime = getParkingTime(bill.getStartTime(), bill.getEndTime());
        txtParkingTime.setText(String.valueOf(parkingTime) + " giờ");
        txtStartTime.setText(formatDate(bill.getStartTime()));
        txtEndTime.setText(formatDate(bill.getEndTime()));
        txtParkingSlot.setText(bill.getParkingSlot().getName());
        txtUnitPrice.setText(AppConfig.toVND(UNIT_PRICE));
        double total = UNIT_PRICE * parkingTime;
        bill.setTotal(total);
        txtTotal.setText(AppConfig.toVND(total));
        txtPaymentMethod.setText(bill.getPaymentMethod().getName());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnConfirmListener();
            }
        });

    }

    private void btnConfirmListener() {
        Call<Bill> createBill = BillService.BILL_SERVICE.createBill(bill);
        createBill.enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                Bill responseBill = response.body();
                if (responseBill != null) {
                    Intent intent = new Intent();
                    intent.setClass(ParkingTicketActivity.this, ParkingDetailActivity.class);
                    intent.putExtra("responseBill", responseBill);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ParkingTicketActivity.this);
                alert.setTitle("Thông báo");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.setMessage("\nTạo hoá đơn thất bại\n");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.create().show();

            }
        });
    }

    private double getParkingTime(String startTime, String endTime) {
        double parkingTime = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = null;
        Date end = null;
        try {
            start = simpleDateFormat.parse(startTime);
            end = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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