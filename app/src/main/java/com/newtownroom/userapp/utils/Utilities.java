package com.newtownroom.userapp.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Utilities {

    public static  boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String parseDate(String inputDate, String ipFormat, String opFormat) {

        SimpleDateFormat ipDateFormat = new SimpleDateFormat(ipFormat, Locale.getDefault());
        SimpleDateFormat opDateFormat = new SimpleDateFormat(opFormat, Locale.getDefault());
        String returnDate = "";
        try {
            Date date = ipDateFormat.parse(inputDate);
            returnDate = opDateFormat.format(Objects.requireNonNull(date));
        } catch (ParseException e) {
            Log.d("Date parsing error", e.toString());
        }
        return returnDate;
    }
}
