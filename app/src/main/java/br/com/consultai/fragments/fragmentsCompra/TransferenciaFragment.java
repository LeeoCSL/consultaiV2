package br.com.consultai.fragments.fragmentsCompra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.consultai.R;

/**
 * Created by leonardo.ribeiro on 26/01/2018.
 */

public class TransferenciaFragment extends Fragment {

    EditText edt_agencia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_transferencia, null);









        return view;
    }
}
