package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class ConsultaProduto {

    @SerializedName("checksum")
    private String checksum;

    @SerializedName("token")
    private String token;

    @SerializedName("numeroCartao")
    private String numeroCartao;

    public ConsultaProduto(){}

    public ConsultaProduto(String checksum, String token, String numeroCartao) {
        this.checksum = checksum;
        this.token = token;
        this.numeroCartao = numeroCartao;

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

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @Override
    public String toString() {
        return "Credencial{" +
                "checksum='" + checksum + '\'' +
                ", token='" + token + '\'' +
                ", numeroCartao='" + numeroCartao +
                '}';
    }


}
