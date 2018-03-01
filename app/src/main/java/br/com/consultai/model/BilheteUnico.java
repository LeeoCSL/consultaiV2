package br.com.consultai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan.boni on 19/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BilheteUnico {

    private Integer id;
    private String apelido;
    private double saldo;
    private boolean estudante;
    private String numero;
    private int operacao;
    @JsonProperty("saldo_anterior")
    private double saldoAnterior;

    public BilheteUnico(){}

    public BilheteUnico(Integer id, String apelido, double saldo, boolean estudante, String numero, int operacao, double saldoAnterior) {
        this.id = id;
        this.apelido = apelido;
        this.saldo = saldo;
        this.estudante = estudante;
        this.numero = numero;
        this.operacao = operacao;
        this.saldoAnterior = saldoAnterior;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(double saldoAnterior) {
        this.saldo = saldoAnterior;
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

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
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
                '}';
    }
}
