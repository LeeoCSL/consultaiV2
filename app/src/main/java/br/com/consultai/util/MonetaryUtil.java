package br.com.consultai.util;

import java.text.DecimalFormat;

/**
 * Created by renan.boni on 29/01/2018.
 */

public class MonetaryUtil {

    public static String doubleToMonetary(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value).toString().replace(".", ",");
    }
}
