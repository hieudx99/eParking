package com.example.eparking.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eparking.R;
import com.example.eparking.model.ParkingSlot;

import java.util.List;

public class ParkingSlotAdapter extends RecyclerView.Adapter<ParkingSlotAdapter.ParkingSlotViewHolder> {

    private List<ParkingSlot> mListParkingSlot;
    private Context mContext;
    private ParkingSlotItemOnClickListener parkingSlotItemOnClickListener;
    private int selectedPosition = -1;
    private boolean userMode;

//    public ParkingSlotAdapter(Context mContext) {
//        this.mContext = mContext;
//    }


    public ParkingSlotAdapter(Boolean userMode, Context mContext, ParkingSlotItemOnClickListener parkingSlotItemOnClickListener) {
        this.userMode = userMode;
        this.mContext = mContext;
        this.parkingSlotItemOnClickListener = parkingSlotItemOnClickListener;
    }

    public void setData(List<ParkingSlot> list) {
        this.mListParkingSlot = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ParkingSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_slot, parent, false);
        return new ParkingSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingSlotViewHolder holder, int position) {
        ParkingSlot parkingSlot = mListParkingSlot.get(position);
        if (parkingSlot == null) {
            return;
        }
        holder.txtParkingSlotName.setText(parkingSlot.getName());

        if (selectedPosition == position) {
            holder.txtParkingSlotName.setText(parkingSlot.getName());
            holder.txtParkingSlotName.setBackgroundResource(R.drawable.rounded_textview_with_border_selected);
            holder.txtParkingSlotName.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            if (parkingSlot.getStatus().equalsIgnoreCase("U")) {
                holder.txtParkingSlotName.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.txtParkingSlotName.setBackgroundResource(R.drawable.rounded_textview_with_border_disable);
            } else {
                holder.txtParkingSlotName.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.txtParkingSlotName.setBackgroundResource(R.drawable.rounded_textview_with_border);
            }
        }

        holder.txtParkingSlotName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingSlotItemOnClickListener.onClick(parkingSlot);
                if (userMode == true && parkingSlot.getStatus().equalsIgnoreCase("U")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Thông báo");
                    alert.setIcon(R.mipmap.ic_launcher_round);
                    alert.setMessage("\nÔ bạn chọn đã được đặt!\n");
                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.create().show();
                    return;
                }
                else if (userMode == false && parkingSlot.getStatus().equalsIgnoreCase("O")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Thông báo");
                    alert.setIcon(R.mipmap.ic_launcher_round);
                    alert.setMessage("\nÔ bạn chọn chưa được đặt!\n");
                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.create().show();
                    return;
                }
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
        if (mListParkingSlot != null) {
            return mListParkingSlot.size();
        }
        return 0;
    }

    public class ParkingSlotViewHolder extends RecyclerView.ViewHolder {

        private TextView txtParkingSlotName;

        public ParkingSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            txtParkingSlotName = itemView.findViewById(R.id.txt_parking_slot_name);

        }
    }

    public interface ParkingSlotItemOnClickListener {
        void onClick(ParkingSlot parkingSlot);
    }

}
