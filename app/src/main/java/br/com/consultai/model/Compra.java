package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class Compra {

    @SerializedName("checksum")
    private String checksum;

    @SerializedName("token")
    private String token;

    @SerializedName("pagamento")
    private Pagamento pagamento;

    @SerializedName("idProduto")
    private String idProduto;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("numeroCartao")
    private String numeroCartao;

    @SerializedName("quantidade")
    private String quantidade;

    @SerializedName("valor")
    private String valor;

    @SerializedName("valorTaxa")
    private String valorTaxa;

    public Compra(){}

    public Compra(String checksum, String token, Pagamento pagamento, String idProduto,
                  String latitude, String longitude, String numeroCartao, String quantidade,
                  String valor, String valorTaxa) {
        this.checksum = checksum;
        this.token = token;
        this.pagamento = pagamento;
        this.idProduto = idProduto;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numeroCartao = numeroCartao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valorTaxa = valorTaxa;

    }


    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento =pagamento ;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto ) {
        this.idProduto =idProduto ;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude ;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude =longitude ;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade =quantidade ;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor =valor ;
    }

    public String getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(String valorTaxa) {
        this.valorTaxa =valorTaxa ;
    }

    @Override
    public String toString() {
        return "Credencial{" +
                "checksum='" + checksum + '\'' +
                ", token='" + token + '\'' +
                ", pagamento='" + pagamento + '\'' +
                ", idProduto='" + idProduto + '\'' +
                ", latitude=" + latitude + '\'' +
                ", longitude=" + longitude + '\'' +
                ", numeroCartao=" + numeroCartao + '\'' +
                ", quantidade=" + quantidade + '\'' +
                ", valor=" + valor + '\'' +
                ", valorTaxa=" + valorTaxa +
                '}';
    }

}
