package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class CancelamentoPedido {

    @SerializedName("checksum")
    private String checksum;

    @SerializedName("token")
    private String token;

    @SerializedName("idPedido")
    private String idPedido;

    public CancelamentoPedido(){}

    public CancelamentoPedido(String checksum, String token, String idPedido) {
        this.checksum = checksum;
        this.token = token;
        this.idPedido = idPedido;
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

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public String toString() {
        return "Credencial{" +
                "checksum='" + checksum + '\'' +
                "idPedido='" + idPedido + '\'' +
                ", token='" + token +
                '}';
    }
}
