package com.example.eparking.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eparking.R;
import com.example.eparking.model.Car;

import java.util.List;

public class UserCarAdapter extends RecyclerView.Adapter<UserCarAdapter.UserCarViewHolder> {

    private List<Car> listCar;

    public void setData(List<Car> listCar) {
        this.listCar = listCar;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_car, parent, false);
        return new UserCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCarViewHolder holder, int position) {
        Car car = listCar.get(position);
        if (car == null) {
            return;
        }
        int carPosition = position + 1;
        holder.txtLicensePlate.setText("BIỂN SỐ XE (XE " + carPosition + ")");
        holder.edtLicensePlate.setText(car.getLicensePlate());
        holder.txtCarName.setText("TÊN XE (XE " + carPosition + ")");
        holder.edtCarName.setText(car.getName());
    }

    @Override
    public int getItemCount() {
        if (listCar != null) {
            return listCar.size();
        }
        return 0;
    }

    public class UserCarViewHolder extends RecyclerView.ViewHolder {

        private TextView txtLicensePlate;
        private TextView txtCarName;
        private EditText edtLicensePlate;
        private EditText edtCarName;

        public UserCarViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLicensePlate = itemView.findViewById(R.id.txt_item_user_car_license_plate);
            txtCarName = itemView.findViewById(R.id.txt_item_user_car_car_name);
            edtLicensePlate = itemView.findViewById(R.id.edt_item_user_car_license_plate);
            edtCarName = itemView.findViewById(R.id.edt_item_user_car_car_name);
        }
    }
}
