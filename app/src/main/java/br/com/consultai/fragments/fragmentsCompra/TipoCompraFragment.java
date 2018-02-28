package br.com.consultai.fragments.fragmentsCompra;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import br.com.consultai.R;

/**
 * Created by leonardo.ribeiro on 26/02/2018.
 */

public class TipoCompraFragment extends Fragment {

    Button btn_comum, btn_diario, btn_mensal;

    Boolean comum = false;
    Boolean diario = false;
    Boolean mensal = false;
    LinearLayout lay_utilizacao_diario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_pedidos, null);

        btn_comum = (Button) view.findViewById(R.id.btn_comum);
        btn_diario = (Button) view.findViewById(R.id.btn_diario);
        btn_mensal = (Button) view.findViewById(R.id.btn_mensal);
        lay_utilizacao_diario = (LinearLayout) view.findViewById(R.id.lay_utilizacao_diario);


        btn_comum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comum == false){
                    comum = true;
                    diario = false;
                    mensal = false;
                    btn_comum.setBackground(getContext().getResources().getDrawable(R.drawable.comum_selec));
                    btn_diario.setBackground(getContext().getResources().getDrawable(R.drawable.diario));
                    btn_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.mensal));
                }
            }
        });

        btn_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(diario == false){
                    comum = false;
                    diario = true;
                    mensal = false;
                    btn_comum.setBackground(getContext().getResources().getDrawable(R.drawable.comum));
                    btn_diario.setBackground(getContext().getResources().getDrawable(R.drawable.diario_selec));
                    btn_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.mensal));
                    lay_utilizacao_diario.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_mensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mensal == false){
                    comum = false;
                    diario = false;
                    mensal = true;
                    btn_comum.setBackground(getContext().getResources().getDrawable(R.drawable.comum));
                    btn_diario.setBackground(getContext().getResources().getDrawable(R.drawable.diario));
                    btn_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.mensal_selec));
                }
            }
        });



        return view;
    }
}
