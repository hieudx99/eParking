package com.example.eparking.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eparking.R;
import com.example.eparking.service.SessionManager;
import com.example.eparking.service.UserService;
import com.example.eparking.model.User;
import com.example.eparking.model.dto.UserCredential;
import com.example.eparking.view.admin.AdminHomeActivity;
import com.example.eparking.view.user.SignUpActivity;
import com.example.eparking.view.user.UserHomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_username;
    private EditText edt_password;
    private TextView txt_message;
    private Button btn_login;
    private Button btn_signup;
    private ImageButton signInButton;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        txt_message = findViewById(R.id.txt_message);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        signInButton = findViewById(R.id.btn_google_sign_in);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginListener();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignupListener();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 10000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 10000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String googleAccessToken = account.getIdToken();
            System.out.println(googleAccessToken);
            // Signed in successfully, show authenticated UI.
            Call<User> google = UserService.USER_SERVICE.google(googleAccessToken);
            google.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    String token = response.headers().get("Authorization");
                    System.out.println(token);
                    SessionManager sessionManager = new SessionManager();
                    sessionManager.saveJwtToken(token);
                    updateUI(user);

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    txt_message.setVisibility(View.VISIBLE);
                    txt_message.setText("Login google error");
                }
            });

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(@NonNull User user) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, UserHomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


    private void btnSignupListener() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    private void btnLoginListener() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        UserCredential userCredential = new UserCredential(username, password);

        Call<User> checkLogin = UserService.USER_SERVICE.checkLogin(userCredential);
        checkLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 401) {
                    txt_message.setVisibility(View.VISIBLE);
                    txt_message.setText("Unauthorized");
                }
                else if (response.code() == 200) {
                    User user = response.body();
                    if (user == null) {
                        txt_message.setVisibility(View.VISIBLE);
                        txt_message.setText("Invalid username or password");
                    } else {
                        String token = response.headers().get("Authorization");
                        SessionManager sessionManager = new SessionManager();
                        sessionManager.saveJwtToken(token);

                        String role = user.getRole().getName();
                        if (role.equalsIgnoreCase("user")) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, UserHomeActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        } else if (role.equalsIgnoreCase("admin")) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, AdminHomeActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }

                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                txt_message.setVisibility(View.VISIBLE);
                txt_message.setText("Error");
            }
        });
    }
}
