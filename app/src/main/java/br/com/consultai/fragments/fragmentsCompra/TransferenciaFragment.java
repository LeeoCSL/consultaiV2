package br.com.consultai.fragments.fragmentsCompra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.Banco;
import br.com.consultai.model.Compra;
import br.com.consultai.model.Favorecido;
import br.com.consultai.model.Transferencia;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.retrofit.RetrofitInitCompra;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class TransferenciaFragment extends Fragment {

    EditText edt_agencia;
    EditText edt_numero_conta;

    Button btn_voltar, btn_avancar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_transferencia, null);
//        ComprarActivity.attStepView(2);
        edt_agencia = (EditText) view.findViewById(R.id.edt_agencia);
        edt_numero_conta = (EditText) view.findViewById(R.id.edt_numero_conta);
        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);
        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);
        //1 bradesco
        //2 itau
        //3 caixa
        //4 bb
        //5 santander

//        ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_checkbox_200);
//        ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_verde_200);
//        ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_selec_200);
//        ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_cinza_200);
//        ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_cinza_200);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(7, false);
            }
        });
        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO

//                Transferencia transf = new Transferencia();
//                Banco banco = new Banco();
//                Favorecido favorecido = new Favorecido();
//                banco.setId();
//                favorecido.setNome();
//                favorecido.setCpf();
//                favorecido.setAgencia();
//                favorecido.setConta();
//
//                transf.setBanco(banco);
//                transf.setFavorecido(favorecido);
//
//
//                ComprarActivity.pagamento.setIdFormaPagamento();
//                ComprarActivity.pagamento.setTransferenciia(transf);
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
//
//                            ComprarActivity.attStepView(3);
//                            ComprarActivity.mViewPager.setCurrentItem(9);
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

        if(ComprarActivity.selecaoBanco == 1){
            MaskEditTextChangedListener maskAG = new MaskEditTextChangedListener("####-#", edt_agencia);
            edt_agencia.addTextChangedListener(maskAG);

            MaskEditTextChangedListener maskCC = new MaskEditTextChangedListener("#######-#", edt_numero_conta);
            edt_numero_conta.addTextChangedListener(maskCC);
        }
        if(ComprarActivity.selecaoBanco == 2){
            MaskEditTextChangedListener maskAG = new MaskEditTextChangedListener("####", edt_agencia);
            edt_agencia.addTextChangedListener(maskAG);

            MaskEditTextChangedListener maskCC = new MaskEditTextChangedListener("#####-#", edt_numero_conta);
            edt_numero_conta.addTextChangedListener(maskCC);
        }
        if(ComprarActivity.selecaoBanco == 3){
            MaskEditTextChangedListener maskAG = new MaskEditTextChangedListener("#######-#", edt_agencia);
            edt_agencia.addTextChangedListener(maskAG);

            MaskEditTextChangedListener maskCC = new MaskEditTextChangedListener("#######-#", edt_numero_conta);
            edt_numero_conta.addTextChangedListener(maskCC);
        }
        if(ComprarActivity.selecaoBanco == 4){
            MaskEditTextChangedListener maskAG = new MaskEditTextChangedListener("####-#", edt_agencia);
            edt_agencia.addTextChangedListener(maskAG);

            MaskEditTextChangedListener maskCC = new MaskEditTextChangedListener("########-#", edt_numero_conta);
            edt_numero_conta.addTextChangedListener(maskCC);
        }
        if(ComprarActivity.selecaoBanco == 5){
            MaskEditTextChangedListener maskAG = new MaskEditTextChangedListener("####", edt_agencia);
            edt_agencia.addTextChangedListener(maskAG);

            MaskEditTextChangedListener maskCC = new MaskEditTextChangedListener("########-#", edt_numero_conta);
            edt_numero_conta.addTextChangedListener(maskCC);
        }



        return view;
    }
}
