package br.com.consultaiv2.util;

import android.util.Log;

/**
 * Created by renan.boni on 29/01/2018.
 */

public class DateUtil {

    public static String stringToTime(String hour){
        Log.i("HORA_OK", hour);
        String[] tokens = hour.split(":");
        return tokens[0].concat("h").concat(tokens[1]).concat("min");
    }

}
