package com.example.eparking.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eparking.R;
import com.example.eparking.model.User;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>{

    private List<User> mListUser;
    private UserInfoItemOnClickListener userInfoItemOnClickListener;

    public UserInfoAdapter(UserInfoItemOnClickListener listener) {
        this.userInfoItemOnClickListener = listener;
    }

    public void setData(List<User> listUser) {
        this.mListUser = listUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_info, parent, false);
        return new UserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {
        User user = mListUser.get(position);
        if (user == null) {
            return;
        }
        holder.item_fullname.setText(user.getFullname());
        holder.item_identidyCard.setText(user.getIdentityCard());
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoItemOnClickListener.onClick(user);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }

    public class UserInfoViewHolder extends RecyclerView.ViewHolder {

        private TextView item_fullname;
        private TextView item_identidyCard;
        private LinearLayout item_layout;

        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            item_fullname = itemView.findViewById(R.id.item_fullname);
            item_identidyCard = itemView.findViewById(R.id.item_identityCard);
            item_layout = itemView.findViewById(R.id.item_layout);
        }
    }

    public interface UserInfoItemOnClickListener {
        void onClick(User user);
    }
}
