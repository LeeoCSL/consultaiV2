package br.com.consultai.fragments.fragmentsCompra;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.MainActivity;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class ComprarFragment extends Fragment {
    Button btn_credito, btn_boleto, btn_transferencia;
    Button btn_valor_10, btn_valor_20, btn_valor_30;

    Button btn_voltar, btn_continuar;

    EditText edt_outro_valor;
    LinearLayout lay_valor;

    int tipoCompra = -1;
    //-1 = nulo
    //0 = credito
    //1 = boleto
    //2 = transferencia
    Double valorCompra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_comprar, null);

        btn_continuar = (Button) view.findViewById(R.id.btn_continuar);
        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);
        btn_credito = (Button) view.findViewById(R.id.btn_credito);
        btn_boleto = (Button) view.findViewById(R.id.btn_boleto);
        btn_transferencia = (Button) view.findViewById(R.id.btn_transferencia);
        btn_valor_10 = (Button) view.findViewById(R.id.btn_valor_10);
        btn_valor_20 = (Button) view.findViewById(R.id.btn_valor_20);
        btn_valor_30 = (Button) view.findViewById(R.id.btn_valor_30);
        edt_outro_valor = (EditText) view.findViewById(R.id.edt_outro_valor);
        lay_valor = (LinearLayout) view.findViewById(R.id.lay_valor);

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipoCompra == 0) {
                    ComprarActivity.mViewPager.setCurrentItem(2);
                }
                else if(tipoCompra == 1) {
                    //TODO compra boleto
                    ComprarActivity.mViewPager.setCurrentItem(5);
                }
                else if(tipoCompra == 2) {
                    ComprarActivity.mViewPager.setCurrentItem(6);
                }
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_credito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay_valor.setVisibility(View.VISIBLE);
                tipoCompra = 0;
            }
        });

        btn_boleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay_valor.setVisibility(View.VISIBLE);
                tipoCompra = 1;
            }
        });

        btn_transferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay_valor.setVisibility(View.VISIBLE);
                tipoCompra = 2;
            }
        });

        btn_valor_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorCompra = 10.0;
                btn_continuar.setVisibility(View.VISIBLE);
                edt_outro_valor.setText(" ");
                //TODO selected button
            }
        });

        btn_valor_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorCompra = 20.0;
                btn_continuar.setVisibility(View.VISIBLE);
                edt_outro_valor.setText(" ");
                //TODO selected button
            }
        });

        btn_valor_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorCompra = 30.0;
                btn_continuar.setVisibility(View.VISIBLE);
                edt_outro_valor.setText(" ");
                //TODO selected button
            }
        });

        edt_outro_valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //TODO deselect button
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_outro_valor.getText() != null) {
                    valorCompra = Double.parseDouble(String.valueOf(edt_outro_valor.getText()));
                    btn_continuar.setVisibility(View.VISIBLE);
                }
            }
        });




        return view;
    }
}
