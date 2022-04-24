package com.example.eparking.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eparking.R;
import com.example.eparking.model.Car;

import java.util.List;

public class ListCarAdapter extends RecyclerView.Adapter<ListCarAdapter.ListCarViewHolder> {

    private List<Car> listCar;
    ItemListCarOnclickListener itemListCarOnclickListener;
    private Context mContext;

    public ListCarAdapter(Context context, ItemListCarOnclickListener listener) {
        this.itemListCarOnclickListener = listener;
        this.mContext = context;
    }

    public void setData(List<Car> list) {
        this.listCar = list;
    }
    @NonNull
    @Override
    public ListCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_car, parent, false);
        return new ListCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCarViewHolder holder, int position) {
        Car car = listCar.get(position);
        if (car == null) {
            return;
        }
        holder.txtCarName.setText(car.getName());
        holder.txtLicense.setText(car.getLicensePlate());
        holder.txtColor.setText(car.getColor());
        holder.txtNbrSeat.setText(String.valueOf(car.getSeatNumber()));
        holder.txtBrand.setText(car.getBrand().getName());
//        holder.itemView.setOnLongClickListener(new);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListCarOnclickListener.onClick(car);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, car.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listCar != null) {
            return listCar.size();
        }
        return 0;
    }

    public class ListCarViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCarName;
        private TextView txtLicense;
        private TextView txtColor;
        private TextView txtNbrSeat;
        private TextView txtBrand;
        private LinearLayout item;


        public ListCarViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCarName = itemView.findViewById(R.id.txt_item_list_car_car_name);
            txtLicense = itemView.findViewById(R.id.txt_item_list_car_license_plate);
            txtColor = itemView.findViewById(R.id.txt_item_list_car_color);
            txtNbrSeat = itemView.findViewById(R.id.txt_item_list_car_nbr_seat);
            txtBrand = itemView.findViewById(R.id.txt_item_list_car_brand);
            item = itemView.findViewById(R.id.item_list_car);
        }
    }

    public interface ItemListCarOnclickListener {
        void onClick(Car car);
    }
}
