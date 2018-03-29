package br.com.consultai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 19/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pedidos {

    private String dataPedido;
    private String numeroPedido;
    private double valorPedido;
    private String statusPedido;

    public Pedidos(){}

    public Pedidos(String dataPedido, String numeroPedido, double valorPedido, String statusPedido) {
        this.dataPedido = dataPedido;
        this.numeroPedido = numeroPedido;
        this.valorPedido = valorPedido;
        this.statusPedido = statusPedido;

    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setANumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public double getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(double valorPedido) {
        this.valorPedido = valorPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }


    @Override
    public String toString() {
        return "BilheteUnico{" +
                "dataPedido=" + dataPedido +
                ", numeroPedido='" + numeroPedido + '\'' +
                ", valorPedido=" + valorPedido +
                ", statusPedido=" + statusPedido +
                '}';
    }
}
