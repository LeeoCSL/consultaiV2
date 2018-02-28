package br.com.consultai.fragments.fragmentsCompra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class SelecBancoFragment extends Fragment {

    Button btn_bradesco, btn_itau, btn_caixa, btn_BB, btn_santander;

    Button btn_voltar, btn_avancar;
   // public static int selecaoBanco = 0;
    //1 bradesco
    //2 itau
    //3 caixa
    //4 bb
    //5 santander

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_selec_banco, null);

        btn_bradesco = (Button) view.findViewById(R.id.btn_bradesco);
        btn_itau = (Button) view.findViewById(R.id.btn_itau);
        btn_caixa = (Button) view.findViewById(R.id.btn_caixa);
        btn_BB = (Button) view.findViewById(R.id.btn_BB);
        btn_santander = (Button) view.findViewById(R.id.btn_santander);
        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);
        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);


        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(2);
            }
        });
        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(8);
            }
        });

        btn_bradesco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.selecaoBanco = 1;
            }
        });

        btn_itau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.selecaoBanco = 2;
            }
        });

        btn_caixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.selecaoBanco = 3;
            }
        });

        btn_BB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.selecaoBanco = 4;
            }
        });

        btn_santander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.selecaoBanco = 5;
            }
        });




        return view;
    }
}
