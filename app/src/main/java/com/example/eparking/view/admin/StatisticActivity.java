package com.example.eparking.view.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eparking.R;
import com.example.eparking.model.User;
import com.example.eparking.model.UserStat;
import com.example.eparking.model.dto.StartEndDateDTO;
import com.example.eparking.service.UserStatService;
import com.example.eparking.view.AppConfig;
import com.example.eparking.view.adapter.UserStatAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private EditText edt_from;
    private EditText edt_to;
    private Button btn_view;
    private TextView txt_total;
    private int from_year_selected;
    private int from_month_selected;
    private int from_day_selected;
    private int to_year_selected;
    private int to_month_selected;
    private int to_day_selected;
    private String date_from;
    private String date_to;
    private List<UserStat> listUserStat;
    DatePickerDialog.OnDateSetListener setListener;
    private RecyclerView rcv_user_stat;
    private UserStatAdapter userStatAdapter;

    private User selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_statistic_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        edt_from = findViewById(R.id.edt_from);
        edt_to = findViewById(R.id.edt_to);
        btn_view = findViewById(R.id.btn_view);
        txt_total = findViewById(R.id.txt_total);
        rcv_user_stat = findViewById(R.id.rcv_user_stat);

        toolbar_title.setText("THỐNG KÊ DOANH THU");

        edt_from.setInputType(InputType.TYPE_NULL);
        edt_to.setInputType(InputType.TYPE_NULL);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        from_year_selected = year;
        from_month_selected = month;
        from_day_selected = day;
        to_year_selected = year;
        to_month_selected = month;
        to_day_selected = day;
        edt_from.setText(getStringDateToDisplay(year, month, day));
        edt_to.setText(getStringDateToDisplay(year, month, day));
        date_from = getStringDate(year, month, day);
        date_to = getStringDate(year, month, day);


        edt_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtFromListener();
            }
        });
        edt_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtToListener();
            }
        });

        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnViewListener();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StatisticActivity.this, LinearLayoutManager.VERTICAL, false);
        rcv_user_stat.setLayoutManager(linearLayoutManager);
        rcv_user_stat.addItemDecoration(new DividerItemDecoration(rcv_user_stat.getContext(), DividerItemDecoration.VERTICAL));
        userStatAdapter = new UserStatAdapter(new UserStatAdapter.UserStatItemOnClickListener() {
            @Override
            public void onClick(UserStat userStat) {
                userStatItemListener(userStat);
            }
        });
        
    }

    private void userStatItemListener(UserStat userStat) {
        Intent intent = new Intent();
        intent.setClass(StatisticActivity.this, UserParkingHistoryActivity.class);
        intent.putExtra("userStat", userStat);
        intent.putExtra("startDate", date_from);
        intent.putExtra("endDate", date_to);
        startActivity(intent);
    }

    private void btnViewListener() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date start = null;
        Date end = null;
        try {
            start = simpleDateFormat.parse(date_from);
            end = simpleDateFormat.parse(date_to);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (start.before(end) || start.equals(end)) {
            StartEndDateDTO dateDTO = new StartEndDateDTO(date_from, date_to);
            Call<List<UserStat>> getListUserStat = UserStatService.USER_STAT_SERVICE.getListUserStat(dateDTO);
            getListUserStat.enqueue(new Callback<List<UserStat>>() {
                @Override
                public void onResponse(Call<List<UserStat>> call, Response<List<UserStat>> response) {
                    listUserStat = response.body();
                    if (listUserStat != null) {
                        double total = 0;
                        for (UserStat u : listUserStat) {
                            total += u.getRentTotal();
                        }
                        txt_total.setText(AppConfig.toVND(total));
                        userStatAdapter.setData(listUserStat);
                        rcv_user_stat.setAdapter(userStatAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<UserStat>> call, Throwable t) {

                }
            });
        }
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Thông báo");
            alert.setIcon(R.mipmap.ic_launcher_round);
            alert.setMessage("Ngày bắt đầu không được nhỏ hơn ngày kết thúc");
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.create().show();
            return;
        }
    }

    private void edtToListener() {
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                to_year_selected = year;
                to_month_selected = month;
                to_day_selected = day;
                String date = getStringDateToDisplay(year, month, day);
                edt_to.setText(date);
                date_to = getStringDate(year, month, day);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                StatisticActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                setListener, to_year_selected, to_month_selected-1, to_day_selected);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void edtFromListener() {
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                from_year_selected = year;
                from_month_selected = month;
                from_day_selected = day;
                String date = getStringDateToDisplay(year, month, day);
                edt_from.setText(date);
                date_from = getStringDate(year, month, day);

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                StatisticActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                setListener, from_year_selected, from_month_selected-1, from_day_selected);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }



    private String getStringDateToDisplay(int year, int month, int day) {
        return (day < 10 ? "0" + day : day)  + "/" + (month<10?"0"+month:month) + "/" + year;
    }
    private String getStringDate(int year, int month, int day) {
        return year + "-" + (month<10?"0"+month:month) + "-" + (day < 10 ? "0" + day : day);
    }


}