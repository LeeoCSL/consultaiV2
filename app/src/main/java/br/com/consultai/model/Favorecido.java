package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class Favorecido {

    @SerializedName("nome")
    private String nome;

    @SerializedName("cpf")
    private String cpf;

    @SerializedName("agencia")
    private String agencia;

    @SerializedName("conta")
    private String conta;


    public Favorecido(){}

    public Favorecido(String nome, String cpf, String agencia, String conta) {
        this.nome = nome;
        this.cpf = cpf;
        this.agencia = agencia;
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }


}
