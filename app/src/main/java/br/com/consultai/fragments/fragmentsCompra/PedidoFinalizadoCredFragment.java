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

public class PedidoFinalizadoCredFragment extends Fragment {

    Button btn_avancar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_pedido_finalizado_cred, null);
//        ComprarActivity.attStepView(3);

        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);

        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }
}
