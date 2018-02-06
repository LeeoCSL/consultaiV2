package br.com.consultai.util;

import android.util.Patterns;

/**
 * Created by renan.boni on 19/01/2018.
 */

public class InputValidator {

    public static String NAME_PATTERN = "^[A-Za-z]+((\\s)?([A-Za-z])+)*$";

    public static boolean isValidName(String name) {
        return name.matches(NAME_PATTERN);
    }

    public static boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String capitalize(String txt) {
        if (txt != null) {
            if (!txt.isEmpty()) {
                String[] strArray = txt.split(" ");
                StringBuilder builder = new StringBuilder();

                for (String s : strArray) {
                    String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                    builder.append(cap).append(" ");
                }
                return builder.toString();
            }
        }
        return "";
    }


}
