package br.com.consultai.fragments.fragmentsCompra;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.MainActivity;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.ConsultaFormaPagamento;
import br.com.consultai.model.ConsultaProduto;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInitCompra;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class SelecBilheteFragment extends Fragment {

    TextView txt_apelido_bilhete, txt_numero_bilhete;

    Button btnCadBilhete, btn_voltar, btn_avancar;

    CheckBox cb_bilhete;

    private BilheteUnico bilheteUnico;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_selec_bilhete, null);

//        ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_selec_200);
//        ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_cinza_200);
//        ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_cinza_200);
//        ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_cinza_200);
//        ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_cinza_200);

        txt_apelido_bilhete = (TextView) view.findViewById(R.id.txt_apelido_bilhete);
        txt_numero_bilhete = (TextView) view.findViewById(R.id.txt_numero_bilhete);
        cb_bilhete = (CheckBox) view.findViewById(R.id.cb_bilhete);
        btnCadBilhete = (Button) view.findViewById(R.id.btnCadBilhete);
        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);
        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);

        Usuario usuario = CustomApplication.currentUser;

        if(usuario.getBilheteUnico() != null) {
            bilheteUnico = usuario.getBilheteUnico();

            txt_apelido_bilhete.setText(bilheteUnico.getApelido());
            txt_numero_bilhete.setText(bilheteUnico.getNumero());

        }

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_bilhete.isChecked()){

                    ConsultaProduto consulta = new ConsultaProduto();
                    consulta.setChecksum("xxx");
                    consulta.setToken("xxx");
                    consulta.setNumeroCartao(txt_numero_bilhete.getText().toString());

//                Call<StatusResponse> call = new RetrofitInitCompra(getActivity()).getCompraService().consultarProduto(consulta);
//                call.enqueue(new Callback<StatusResponse>() {
//                    @Override
//                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                        StatusResponse res = response.body();
//
//                        if(res.hasError()){
//                            Toast.makeText(getContext(), "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(getContext(), "oia deu certo " + res.getMessage(), Toast.LENGTH_SHORT).show();
//

                    //TODO consultar Forma de Pagamento

                    ConsultaFormaPagamento consultaPag = new ConsultaFormaPagamento();
                    consultaPag.setChecksum("xxx");
                    consultaPag.setToken("xxx");

                    ComprarActivity.mViewPager.setCurrentItem(11, false);
ComprarActivity.atualizaStepView();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusResponse> call, Throwable t) {
//                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//   //                     mProgressBar.setVisibility(View.GONE);
//                    }
//                });




                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Selecione um bilhete");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

        btnCadBilhete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ComprarActivity.mViewPager.setCurrentItem(1, false);
                ComprarActivity.atualizaStepView();


            }
        });






        return view;
    }
}
