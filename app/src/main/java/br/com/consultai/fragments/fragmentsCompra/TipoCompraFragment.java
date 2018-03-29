package br.com.consultai.fragments.fragmentsCompra;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;

/**
 * Created by leonardo.ribeiro on 26/02/2018.
 */

public class TipoCompraFragment extends Fragment {

    Button btn_comum, btn_diario, btn_mensal;

    Button btn_onibus_mensal, btn_trem_mensal, btn_integracao_mensal;

    Button btn_onibus_diario, btn_trem_diario, btn_integracao_diario;

    Boolean onibusD = false;
    Boolean tremD = false;
    Boolean integracaoD = false;

    Boolean onibusM = false;
    Boolean tremM = false;
    Boolean integracaoM = false;

    Boolean comum = false;
    Boolean diario = false;
    Boolean mensal = false;
    LinearLayout lay_utilizacao_diario;

    Button btn_voltar;
    Button btn_continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_tipo_compra, null);

        btn_comum = (Button) view.findViewById(R.id.btn_comum);
        btn_diario = (Button) view.findViewById(R.id.btn_diario);
        btn_mensal = (Button) view.findViewById(R.id.btn_mensal);
        lay_utilizacao_diario = (LinearLayout) view.findViewById(R.id.lay_utilizacao_diario);

        btn_onibus_diario = (Button) view.findViewById(R.id.btn_onibus_diario);
        btn_trem_diario = (Button) view.findViewById(R.id.btn_trem_diario);
        btn_integracao_diario = (Button) view.findViewById(R.id.btn_integracao_diario);

        btn_onibus_mensal = (Button) view.findViewById(R.id.btn_onibus_mensal);
        btn_trem_mensal = (Button) view.findViewById(R.id.btn_trem_mensal);
        btn_integracao_mensal = (Button) view.findViewById(R.id.btn_integracao_mensal);

        btn_voltar = (Button) view.findViewById(R.id.btn_voltar);
        btn_continuar = (Button) view.findViewById(R.id.btn_continuar);

//        ComprarActivity.circulo1.setBackgroundResource(R.drawable.circulo_selec_200);
//        ComprarActivity.traco1.setBackgroundResource(R.drawable.traco_cinza_200);
//        ComprarActivity.circulo2.setBackgroundResource(R.drawable.circulo_cinza_200);
//        ComprarActivity.traco2.setBackgroundResource(R.drawable.traco_cinza_200);
//        ComprarActivity.circulo3.setBackgroundResource(R.drawable.circulo_cinza_200);

        btn_comum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comum == false) {
                    comum = true;
                    diario = false;
                    mensal = false;
                    btn_comum.setBackground(getContext().getResources().getDrawable(R.drawable.comum_selec));
                    btn_diario.setBackground(getContext().getResources().getDrawable(R.drawable.diario));
                    btn_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.mensal));

                    ComprarActivity.mViewPager.setCurrentItem(2, false);
                    ComprarActivity.atualizaStepView();
                }
            }
        });

        btn_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diario == false) {
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

        btn_onibus_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onibusD == false) {
                    onibusD = true;
                    tremD = false;
                    integracaoD = false;
                    btn_onibus_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_selec));
                    btn_trem_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_metro_cptm));
                    btn_integracao_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_metro_cptm));

                }
            }
        });

        btn_trem_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tremD == false) {
                    onibusD = false;
                    tremD = true;
                    integracaoD = false;
                    btn_onibus_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus));
                    btn_trem_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_metro_cptm_selec));
                    btn_integracao_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_metro_cptm));

                }
            }
        });

        btn_integracao_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (integracaoD == false) {
                    onibusD = false;
                    tremD = false;
                    integracaoD = true;
                    btn_onibus_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus));
                    btn_trem_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_metro_cptm));
                    btn_integracao_diario.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_metro_cptm_selec));

                }
            }
        });

        btn_mensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mensal == false) {
                    comum = false;
                    diario = false;
                    mensal = true;
                    btn_comum.setBackground(getContext().getResources().getDrawable(R.drawable.comum));
                    btn_diario.setBackground(getContext().getResources().getDrawable(R.drawable.diario));
                    btn_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.mensal_selec));
                }
            }
        });

        btn_onibus_mensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onibusM == false) {
                    onibusM = true;
                    tremM = false;
                    integracaoM = false;
                    btn_onibus_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_selec));
                    btn_trem_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_metro_cptm));
                    btn_integracao_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_metro_cptm));

                }
            }
        });

        btn_trem_mensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tremM == false) {
                    onibusM = true;
                    tremM = false;
                    integracaoM = false;
                    btn_onibus_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_selec));
                    btn_trem_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_metro_cptm));
                    btn_integracao_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_metro_cptm));

                }
            }
        });

        btn_integracao_mensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (integracaoM == false) {
                    onibusM = true;
                    tremM = false;
                    integracaoM = false;
                    btn_onibus_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_selec));
                    btn_trem_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_metro_cptm));
                    btn_integracao_mensal.setBackground(getContext().getResources().getDrawable(R.drawable.icone_onibus_metro_cptm));

                }
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(0, false);
            }
        });

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComprarActivity.mViewPager.setCurrentItem(2, false);
            }
        });


        return view;
    }
}
