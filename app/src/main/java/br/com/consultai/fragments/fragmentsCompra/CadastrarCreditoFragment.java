package br.com.consultai.fragments.fragmentsCompra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class CadastrarCreditoFragment extends Fragment {

    EditText edt_numero_cartao, edt_validade, edt_nome_cartao, edt_cvv;

    Button btn_avancar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_cadastrar_cred, null);
        ComprarActivity.attStepView(2);

        edt_numero_cartao = (EditText) view.findViewById(R.id.edt_numero_cartao);
        edt_validade = (EditText) view.findViewById(R.id.edt_validade);
        edt_nome_cartao = (EditText) view.findViewById(R.id.edt_nome_cartao);
        edt_cvv = (EditText) view.findViewById(R.id.edt_cvv);
        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);


        MaskEditTextChangedListener maskCred = new MaskEditTextChangedListener("#### #### #### ####", edt_numero_cartao);
        MaskEditTextChangedListener maskValidade = new MaskEditTextChangedListener("##/##", edt_validade);


        edt_numero_cartao.addTextChangedListener(maskCred);
        edt_validade.addTextChangedListener(maskValidade);

        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_numero_cartao.getText().toString().length() != 19 ){
                    edt_numero_cartao.setError("O número do cartão esta imcompleto");
                }
                if (edt_validade.getText().toString().length() != 5 ){
                    edt_validade.setError("a validade esta imcompleta");
                }
                if (edt_nome_cartao.getText().toString().length() == 0 ){
                    edt_nome_cartao.setError("O nome está vazio");
                }
                if (edt_cvv.getText().toString().length() != 3 ){
                    edt_cvv.setError("O número do CVV esta imcompleto");
                }
                else{
                    //TODO
                    ComprarActivity.mViewPager.setCurrentItem(3);
                }

            }
        });



        return view;
    }
}
