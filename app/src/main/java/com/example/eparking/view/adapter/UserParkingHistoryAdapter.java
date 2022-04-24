package com.example.eparking.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eparking.R;
import com.example.eparking.model.Bill;
import com.example.eparking.view.AppConfig;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class UserParkingHistoryAdapter extends RecyclerView.Adapter<UserParkingHistoryAdapter.UserParkingHistoryViewHolder> {
    private List<Bill> listBill;
    private ParkingHistoryItemOnClickListener parkingHistoryItemOnClickListener;

    public UserParkingHistoryAdapter(ParkingHistoryItemOnClickListener listener) {
        this.parkingHistoryItemOnClickListener = listener;
    }

    public void setData(List<Bill> listBill) {
        this.listBill = listBill;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserParkingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_history, parent, false);
        return new UserParkingHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserParkingHistoryViewHolder holder, int position) {
        Bill bill = listBill.get(position);
        if (bill == null) {
            return;
        }
        holder.txtCarName.setText(bill.getCar().getName());
        holder.txtLicensePlate.setText(bill.getCar().getLicensePlate());
        holder.txtParkingTime.setText(String.valueOf(getParkingTime(bill.getStartTime(), bill.getEndTime())));
        holder.txtStartTime.setText(formatDate(bill.getStartTime()));
        holder.txtEndTime.setText(formatDate(bill.getEndTime()));
        holder.txtTotal.setText(AppConfig.toVND(bill.getTotal()));
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingHistoryItemOnClickListener.onClick(bill);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listBill != null) {
            return listBill.size();
        }
        return 0;
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

    public class UserParkingHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCarName;
        private TextView txtLicensePlate;
        private TextView txtParkingTime;
        private TextView txtStartTime;
        private TextView txtEndTime;
        private TextView txtTotal;
        private LinearLayout item;

        public UserParkingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCarName = itemView.findViewById(R.id.txt_item_parking_history_car_name);
            txtLicensePlate = itemView.findViewById(R.id.txt_item_parking_history_license_plate);
            txtParkingTime = itemView.findViewById(R.id.txt_item_parking_history_parking_time);
            txtStartTime = itemView.findViewById(R.id.txt_item_parking_history_start_time);
            txtEndTime = itemView.findViewById(R.id.txt_item_parking_history_end_time);
            txtTotal = itemView.findViewById(R.id.txt_item_parking_history_total);
            item = itemView.findViewById(R.id.item_parking_history);

        }
    }

    public interface ParkingHistoryItemOnClickListener{
        void onClick(Bill bill);
    }

}
