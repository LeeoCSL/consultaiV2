package br.com.consultai.fragments.fragmentsCompra;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.MainActivity;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class TransferenciaFinalizadaFragment extends Fragment {

    Button btn_avancar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_transferencia_fim, null);

        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);

//        ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_checkbox_200);
//        ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_verde_200);
//        ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_checkbox_200);
//        ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_verde_200);
//        ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_selec_200);

        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });






        return view;
    }
}
