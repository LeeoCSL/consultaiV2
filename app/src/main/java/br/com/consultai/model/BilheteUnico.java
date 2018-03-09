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
    private String operacao;
    @JsonProperty("saldo_anterior")
    private double saldoAnterior;
    private String id_desconto;
    private String id_usuario;

    public BilheteUnico(){}

    public BilheteUnico(Integer id, String apelido, double saldo, boolean estudante, String numero, String operacao, double saldoAnterior, String id_desconto, String id_usuario) {
        this.id = id;
        this.apelido = apelido;
        this.saldo = saldo;
        this.estudante = estudante;
        this.numero = numero;
        this.operacao = operacao;
        this.saldoAnterior = saldoAnterior;
        this.id_desconto = id_desconto;
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
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
        this.saldoAnterior = saldoAnterior;
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

    public String getId_desconto() {
        return id_desconto;
    }

    public void setId_desconto(String id_desconto) {
        this.id_desconto = id_desconto;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
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
