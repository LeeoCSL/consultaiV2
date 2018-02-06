package br.com.consultai.fragments.fragmentsCompra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import br.com.consultai.R;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class BandeiraFragment extends Fragment {
    Button btn_elo, btn_master, btn_visa,btn_continuar;
    Button btn_valor_10, btn_valor_20, btn_valor_30;


    LinearLayout lay_bandeira;

    int bandeira = -1;

    //0 = elo
    //1 = master
    //2 = visa

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_bandeira, null);

        btn_elo = (Button) view.findViewById(R.id.btn_elo);
        btn_master = (Button) view.findViewById(R.id.btn_master);
        btn_visa = (Button) view.findViewById(R.id.btn_visa);
        btn_continuar = (Button) view.findViewById(R.id.btn_continuar);

        lay_bandeira = (LinearLayout) view.findViewById(R.id.lay_bandeira);

        btn_elo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandeira = 0;
            }
        });

        btn_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandeira = 1;
            }
        });

        btn_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandeira = 2;
            }
        });

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bandeira == -1){
                    Toast.makeText(getContext(), "Por favor selecione a bandeira", Toast.LENGTH_LONG).show();
                }
                else{
                    //TODO
                }

            }
        });



       


        return view;
    }
}
