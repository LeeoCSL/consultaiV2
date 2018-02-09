package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class Transferencia {

    @SerializedName("banco")
    private Banco banco;

    @SerializedName("favorecido")
    private Favorecido favorecido;

    public Transferencia(){}

    public Transferencia(Banco banco, Favorecido favorecido) {
        this.banco = banco;
        this.favorecido = favorecido;

    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Favorecido getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(Favorecido favorecido) {
        this.favorecido = favorecido;
    }
}
