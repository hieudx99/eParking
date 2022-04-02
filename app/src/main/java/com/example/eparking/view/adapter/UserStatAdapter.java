package com.example.eparking.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eparking.R;
import com.example.eparking.model.UserStat;
import com.example.eparking.view.AppConfig;

import java.util.List;

public class UserStatAdapter extends RecyclerView.Adapter<UserStatAdapter.UserStatViewHolder> {

    List<UserStat> listUserStat;
    UserStatItemOnClickListener userStatItemOnClickListener;

    public UserStatAdapter(UserStatItemOnClickListener listener) {
        this.userStatItemOnClickListener = listener;
    }

    public void setData(List<UserStat> list) {
        listUserStat = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserStatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_stat, parent, false);
        return new UserStatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStatViewHolder holder, int position) {
        //lay doi tuong o vi tri position va set data vao view
        UserStat userStat = listUserStat.get(position);
        if (userStat == null)
            return;
        holder.item_fullname.setText(userStat.getFullname());
        holder.item_identityCard.setText(userStat.getIdentityCard());
        holder.item_rent_times.setText(String.valueOf(userStat.getRentTimes()));
        holder.item_rent_total.setText(AppConfig.toVND(userStat.getRentTotal()));
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userStatItemOnClickListener.onClick(userStat);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listUserStat != null) {
            return listUserStat.size();
        }
        return 0;
    }

    public class UserStatViewHolder extends RecyclerView.ViewHolder {

        private TextView item_fullname;
        private TextView item_identityCard;
        private TextView item_rent_times;
        private TextView item_rent_total;
        private LinearLayout item_layout;

        public UserStatViewHolder(@NonNull View itemView) {
            super(itemView);
            item_fullname = itemView.findViewById(R.id.item_txt_fullname);
            item_identityCard = itemView.findViewById(R.id.item_txt_identityCard);
            item_rent_times = itemView.findViewById(R.id.item_txt_rent_times);
            item_rent_total = itemView.findViewById(R.id.item_txt_rent_total);
            item_layout = itemView.findViewById(R.id.user_stat_item);
        }
    }

    public interface UserStatItemOnClickListener {
        void onClick(UserStat userStat);
    }
}
