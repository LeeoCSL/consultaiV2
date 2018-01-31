package br.com.consultaiv2.util;

/**
 * Created by renan.boni on 29/01/2018.
 */

public class DateUtil {

    public static String stringToTime(String hour){
        String[] tokens = hour.split(":");
        return tokens[0].concat("h").concat(tokens[1]).concat("min");
    }

}
