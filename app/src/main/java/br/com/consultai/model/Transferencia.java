package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class Transferencia {

    @SerializedName("banco")
    private String banco;

    @SerializedName("favorecido")
    private String favorecido;

    public Transferencia(){}

    public Transferencia(String banco, String favorecido) {
        this.banco = banco;
        this.favorecido = favorecido;

    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(String favorecido) {
        this.favorecido = favorecido;
    }
}
