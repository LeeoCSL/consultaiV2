package br.com.consultaiv2.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.consultaiv2.R;
import br.com.consultaiv2.activities.EditarCartaoActivity;
import br.com.consultaiv2.application.CustomApplication;
import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.eventbus.events.UpdateUserSaldoEventt;
import br.com.consultaiv2.model.Usuario;
import br.com.consultaiv2.retrofit.RetrofitInit;
import br.com.consultaiv2.util.MonetaryUtil;
import info.hoang8f.widget.FButton;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private Button mEditar, mExcluir;

    private TextView mApelidoBilhete;
    private TextView mSaldoAtual;

    private FButton mBtnRecarga;

    private ProgressBar mProgressBar;

    private CustomToggleButton mDomingo, mSegunda, mTerca, mQuarta, mQuinta, mSexta, mSabado;
    private CustomToggleButton[] mWeekDays = new CustomToggleButton[7];

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        loadUI(view);

        mEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerToEditarCartaoActivity();
            }
        });
        mExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.ic_warning_black_24dp);
                builder.setTitle("Atenção");
                builder.setMessage("Você tem certeza que deseja apagar as suas rotinas?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mProgressBar.setVisibility(View.VISIBLE);

                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getRotinaService().delete(CustomApplication.currentUser.getId());
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if(res.hasError()){
                                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                                    mProgressBar.setVisibility(View.GONE);
                                }else{
                                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                                    CustomApplication.currentUser.getRotinas().clear();
                                    refreshUI();
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        mBtnRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.ic_attach_money_black_24dp);
                builder.setTitle("Nova recarga");
                builder.setMessage("Qual o valor da sua recarga?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        return view;
    }

    private void loadUI(View v){

        mProgressBar = v.findViewById(R.id.progress_bar);

        mApelidoBilhete = v.findViewById(R.id.txt_nome_bilhete);
        mEditar = v.findViewById(R.id.btn_editar);
        mSaldoAtual = v.findViewById(R.id.tv_saldo_atual);

        mExcluir = v.findViewById(R.id.btn_excluir);

        mBtnRecarga = v.findViewById(R.id.btn_recarga);

        mDomingo = v.findViewById(R.id.btn_domingo);
        mSegunda = v.findViewById(R.id.btn_segunda);
        mTerca = v.findViewById(R.id.btn_terca);
        mQuarta = v.findViewById(R.id.btn_quarta);
        mQuinta = v.findViewById(R.id.btn_quinta);
        mSexta = v.findViewById(R.id.btn_sexta);
        mSabado = v.findViewById(R.id.btn_sabado);

        mWeekDays[0] = mDomingo;
        mWeekDays[1] = mSegunda;
        mWeekDays[2] = mTerca;
        mWeekDays[3] = mQuarta;
        mWeekDays[4] = mQuinta;
        mWeekDays[5] = mSexta;
        mWeekDays[6] = mSabado;

        Log.i("token_firebase", FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshUI();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserSaldo(UpdateUserSaldoEventt event){
        refreshUI();
    }

    public void refreshUI(){
        Usuario usuario = CustomApplication.currentUser;

        if(usuario.getRotinas().size() > 0){
            boolean[] diasUso = usuario.getRotinas().get(0).getDiasUso().getDiasUso();

            for(int i = 0; i < diasUso.length; i++){
                mWeekDays[i].setChecked(diasUso[i]);
            }
        }else{
            for(int i = 0; i < mWeekDays.length; i++){
                mWeekDays[i].setChecked(false);
            }
        }

        mApelidoBilhete.setText(usuario.getBilheteUnico().getApelido());
        mSaldoAtual.setText(MonetaryUtil.doubleToMonetary(usuario.getBilheteUnico().getSaldo()));
    }

    public void handlerToEditarCartaoActivity() {
        startActivity(new Intent(getContext(), EditarCartaoActivity.class));
    }
}