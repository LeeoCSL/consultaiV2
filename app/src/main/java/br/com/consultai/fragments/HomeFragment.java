package br.com.consultai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.EditarCartaoActivity;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.eventbus.events.UpdateUserSaldoEventt;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.MonetaryUtil;
import info.hoang8f.widget.FButton;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private Button mEditar, mExcluir, btn_comprar, btn_limpar;

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

        btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ComprarActivity.class);
                startActivity(intent);
            }
        });

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
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Recarga");
                builder.setIcon(R.drawable.ic_attach_money_black_24dp);
                builder.setMessage("Qual o valor da sua recarga?");

                LayoutInflater inflater = getLayoutInflater();
                View addRecarga = inflater.inflate(R.layout.nova_recarga_menu_layout, null);
                builder.setView(addRecarga);

                final AlertDialog alertDialog = builder.create();

                final MaterialEditText input = addRecarga.findViewById(R.id.et_name);

                Button btnSalvar = addRecarga.findViewById(R.id.btn_save);
                btnSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double value = Double.parseDouble(input.getText().toString());

                        BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();

                        final double novoSaldo = bilheteUnico.getSaldo() + value;

                        bilheteUnico.setSaldo(novoSaldo);

                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if(res.hasError()){
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(), "Seu saldo foi atualizado. Novo saldo: R$ " + novoSaldo, Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    refreshUI();
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                Button btnCancelar = addRecarga.findViewById(R.id.btn_cancel);
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        return view;
    }

    private void loadUI(View v){

        mProgressBar = v.findViewById(R.id.progress_bar);

        mApelidoBilhete = v.findViewById(R.id.txt_nome_bilhete);
        mEditar = v.findViewById(R.id.btn_editar);
        mSaldoAtual = v.findViewById(R.id.tv_saldo_atual);
        btn_limpar = v.findViewById(R.id.btn_limpar);
        btn_comprar = v.findViewById(R.id.btn_comprar);

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

        Log.i("CURRENT USER", CustomApplication.currentUser.toString());
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