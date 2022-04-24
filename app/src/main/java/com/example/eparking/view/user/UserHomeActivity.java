package com.example.eparking.view.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eparking.R;
import com.example.eparking.model.User;
import com.example.eparking.service.SessionManager;
import com.example.eparking.view.AppConfig;
import com.example.eparking.view.LoginActivity;
import com.example.eparking.view.admin.AdminHomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserHomeActivity extends AppCompatActivity {

    private ImageView toolbar_logout_icon;
    private TextView toolbar_title;
    private TextView txt_fullname;
    private TextView txt_identityCard;
    private TextView txt_telephone;
    private ImageButton btn_parking;
    private ImageButton btn_history;
    private ImageButton btn_info;
    private User user;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_activity);

        toolbar_logout_icon = findViewById(R.id.toolbar_logout_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        txt_fullname = findViewById(R.id.txt_fullname);
        txt_identityCard = findViewById(R.id.txt_identityCard);
        txt_telephone = findViewById(R.id.txt_telephone);
        btn_parking = findViewById(R.id.btn_parking);
        btn_history = findViewById(R.id.btn_history);
        btn_info = findViewById(R.id.btn_info);

        toolbar_title.setText("MÀN HÌNH CHÍNH");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        txt_fullname.setText(user.getFullname());
        txt_identityCard.setText(user.getIdentityCard());
        txt_telephone.setText(user.getTelephone());

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        toolbar_logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogoutListener();
            }
        });

        btn_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnParkingListener();
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHistoryListener();
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnInfoListener();
            }
        });
    }

    private void btnInfoListener() {
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, UserInfoActivity.class);
        intent.putExtra("user", user);
        startActivityForResult(intent, AppConfig.REQUEST_CODE_USER);
    }

    private void btnHistoryListener() {
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, ParkingHistoryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void btnParkingListener() {
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, ParkingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_USER) {
            if (resultCode == Activity.RESULT_OK) {
                this.user = (User) data.getSerializableExtra("user");
                finish();
                Intent intent = new Intent();
                intent.setClass(UserHomeActivity.this, UserHomeActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        }
    }

    private void btnLogoutListener() {
        signOut();
        SessionManager sessionManager = new SessionManager();
        sessionManager.clearJwtToken();
        finish();
        Intent intent = new Intent();
        intent.setClass(UserHomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        gsc.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}