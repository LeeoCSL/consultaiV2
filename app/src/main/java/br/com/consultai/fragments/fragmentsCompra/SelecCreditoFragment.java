package br.com.consultai.fragments.fragmentsCompra;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import br.com.consultai.R;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.CartaoPag;
import br.com.consultai.model.Compra;
import br.com.consultai.model.Pagamento;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.retrofit.RetrofitInitCompra;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class SelecCreditoFragment extends Fragment {

    Button btn_voltar, btn_avancar, btnCadastrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_selec_cred, null);


        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);
        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);
        btnCadastrar = (Button) view.findViewById(R.id.btnCadastrar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(2);
            }
        });


        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO geração pedido compra


//                CartaoPag cartao = new CartaoPag();
//                cartao.setNome();
//                cartao.setNumeroCartao();
//                cartao.setValidade();
//                cartao.setCard_encrypted_json();
//
//                ComprarActivity.pagamento.setIdFormaPagamento();
//                ComprarActivity.pagamento.setCartao(cartao);
//
//
//
//                Compra compra = new Compra();
//                compra.setChecksum("xxx");
//                compra.setToken();
//                compra.setPagamento(ComprarActivity.pagamento);
//                compra.setIdProduto();
//                compra.setLatitude();
//                compra.setLongitude();
//                compra.setNumeroCartao();
//                compra.setQuantidade();
//                compra.setValor();
//                compra.setValorTaxa();
//
//                Call<StatusResponse> call = new RetrofitInitCompra(getContext()).getCompraService().pedido(compra);
//                call.enqueue(new Callback<StatusResponse>() {
//                    @Override
//                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                        StatusResponse res = response.body();
//
//                        if(res.hasError()){
//                            Toast.makeText(getContext(), "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
//                        }else{
////
                            ComprarActivity.mViewPager.setCurrentItem(5);
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusResponse> call, Throwable t) {
//                        Toast.makeText(getContext(), "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });



            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(4);
            }
        });



        return view;
    }
}
