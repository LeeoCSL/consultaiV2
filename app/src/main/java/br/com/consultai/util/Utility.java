package br.com.consultai.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Patterns;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by renan.boni on 10/08/2017.
 */

public class Utility {

    public static BigDecimal bd;

    public static String formatValue(float value) {
        return "R$ " + String.format("%.2f", value);
    }

    public static float stringToFloat(String text) {
        String s = text.substring(2).replace(".", "");
        s = s.replace(",", "");

        long amount = Long.parseLong(s);

        bd = BigDecimal.valueOf(amount).movePointLeft(2);

        return bd.floatValue();
    }

    public static void makeText(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}