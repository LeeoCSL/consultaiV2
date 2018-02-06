package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class Pagamento {

    @SerializedName("idFormaPagamento")
    private String idFormaPagamento;

    @SerializedName("cartao")
    private CartaoPag cartao;

    @SerializedName("transferenciia")
    private Transferencia transferenciia;

    public Pagamento(){}

    public Pagamento(String idFormaPagamento, CartaoPag cartao, Transferencia transferenciia) {
        this.idFormaPagamento = idFormaPagamento;
        this.cartao = cartao;
        this.transferenciia = transferenciia;

    }


    public String getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(String idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public CartaoPag getCartao() {
        return cartao;
    }

    public void setCartao(CartaoPag cartao) {
        this.cartao = cartao;
    }

    public Transferencia getTransferenciia() {
        return transferenciia;
    }

    public void setTransferenciia(Transferencia transferenciia) {
        this.transferenciia =transferenciia ;
    }


}
