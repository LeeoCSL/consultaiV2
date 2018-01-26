package br.com.consultaiv2.application;

import android.app.Application;
import android.content.SharedPreferences;

import br.com.consultaiv2.model.Rotina;
import br.com.consultaiv2.model.Usuario;

/**
 * Created by renan.boni on 22/01/2018.
 */

public class CustomApplication extends Application {

    public static Usuario currentUser;
    private static String apiKey;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static boolean isUserLoggedIn(){
        return currentUser != null;
    }

    public static void setCurrentUser(Usuario currentUser) {
        CustomApplication.currentUser = currentUser;
    }

    public String getAPItoken(){
        if(apiKey == null || apiKey.isEmpty()){
            SharedPreferences sp = getSharedPreferences("TOKEN", MODE_PRIVATE);
            apiKey = sp.getString("token", "");
        }

        if(apiKey.isEmpty()){
            return null;
        }

        return apiKey;
    }

    public static void updateRoutine(Rotina rotina){
        currentUser.getRotinas().clear();
        currentUser.getRotinas().add(rotina);
    }

    public void setAPItoken(String token){
        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString("token", token);
        edit.commit();
    }
}
