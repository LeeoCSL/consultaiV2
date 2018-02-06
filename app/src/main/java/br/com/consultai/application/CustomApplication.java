package br.com.consultai.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import br.com.consultai.model.Rotina;
import br.com.consultai.model.Usuario;

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

        Log.i("ROTINA_RECEBIDA", rotina.toString());

        currentUser.getRotinas().clear();

        if(rotina.getTipoIda() == 0){
            Rotina ida = new Rotina();
            ida.setIdaID(rotina.getIdaID());
            ida.setFlagIda(rotina.isFlagIda());
            ida.setHoraIda(rotina.getHoraIda());
            ida.setValorIda(rotina.getValorIda());
            ida.setDiasUso(rotina.getDiasUso());

            currentUser.getRotinas().add(ida);
        }

        if(rotina.getTipoVolta() == 1){
            Rotina volta = new Rotina();
            volta.setVoltaID(rotina.getVoltaID());
            volta.setFlagVolta(rotina.isFlagVolta());
            volta.setHoraVolta(rotina.getHoraVolta());
            volta.setValorVolta(rotina.getValorVolta());
            volta.setDiasUso(rotina.getDiasUso());

            currentUser.getRotinas().add(volta);
        }
    }

    public void setAPItoken(String token){
        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString("token", token);
        edit.commit();
    }

    public void destroySession(){
        currentUser = null;
        apiKey = null;

        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.remove("token");
        edit.commit();
    }
}
