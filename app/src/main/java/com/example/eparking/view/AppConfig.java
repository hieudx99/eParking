package com.example.eparking.view;

import java.text.NumberFormat;
import java.util.Locale;

public final class AppConfig {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String TOKEN = "TOKEN" ;
    public static final int REQUEST_CODE_ADD_CAR = 1000001;
    public static final int REQUEST_CODE_EDIT_CAR = 1000002;
    public static final int REQUEST_CODE_USER = 1000003;

    public static String toVND(double number) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vn", "VN"));
        String toNumber = format.format(number);
        return toNumber;

    }
}
