package com.example.eparking.view.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eparking.R;
import com.example.eparking.model.Bill;
import com.example.eparking.model.Car;
import com.example.eparking.model.ParkingSlot;
import com.example.eparking.model.PaymentMethod;
import com.example.eparking.model.User;
import com.example.eparking.service.ParkingSlotService;
import com.example.eparking.service.PaymentMethodService;
import com.example.eparking.view.adapter.LicensePlateAdapter;
import com.example.eparking.view.adapter.ParkingSlotAdapter;
import com.example.eparking.view.admin.ParkingInfoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    //recycler
    private RecyclerView rcvParkingSlot;
    private ParkingSlotAdapter parkingSlotAdapter;

    private RecyclerView rcvLicensePlate;
    private LicensePlateAdapter licensePlateAdapter;
    //end
    private TextView txtFreeSlot;
    private EditText edtStart;
    private EditText edtEnd;
    //
    private Button btnPark;

    //
    private DatePickerDialog.OnDateSetListener setDateListener;
    private TimePickerDialog.OnTimeSetListener setTimeListener;
    private int start_year_selected;
    private int start_month_selected;
    private int start_day_selected;
    private int start_hour_selected;
    private int start_minute_selected;
    private int end_year_selected;
    private int end_month_selected;
    private int end_day_selected;
    private int end_hour_selected;
    private int end_minute_selected;
    private String startTime;
    private String endTime;
    //

    private List<ParkingSlot> listParkingSlot;
    private List<PaymentMethod> listPaymentMethod;
    private ParkingSlot selectedParkingSlot;
    private Car selectedCar;
    private User user;
    private PaymentMethod selectedPaymentMethod;


    private RadioButton radioZaloPay;
    private RadioButton radioMomo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_activity);
        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        txtFreeSlot = findViewById(R.id.txt_parking_freeSlot);
        rcvParkingSlot = findViewById(R.id.rcv_parking_parking_slot);
        rcvLicensePlate = findViewById(R.id.rcv_license_plate);


//        txtLicensePlate = findViewById(R.id.txt_parking_license_plate);
        edtStart = findViewById(R.id.edt_start);
        edtEnd = findViewById(R.id.edt_end);
        edtStart.setInputType(InputType.TYPE_NULL);
        edtEnd.setInputType(InputType.TYPE_NULL);
        //
        btnPark = findViewById(R.id.btn_parking);

        //set activity title
        toolbar_title.setText("ĐẶT CHỖ GỬI XE");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // end

        //get User from previous activity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //end

        // init recyclerview parking slot
        parkingSlotAdapter = new ParkingSlotAdapter(true,this, new ParkingSlotAdapter.ParkingSlotItemOnClickListener() {
            @Override
            public void onClick(ParkingSlot parkingSlot) {
                selectedParkingSlot = parkingSlot;
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcvParkingSlot.setLayoutManager(layoutManager);
        //end

        //init recycler view license plate
        licensePlateAdapter = new LicensePlateAdapter(this, new LicensePlateAdapter.LicensePlateItemOnclickListener() {
            @Override
            public void onClick(Car car) {
                selectedCar = car;
            }
        });
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcvLicensePlate.setLayoutManager(layoutManager1);
        if (user.getListCar() != null && user.getListCar().size() > 0) {
            licensePlateAdapter.setData(user.getListCar());
        }
        rcvLicensePlate.setAdapter(licensePlateAdapter);
        //end

        getListParkingSlot();
        getListPaymentMethod();


        //xu ly Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        start_year_selected = year;
        start_month_selected = month;
        start_day_selected = day;
        start_hour_selected = hour;
        start_minute_selected = minute;
        end_year_selected = year;
        end_month_selected = month;
        end_day_selected = day;
        end_hour_selected = hour;
        end_minute_selected = minute;
        edtStart.setText(getStringDateToDisplay(year, month, day, hour, minute));
        edtEnd.setText(getStringDateToDisplay(year, month, day, hour, minute));
        startTime = getStringDate(year, month, day, hour, minute);
        endTime = getStringDate(year, month, day, hour, minute);

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtStartListener();
            }
        });
        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtEndListener();
            }
        });
        //end

        //radio select payment method
        radioZaloPay = findViewById(R.id.radio_zalopay);
        radioMomo = findViewById(R.id.radio_momo);
        
        btnPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnParkListener();
            }
        });
    }

    private void btnParkListener() {
        Bill bill = new Bill();
        bill.setPaymentStatus("O");
        bill.setUser(user);
        //hardcode car[0]

        //
        if (selectedParkingSlot == null) {
            String message = "Chưa chọn chỗ đỗ xe!";
            showMessage(message);
            return;
        }
        else {
            bill.setParkingSlot(selectedParkingSlot);
        }
        if (selectedCar == null) {
            String message = "Chưa chọn xe!";
            showMessage(message);
            return;
        } else {
            bill.setCar(selectedCar);
        }

        if (isValidDate(startTime, endTime)) {
            bill.setStartTime(startTime);
            bill.setEndTime(endTime);
        }
        else {
            String message = "Thời gian không hợp lệ!";
            showMessage(message);
            return;
        }

        //select payment method
        if (radioZaloPay.isChecked()) {
            selectedPaymentMethod = getPaymentMethod("ZALOPAY");
        }
        else if (radioMomo.isChecked()) {
            selectedPaymentMethod = getPaymentMethod("MOMO");
        } else {
            String message = "Chưa chọn hình thức thanh toán";
            showMessage(message);
            return;
        }

        bill.setPaymentMethod(selectedPaymentMethod);

        Intent intent = new Intent();
        intent.setClass(ParkingActivity.this, ParkingTicketActivity.class);
        intent.putExtra("bill", bill);
        startActivity(intent);

    }

    private boolean isValidDate(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = null;
        Date end = null;
        try {
            start = simpleDateFormat.parse(startTime);
            end = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (start.before(end)) {
            return true;
        }
        else {
            return false;
        }
    }

    private PaymentMethod getPaymentMethod(String name) {
        PaymentMethod paymentMethod = null;
        for (PaymentMethod p : listPaymentMethod) {
            if (p.getName().equalsIgnoreCase(name)) {
                paymentMethod = p;
            }
        }
        return paymentMethod;
    }


    private void edtEndListener() {
        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                end_year_selected = year;
                end_month_selected = month;
                end_day_selected = day;
                setTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        end_hour_selected = hour;
                        end_minute_selected = minute;
                        String selectedDate = getStringDateToDisplay(end_year_selected, end_month_selected,
                                end_day_selected, end_hour_selected, end_minute_selected);
                        edtEnd.setText(selectedDate);
                        endTime = getStringDate(end_year_selected, end_month_selected,
                                end_day_selected, end_hour_selected, end_minute_selected);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ParkingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setTimeListener, end_hour_selected, end_minute_selected, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ParkingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                setDateListener, end_year_selected, end_month_selected-1, end_day_selected);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void edtStartListener() {
        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                start_year_selected = year;
                start_month_selected = month;
                start_day_selected = day;
                setTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        start_hour_selected = hour;
                        start_minute_selected = minute;
                        String selectedDate = getStringDateToDisplay(start_year_selected, start_month_selected,
                                start_day_selected, start_hour_selected, start_minute_selected);
                        edtStart.setText(selectedDate);
                        startTime = getStringDate(start_year_selected, start_month_selected,
                                start_day_selected, start_hour_selected, start_minute_selected);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ParkingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setTimeListener, start_hour_selected, start_minute_selected, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ParkingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                setDateListener, start_year_selected, start_month_selected-1, start_day_selected);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();



    }

    private void getListPaymentMethod() {
        Call<List<PaymentMethod>> getListPaymentMethod = PaymentMethodService.PAYMENT_METHOD_SERVICE.getListPaymentMethod();
        getListPaymentMethod.enqueue(new Callback<List<PaymentMethod>>() {
            @Override
            public void onResponse(Call<List<PaymentMethod>> call, Response<List<PaymentMethod>> response) {
                listPaymentMethod = response.body();
                setListPaymentMethod(listPaymentMethod);
            }

            @Override
            public void onFailure(Call<List<PaymentMethod>> call, Throwable t) {

            }
        });
    }

    private void getListParkingSlot() {
        Call<List<ParkingSlot>> getAll = ParkingSlotService.PARKING_SLOT_SERVICE.findAll();
        getAll.enqueue(new Callback<List<ParkingSlot>>() {
            @Override
            public void onResponse(Call<List<ParkingSlot>> call, Response<List<ParkingSlot>> response) {
                listParkingSlot = response.body();
                if (listParkingSlot == null || listParkingSlot.isEmpty()) {
                    Toast.makeText(ParkingActivity.this, "Không tải được thông tin bãi đỗ", Toast.LENGTH_SHORT).show();
                    return;
                }
                setListParkingSlot(listParkingSlot);
                countFreeSlot();
                parkingSlotAdapter.setData(listParkingSlot);
                rcvParkingSlot.setAdapter(parkingSlotAdapter);

            }

            @Override
            public void onFailure(Call<List<ParkingSlot>> call, Throwable t) {
                Toast.makeText(ParkingActivity.this, "Không tải được thông tin bãi đỗ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListParkingSlot(List<ParkingSlot> listParkingSlot) {
        this.listParkingSlot = listParkingSlot;
    }

    public void setListPaymentMethod(List<PaymentMethod> listPaymentMethod) {
        this.listPaymentMethod = listPaymentMethod;
    }

    private void countFreeSlot() {
        int freeSlot = 0;
        for (ParkingSlot p : listParkingSlot) {
            if (p.getStatus().equalsIgnoreCase("O")) {
                freeSlot++;
            }
        }
        txtFreeSlot.setText(String.valueOf(freeSlot)+"/"+listParkingSlot.size());
    }

    private void showMessage(String messgae) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage("\n" + messgae+ "\n");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }

    //HH:mm - dd/mm/yyyy
    private String getStringDateToDisplay(int year, int month, int day, int hour, int minute) {
        return hour + ":" + ((minute < 10 && minute > 0) ? "0" + minute : minute)
                + " - " + (day < 10 ? "0" + day : day)  + "/" + (month<10?"0"+month:month) + "/" + year;
    }
    //yyyy-mm-dd HH:mm:ss
    private String getStringDate(int year, int month, int day, int hour, int minute) {
        return year + "-" + (month<10?"0"+month:month) + "-" + (day < 10 ? "0" + day : day) + " "
                + hour + ":" + ((minute < 10 && minute > 0) ? "0" + minute : minute + ":00");
    }
}