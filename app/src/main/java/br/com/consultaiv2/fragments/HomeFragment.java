package br.com.consultaiv2.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.consultaiv2.R;
import br.com.consultaiv2.activities.EditarCartaoActivity;


public class HomeFragment extends Fragment {

    private Button mEditar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mEditar = view.findViewById(R.id.btn_editar);
        mEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerToEditarCartaoActivity();
            }
        });

        return view;
    }

    public void handlerToEditarCartaoActivity(){
        startActivity(new Intent(getContext(), EditarCartaoActivity.class));
    }


}
