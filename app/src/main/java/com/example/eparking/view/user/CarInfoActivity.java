package com.example.eparking.view.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eparking.R;
import com.example.eparking.model.Brand;
import com.example.eparking.model.Car;
import com.example.eparking.model.User;
import com.example.eparking.service.BrandService;
import com.example.eparking.service.CarService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarInfoActivity extends AppCompatActivity {

    private ImageView toolbar_back_icon;
    private TextView toolbar_title;
    private EditText edtCarName;
    private EditText edtCarLicense;
    private EditText edtCarColor;
    private EditText edtCarNbrSeat;
    private EditText edtCarBrand;
    private Button btnEdit;
    private Button btnSave;
    private Button btnAdd;
    private Spinner spinnerCarBrand;
    private RelativeLayout spinnerCarLayout;


    private Intent intent;
    private boolean isAddMode;
    private Car car;
    private User user;

    private List<Brand> listBrand;
    private List<String> listBrandString;
    private Brand selectedBrand;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_info_activity);

        toolbar_back_icon = findViewById(R.id.toolbar_back_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        edtCarName = findViewById(R.id.edt_car_info_car_name);
        edtCarLicense = findViewById(R.id.edt_car_info_license);
        edtCarColor = findViewById(R.id.edt_car_info_color);
        edtCarNbrSeat = findViewById(R.id.edt_car_info_nbr_seat);
//        edtCarBrand = findViewById(R.id.edt_car_info_brand);
        spinnerCarBrand = findViewById(R.id.spinner_car_brand);
        spinnerCarLayout = findViewById(R.id.spinner_car_layout);
        btnEdit = findViewById(R.id.btn_car_info_edit);
        btnSave = findViewById(R.id.btn_car_info_save);
        btnAdd = findViewById(R.id.btn_car_info_add);

        //set activity title
        toolbar_title.setText("CHI TIẾT XE");
        toolbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // end

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        isAddMode = intent.getExtras().getBoolean("isAddMode");

        getSpinnerData();
//        spinnerCarBrand.setAdapter(adapter);


        if (isAddMode == true) {
            enableEditText();
            btnEdit.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.INVISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnAddListener();
                }
            });
        }
        else {
            car = (Car) intent.getSerializableExtra("car");
            edtCarName.setText(car.getName());
            edtCarLicense.setText(car.getLicensePlate());
            edtCarColor.setText(car.getColor());
            edtCarNbrSeat.setText(String.valueOf(car.getSeatNumber()));
//            edtCarBrand.setText(car.getBrand());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnEditListener();
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnSaveListener();
                }
            });
        }



    }

    private void getSpinnerData() {
        Call<List<Brand>> findAll = BrandService.BRAND_SERVICE.findAll();
        findAll.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                listBrand = response.body();
                if (listBrand != null) {
                    setListBrand(listBrand);
                    setListBrandString(listBrand);
                    adapter = new ArrayAdapter<>(CarInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, listBrandString);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCarBrand.setAdapter(adapter);
                    spinnerCarBrand.setEnabled(false);
                    if (isAddMode == false ) {
                        spinnerCarBrand.setSelection(adapter.getPosition(car.getBrand().getName()));
                    }
                    if (isAddMode) {
                        spinnerCarBrand.setEnabled(true);
                    }
//                    spinnerCarBrand.setBackground(spinnerCarBrand.getContext().getDrawable(R.drawable.rounded_edittext_with_border));
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                showAlert("load spinner brand error");
            }
        });
    }

    private void btnAddListener() {
        String carName = edtCarName.getText().toString().trim();
        String carLicense = edtCarLicense.getText().toString().trim();
        String carColor = edtCarColor.getText().toString().trim();
        String carNbrSeat = edtCarNbrSeat.getText().toString().trim();
        String carBrand = spinnerCarBrand.getSelectedItem().toString();
        if ((carName == null || carName.isEmpty()) || (carLicense == null || carLicense.isEmpty())
        || (carColor == null || carColor.isEmpty()) || (carNbrSeat == null || carNbrSeat.isEmpty())
        || (carBrand == null || carBrand.isEmpty())) {
            showAlert("Phải điền tất cả các trường");
            return;
        }
        car = new Car();
        car.setName(carName);
        car.setLicensePlate(carLicense);
        car.setColor(carColor);
        car.setSeatNumber(Integer.parseInt(carNbrSeat));
        for (Brand b : listBrand) {
            if (b.getName().equals(carBrand)) {
                selectedBrand = b;
            }
        }
        car.setBrand(selectedBrand);
//        car.setBrand(carBrand);

        Call<Car> addCar = CarService.CAR_SERVICE.addCar(car, user.getId());
        addCar.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                car = response.body();
                if (car != null) {
                    showAlert("Thêm thành công!");
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                showAlert("Thêm thất bại");
            }
        });
    }

    private void btnSaveListener() {
        String carName = edtCarName.getText().toString().trim();
        String carLicense = edtCarLicense.getText().toString().trim();
        String carColor = edtCarColor.getText().toString().trim();
        String carNbrSeat = edtCarNbrSeat.getText().toString().trim();
        String carBrand = spinnerCarBrand.getSelectedItem().toString();
        if (carName.equals(car.getName()) && carLicense.equals(car.getLicensePlate()) && carBrand.equals(car.getBrand())
                && carColor.equals(car.getColor()) && carNbrSeat.equals(String.valueOf(car.getSeatNumber()))
                && carBrand.equals(car.getBrand().getName())) {
            showAlert("Không có thay đổi!");
            return;
        }
        car.setName(carName);
        car.setLicensePlate(carLicense);
        car.setColor(carColor);
        car.setSeatNumber(Integer.parseInt(carNbrSeat));
        for (Brand b : listBrand) {
            if (b.getName().equals(carBrand)) {
                selectedBrand = b;
            }
        }
        car.setBrand(selectedBrand);
        Call<Car> updateCar = CarService.CAR_SERVICE.updateCar(car, user.getId());
        updateCar.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                car = response.body();
                if (car != null) {
                    showAlert("Lưu thành công!");
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                showAlert("Lưu thất bại");
            }
        });

    }

    private void btnEditListener() {
        enableEditText();
        btnEdit.setEnabled(false);
        btnEdit.setTextColor(edtCarName.getResources().getColor(R.color.white));
        btnEdit.setBackgroundColor(edtCarName.getContext().getResources().getColor(R.color.button_disable_color));
        btnSave.setEnabled(true);
        btnSave.setBackgroundColor(edtCarName.getContext().getResources().getColor(R.color.primary_color));
    }

    private void showAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(CarInfoActivity.this);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setTitle("Thông báo");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (message.equalsIgnoreCase("Lưu thành công!")) {
//                    finish();
//                    intent.setClass(CarInfoActivity.this, CarInfoActivity.class);
//                    intent.putExtra("car", car);
//                    intent.putExtra("user", user);
//                    intent.putExtra("isAddMode", false);
//                    startActivity(intent);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("car", car);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                }
                if (message.equalsIgnoreCase("Thêm thành công!")) {
//                    finish();
//                    List<Car> listCar = user.getListCar();
//                    listCar.add(car);
//                    user.setListCar(listCar);
//                    intent.setClass(CarInfoActivity.this, ListCarActivity.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("car", car);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
        alert.create().show();
    }

    private void enableEditText() {
        edtCarName.setEnabled(true);
        edtCarName.setBackground(edtCarName.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        edtCarLicense.setEnabled(true);
        edtCarLicense.setBackground(edtCarName.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        edtCarColor.setEnabled(true);
        edtCarColor.setBackground(edtCarName.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        edtCarNbrSeat.setEnabled(true);
        edtCarNbrSeat.setBackground(edtCarName.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
        spinnerCarBrand.setEnabled(true);
        spinnerCarLayout.setBackground(edtCarName.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
//        edtCarBrand.setEnabled(true);
//        edtCarBrand.setBackground(edtCarName.getResources().getDrawable(R.drawable.rounded_edittext_with_border));
    }

    public List<Brand> getListBrand() {
        return listBrand;
    }

    public void setListBrand(List<Brand> listBrand) {
        this.listBrand = listBrand;
    }

    public void setListBrandString(List<Brand> listBrand) {
        List<String> list = new ArrayList<>();
        for (Brand b : listBrand) {
            list.add(b.getName());
        }
        this.listBrandString = list;
    }
}