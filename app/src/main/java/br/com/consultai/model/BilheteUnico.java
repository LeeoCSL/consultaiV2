package br.com.consultai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renan.boni on 19/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BilheteUnico {

    private String id;
    private String apelido;
    private double saldo;
    private boolean estudante;
    private String numero;
    private String operacao;

    @SerializedName("saldo_anterior")
    private double saldoAnterior;

    private String id_desconto;

    @JsonProperty("id_usuario")
    private String usuarioID;

    public BilheteUnico(){}

    public BilheteUnico(String id, String apelido, double saldo, boolean estudante, String numero, String operacao, double saldoAnterior, String id_desconto, String usuarioID) {
        this.id = id;
        this.apelido = apelido;
        this.saldo = saldo;
        this.estudante = estudante;
        this.numero = numero;
        this.operacao = operacao;
        this.saldoAnterior = saldoAnterior;
        this.id_desconto = id_desconto;
        this.usuarioID = usuarioID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isEstudante() {
        return estudante;
    }

    public void setEstudante(boolean estudante) {
        this.estudante = estudante;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getId_desconto() {
        return id_desconto;
    }

    public void setId_desconto(String id_desconto) {
        this.id_desconto = id_desconto;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    @Override
    public String toString() {
        return "BilheteUnico{" +
                "id=" + id +
                ", apelido='" + apelido + '\'' +
                ", saldo=" + saldo +
                ", estudante=" + estudante +
                ", numero='" + numero + '\'' +
                ", operacao='" + operacao + '\'' +
                ", saldoAnterior=" + saldoAnterior +
                ", id_desconto='" + id_desconto + '\'' +
                ", usuarioID='" + usuarioID + '\'' +
                '}';
    }
}
