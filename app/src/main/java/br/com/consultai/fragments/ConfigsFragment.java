package br.com.consultai.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.activities.ComoUsarActivity;
import br.com.consultai.activities.EditBilheteActivity;
import br.com.consultai.activities.EditPerfilActivity;
import br.com.consultai.activities.EditarCartaoActivity;
import br.com.consultai.application.CustomApplication;
import io.paperdb.Paper;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ConfigsFragment extends Fragment {

    private ProgressBar mProgressBar;
    private LinearLayout btn_perfil, btn_bu, btn_rotina, btn_faq, btn_politica, btn_sair;


    public ConfigsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_configs, container, false);

        loadUI(view);

        btn_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditPerfilActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "pefil", Toast.LENGTH_SHORT).show();
            }
        });
        btn_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditBilheteActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "bu", Toast.LENGTH_SHORT).show();
            }
        });
        btn_rotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditarCartaoActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "rotina", Toast.LENGTH_SHORT).show();
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ComoUsarActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "faq", Toast.LENGTH_SHORT).show();
            }
        });
        btn_politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse("https://zazzytec.com.br/politica_consultai.php"));
                startActivity(intentSite);
//                Toast.makeText(getContext(), "politica", Toast.LENGTH_SHORT).show();
            }
        });
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();

                CustomApplication customApplication = (CustomApplication) getApplicationContext();
                customApplication.destroySession();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });





        return view;

    }


    private void loadUI(View v) {



        mProgressBar = v.findViewById(R.id.progress_bar);

        btn_perfil = v.findViewById(R.id.btn_perfil);
        btn_bu = v.findViewById(R.id.btn_bu);
        btn_rotina = v.findViewById(R.id.btn_rotina);
        btn_faq = v.findViewById(R.id.btn_faq);
        btn_politica = v.findViewById(R.id.btn_politica);
        btn_sair = v.findViewById(R.id.btn_sair);

    }

    @Override
    public void onResume() {
        super.onResume();

        refreshUI();

        Log.i("CURRENT USER", CustomApplication.currentUser.toString());
    }



    public void refreshUI() {
//        Usuario usuario = CustomApplication.currentUser;
//
//        if (usuario.getRotina().size() > 0) {
//            boolean[] diasUso = usuario.getRotina().get(0).getDiasUso().getDiasUso();
//
//            for (int i = 0; i < diasUso.length; i++) {
//                mWeekDays[i].setChecked(diasUso[i]);
//            }
//        } else {
//            for (int i = 0; i < mWeekDays.length; i++) {
//                mWeekDays[i].setChecked(false);
//            }
//        }
//
//        mApelidoBilhete.setText(usuario.getBilheteUnico().getApelido());
//        mSaldoAtual.setText(MonetaryUtil.doubleToMonetary(usuario.getBilheteUnico().getSaldo()));
    }

}