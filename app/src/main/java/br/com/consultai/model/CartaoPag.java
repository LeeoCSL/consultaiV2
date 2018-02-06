package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class CartaoPag {

    @SerializedName("nome")
    private String nome;

    @SerializedName("numeroCartao")
    private String numeroCartao;

    @SerializedName("validade")
    private String validade;

    @SerializedName("card.encrypted.json")
    private String card_encrypted_json;

    public CartaoPag(){}

    public CartaoPag(String nome, String numeroCartao, String validade, String card_encrypted_json) {
        this.nome = nome;
        this.numeroCartao = numeroCartao;
        this.validade = validade;
        this.card_encrypted_json = card_encrypted_json;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade =validade ;
    }

    public String getCard_encrypted_json() {
        return card_encrypted_json;
    }

    public void setCard_encrypted_json(String card_encrypted_json ) {
        this.card_encrypted_json =card_encrypted_json ;
    }
}
