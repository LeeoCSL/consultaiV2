package br.com.consultai.fragments.fragmentsCompra;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
    Double valorCompra = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_comprar, null);

        ComprarActivity.attStepView(2);

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
                edt_outro_valor.clearFocus();
                if(valorCompra == 0.0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Selecione um valor");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                if(valorCompra <4 || valorCompra >300) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Valor invalido, selecione um valor entre 4 e 300");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }


                else{
                if(tipoCompra == 0) {
                    ComprarActivity.attStepView(2);
                    ComprarActivity.mViewPager.setCurrentItem(3);
                }
                else if(tipoCompra == 1) {
                    //TODO compra boleto
                    ComprarActivity.attStepView(2);
                    ComprarActivity.mViewPager.setCurrentItem(6);
                }
                else if(tipoCompra == 2) {
                    ComprarActivity.attStepView(2);
                    ComprarActivity.mViewPager.setCurrentItem(7);
                }
            }
            }

        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.attStepView(1);
                ComprarActivity.mViewPager.setCurrentItem(0);
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
                edt_outro_valor.setText("");
                Toast.makeText(getContext(), String.valueOf(valorCompra), Toast.LENGTH_SHORT).show();
                //TODO selected button
            }
        });

        btn_valor_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorCompra = 20.0;
                btn_continuar.setVisibility(View.VISIBLE);
                edt_outro_valor.setText("");
                //TODO selected button
                Toast.makeText(getContext(), String.valueOf(valorCompra), Toast.LENGTH_SHORT).show();

            }
        });

        btn_valor_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorCompra = 30.0;
                btn_continuar.setVisibility(View.VISIBLE);
                edt_outro_valor.setText("");
                //TODO selected button
                Toast.makeText(getContext(), String.valueOf(valorCompra), Toast.LENGTH_SHORT).show();

            }
        });

        edt_outro_valor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b ) {
                    if(edt_outro_valor.getText().length() >5)
                    valorCompra = Double.parseDouble(String.valueOf(edt_outro_valor.getText().subSequence(1, edt_outro_valor.length())));
                    btn_continuar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), String.valueOf(valorCompra), Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }
}
