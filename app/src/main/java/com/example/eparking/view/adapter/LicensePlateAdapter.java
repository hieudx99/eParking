package com.example.eparking.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eparking.R;
import com.example.eparking.model.Car;

import java.util.List;

public class LicensePlateAdapter extends RecyclerView.Adapter<LicensePlateAdapter.LicensePlateViewHolder> {

    private List<Car> listCar;
    private Context mContext;
    private LicensePlateItemOnclickListener licensePlateItemOnclickListener;
    private int selectedPosition = -1;

    public LicensePlateAdapter(Context mContext, LicensePlateItemOnclickListener listener) {
        this.mContext = mContext;
        this.licensePlateItemOnclickListener = listener;
    }

    public void setData(List<Car> listCar) {
        this.listCar = listCar;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LicensePlateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_license_plate, parent, false);
        return new LicensePlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LicensePlateViewHolder holder, int position) {
        Car car = listCar.get(position);
        if (car == null) {
            return;
        }
        holder.txtLicensePlate.setText(car.getLicensePlate());

        if (selectedPosition == position) {
            holder.txtLicensePlate.setText(car.getLicensePlate());
            holder.txtLicensePlate.setBackgroundResource(R.drawable.rounded_textview_with_border_selected);
            holder.txtLicensePlate.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.txtLicensePlate.setBackgroundResource(R.drawable.rounded_textview_with_border);
            holder.txtLicensePlate.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        holder.txtLicensePlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licensePlateItemOnclickListener.onClick(car);
                if (selectedPosition >= 0) {
                    notifyItemChanged(selectedPosition);
                }
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
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

    public class LicensePlateViewHolder extends RecyclerView.ViewHolder {

        private TextView txtLicensePlate;

        public LicensePlateViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLicensePlate = itemView.findViewById(R.id.txt_item_license_plate);
        }
    }

    public interface LicensePlateItemOnclickListener {
        void onClick(Car car);
    }
}
