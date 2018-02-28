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

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class CadastrarBilheteFragment extends Fragment {

    EditText edt_apelido_bilhete, edt_numero_bilhete;

    Button btn_continuar, btn_voltar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_cadastrar_bilhete, null);

        edt_apelido_bilhete = (EditText) view.findViewById(R.id.edt_apelido_bilhete);
        edt_numero_bilhete = (EditText) view.findViewById(R.id.edt_numero_bilhete);

        btn_continuar = (Button) view.findViewById(R.id.btn_continuar);
        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);


        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_apelido_bilhete.getText().toString().length() == 0 || edt_numero_bilhete.getText().toString().length() < 9 ){
                    Toast.makeText(getContext(), "Por favor digite as informações corretamente", Toast.LENGTH_SHORT).show();
                }
                else{
                    //TODO
                    ComprarActivity.mViewPager.setCurrentItem(2);
                }
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(0);

            }
        });

        return view;
    }
}
