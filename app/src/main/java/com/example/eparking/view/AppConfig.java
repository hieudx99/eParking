package com.example.eparking.view;

import java.text.NumberFormat;
import java.util.Locale;

public final class AppConfig {
    public static String toVND(double number) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vn", "VN"));
        String toNumber = format.format(number);
        return toNumber;

    }
}
