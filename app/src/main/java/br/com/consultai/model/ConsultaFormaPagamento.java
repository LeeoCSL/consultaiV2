package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class ConsultaFormaPagamento {

    @SerializedName("checksum")
    private String checksum;

    @SerializedName("token")
    private String token;

    public ConsultaFormaPagamento(){}

    public ConsultaFormaPagamento(String checksum, String token) {
        this.checksum = checksum;
        this.token = token;
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

    @Override
    public String toString() {
        return "Credencial{" +
                "checksum='" + checksum + '\'' +
                ", token='" + token +
                '}';
    }


}
