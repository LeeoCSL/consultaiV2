package br.com.consultai.fragments.fragmentsCompra;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.MainActivity;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class PedidoBoletoFragment extends Fragment {


    TextView txt_num_boleto;

    Button btn_copiar;
    Button btn_avancar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_pedido_boleto, null);

        txt_num_boleto = (TextView) view.findViewById(R.id.txt_num_boleto);
        btn_copiar = (Button) view.findViewById(R.id.btn_copiar);
        btn_avancar = (Button) view.findViewById(R.id.btn_avancar);


        btn_copiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", txt_num_boleto.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });


        btn_avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
