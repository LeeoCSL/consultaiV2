package br.com.consultai.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import br.com.consultai.R;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.EditarCartaoActivity;
import br.com.consultai.activities.MainActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.eventbus.events.UpdateUserSaldoEventt;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Credencial;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.retrofit.RetrofitInitCompra;
import br.com.consultai.util.MonetaryUtil;
import br.com.consultai.util.Utility;
import info.hoang8f.widget.FButton;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.TELEPHONY_SERVICE;


public class HomeFragment extends Fragment {

    private Button mEditar, mExcluir, btn_comprar, btn_limpar;

    private TextView mApelidoBilhete;
    private TextView mSaldoAtual;

    private FButton mBtnRecarga;

    private ProgressBar mProgressBar;

    private CustomToggleButton mDomingo, mSegunda, mTerca, mQuarta, mQuinta, mSexta, mSabado;
    private CustomToggleButton[] mWeekDays = new CustomToggleButton[7];

    public HomeFragment() {
    }

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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.viagem_mais, null);
                ImageView comoFunciona = (ImageView) mView.findViewById(R.id.btnComoFunciona);
                ImageView comoFuncionaMenos = (ImageView) mView.findViewById(R.id.btnComoFuncionaMenos);

                Button maisOnibusComum = (Button) mView.findViewById(R.id.btnMaisOnibusComum);
                Button maisEstudante = (Button) mView.findViewById(R.id.btnMaisEstudante);
                Button maisIntegracaoComum = (Button) mView.findViewById(R.id.btnMaisIntegracaoComum);


                Button menosOnibusComum = (Button) mView.findViewById(R.id.btnMenosOnibusComum);
                Button menosIntegracaoComum = (Button) mView.findViewById(R.id.btnMenosIntegracaoComum);
                Button menosEstudante = (Button) mView.findViewById(R.id.btnMenosEstudante);

                Usuario usuario = CustomApplication.currentUser;
                BilheteUnico bu = usuario.getBilheteUnico();


                if (!bu.isEstudante()) {
                    maisOnibusComum.setVisibility(View.VISIBLE);
                    maisIntegracaoComum.setVisibility(View.VISIBLE);
                    menosOnibusComum.setVisibility(View.VISIBLE);
                    menosIntegracaoComum.setVisibility(View.VISIBLE);
                    maisEstudante.setVisibility(View.INVISIBLE);
                    menosEstudante.setVisibility(View.INVISIBLE);
                } else if (bu.isEstudante()) {
                    maisOnibusComum.setVisibility(View.INVISIBLE);
                    maisIntegracaoComum.setVisibility(View.INVISIBLE);
                    menosOnibusComum.setVisibility(View.INVISIBLE);
                    menosIntegracaoComum.setVisibility(View.INVISIBLE);
                    maisEstudante.setVisibility(View.VISIBLE);
                    menosEstudante.setVisibility(View.VISIBLE);
                }

                mBuilder.setView(mView);
                final AlertDialog dialog1 = mBuilder.create();

                comoFunciona.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Adicione uma viagem extra sempre que fizer um trajeto diferente do que está definido em sua rotina.");

                        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create();
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                comoFuncionaMenos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Exclua uma viagem sempre que fizer um trajeto diferente do que está definido em sua rotina.");

                        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                maisOnibusComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        final double value = 4.00;

                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        final double novoSaldo = bilheteUnico.getSaldo() - value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("2");
                        bilheteUnico.setId_desconto("0");
                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
                                    dialog1.dismiss();
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();

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
                maisIntegracaoComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        final double value = 2.96;

                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        final double novoSaldo = bilheteUnico.getSaldo() - value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("2");
                        bilheteUnico.setId_desconto("0");
                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
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

                maisEstudante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        final double value = 2.00;

                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        final double novoSaldo = bilheteUnico.getSaldo() - value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("2");
                        bilheteUnico.setId_desconto("0");
                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
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

                menosOnibusComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        final double value = 4.00;

                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        final double novoSaldo = bilheteUnico.getSaldo() + value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("2");
                        bilheteUnico.setId_desconto("1");
                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
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

                menosIntegracaoComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        final double value = 2.96;

                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        final double novoSaldo = bilheteUnico.getSaldo() + value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("2");
                        bilheteUnico.setId_desconto("1");
                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
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

                menosEstudante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        final double value = 2.00;

                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        final double novoSaldo = bilheteUnico.getSaldo() + value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("2");
                        bilheteUnico.setId_desconto("1");

                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
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


                dialog1.show();
            }
        });



        btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = CustomApplication.currentUser;

                Credencial credencial = new Credencial();
                credencial.setChecksum("711sbba7dc09522f2bef16626be459179454b1bd30887a4342b752e342b752e37ed81");
                credencial.setGcmID(FirebaseInstanceId.getInstance().getToken());
                credencial.setIdSistemaOperacional(Build.ID);
                credencial.setModeloComercial(Build.BRAND);
                credencial.setModeloDispositivo(Build.MODEL);
                credencial.setNomeOperadora(MainActivity.nomeOperadoraa);
                credencial.setSerialTerminal(Build.SERIAL);
                credencial.setSimCardSerialNumber(MainActivity.SChip);
                credencial.setVersaoAPP(MainActivity.versao);
                credencial.setVersaoDLL("af9c4ced12205dfe4af4f04acd6fe98c761c45f1339b00f808d5aec1aa7a3b18");
                credencial.setVersaoOS(Build.VERSION.RELEASE);
                credencial.setEmail(usuario.getEmail());
                credencial.setIdAplicacao("30");

//                Call<StatusResponse> call = new RetrofitInitCompra(getActivity()).getCompraService().inicializar(credencial);
//                call.enqueue(new Callback<StatusResponse>() {
//                    @Override
//                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                        StatusResponse res = response.body();
//
//                        if(res.hasError()){
//                            Toast.makeText(getContext(), "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(getContext(), "oia deu certo " + res.getMessage(), Toast.LENGTH_SHORT).show();
//
                Intent intent = new Intent(getContext(), ComprarActivity.class);
                startActivity(intent);
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusResponse> call, Throwable t) {
//                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                        mProgressBar.setVisibility(View.GONE);
//                    }
//                });


            }
        });

        mEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Utility.isNetworkAvailable(getContext())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Você não está conectado a internet");
                    builder.setMessage("Por favor conecte-se à internet para continuar");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();
                }
                else {
                handlerToEditarCartaoActivity();
            }
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

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                                    mProgressBar.setVisibility(View.GONE);
                                } else {
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
                        final double value = Double.parseDouble(input.getText().toString());

                        final BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                        double saldoAnt = bilheteUnico.getSaldo();
//
                        bilheteUnico.setSaldoAnterior(saldoAnt);
                        final double novoSaldo = bilheteUnico.getSaldo() + value;

                        bilheteUnico.setSaldo(novoSaldo);
                        bilheteUnico.setOperacao("1");
                        bilheteUnico.setId_desconto(null);

                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                    bilheteUnico.setSaldo(novoSaldo - value);
                                } else {
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

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Limpar saldo?");

                builder.setMessage("Deseja realmente limpar seu saldo?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();


                        bilheteUnico.setSaldoAnterior(bilheteUnico.getSaldo());
                        bilheteUnico.setSaldo(0);
                        bilheteUnico.setOperacao("0");
                        bilheteUnico.setId_desconto(null);

                        Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if (res.hasError()) {
                                    Toast.makeText(getActivity(), "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Pronto", Toast.LENGTH_SHORT).show();
                                    BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                                    bilheteUnico.setSaldo(0);
                                    dialogInterface.dismiss();
                                    refreshUI();
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
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


    private void loadUI(View v) {

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
    public void updateUserSaldo(UpdateUserSaldoEventt event) {
        refreshUI();
    }

    public void refreshUI() {
        Usuario usuario = CustomApplication.currentUser;

        if (usuario.getRotinas().size() > 0) {
            boolean[] diasUso = usuario.getRotinas().get(0).getDiasUso().getDiasUso();

            for (int i = 0; i < diasUso.length; i++) {
                mWeekDays[i].setChecked(diasUso[i]);
            }
        } else {
            for (int i = 0; i < mWeekDays.length; i++) {
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