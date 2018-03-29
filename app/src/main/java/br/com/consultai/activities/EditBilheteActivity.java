package br.com.consultai.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.MonetaryUtil;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBilheteActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Required(order = 1, message = "Insira o apelido do cartão")
    private TextInputEditText mCartaoApelido;

    @Required(order = 1, message = "Insira o número do cartão")
    private TextInputEditText mCartaoNumero;

    private CheckBox mEstudante;

    private FButton btn_continuar;

    TextView txtSaldo;

    Button btn_limpar, btn_voltar;

    private BilheteUnico bilheteUnico;

    private Validator validator;

    private ProgressBar mProgressBar;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bilhete);
        ButterKnife.bind(this);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Verificando suas credenciais");


        validator = new Validator(this);
        validator.setValidationListener(this);

        txtSaldo = findViewById(R.id.txtSaldo);
        mCartaoApelido = findViewById(R.id.edt_apelido);
        mCartaoNumero = findViewById(R.id.edt_numero);
        mEstudante = findViewById(R.id.checkEstudante);

        MaskEditTextChangedListener maskNum = new MaskEditTextChangedListener("#########", mCartaoNumero);
        mCartaoNumero.addTextChangedListener(maskNum);

        btn_voltar = (Button) findViewById(R.id.btn_voltar);

        btn_limpar = (Button) findViewById(R.id.btn_limpar);

        btn_continuar = findViewById(R.id.btn_continuar);
        mProgressBar = findViewById(R.id.progress_bar);
        updateUI();
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

//                MainActivity.mViewPager.setCurrentItem(2);
            }
        });

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditBilheteActivity.this);
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

                        Call<StatusResponse> call = new RetrofitInit(EditBilheteActivity.this).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                StatusResponse res = response.body();

                                if(res!=null){
                                if (res.hasError()) {
                                    Toast.makeText(EditBilheteActivity.this, "Ops, um erro ocorreu. Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditBilheteActivity.this, "Pronto", Toast.LENGTH_SHORT).show();
                                    BilheteUnico bilheteUnico = CustomApplication.currentUser.getBilheteUnico();
                                    bilheteUnico.setSaldo(0);
                                    dialogInterface.dismiss();
                                    updateUI();
                                }
                            }
                            else{
                                Toast.makeText(EditBilheteActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
                            }
                            }

                            @Override
                            public void onFailure(Call<StatusResponse> call, Throwable t) {
                                Toast.makeText(EditBilheteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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


    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    public void updateUI() {

        Usuario usuario = CustomApplication.currentUser;

        if (usuario.getBilheteUnico() != null) {
            bilheteUnico = usuario.getBilheteUnico();
            txtSaldo.setText(MonetaryUtil.doubleToMonetary(usuario.getBilheteUnico().getSaldo()));
            mCartaoApelido.setText(bilheteUnico.getApelido());
            mCartaoNumero.setText(bilheteUnico.getNumero());
            if (bilheteUnico.isEstudante()) {
                mEstudante.setChecked(true);
            } else {
                mEstudante.setChecked(false);
            }
        }
    }





    public void onValidationSucceeded() {
        if(bilheteUnico.getId() != null){

            mDialog.show();

            bilheteUnico.setApelido(mCartaoApelido.getText().toString());
            bilheteUnico.setNumero(mCartaoNumero.getText().toString());
            bilheteUnico.setEstudante(mEstudante.isChecked());
            bilheteUnico.setOperacao("3");
            bilheteUnico.setId_desconto(null);
            Toast.makeText(this, "est: " + mEstudante.isChecked(), Toast.LENGTH_SHORT).show();
//            Call<StatusResponse> call = new RetrofitInit(this).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
//            call.enqueue(new Callback<StatusResponse>() {
//                @Override
//                public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                    StatusResponse res = response.body();
//
//                    if(res.hasError()){
//                        mProgressBar.setVisibility(View.GONE);
//                        Toast.makeText(EditBilheteActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
//                    }else{
//                        CustomApplication.currentUser.setBilheteUnico(bilheteUnico);
//                        updateUI();
//                        Toast.makeText(EditBilheteActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
//                        mProgressBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<StatusResponse> call, Throwable t) {
//                    Toast.makeText(EditBilheteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    mProgressBar.setVisibility(View.GONE);
//                }
//            });

            // update 26/03

                    Usuario u = CustomApplication.currentUser;
                    BilheteUnico bu = u.getBilheteUnico();


                Call<StatusResponse> call = new RetrofitInit(EditBilheteActivity.this).getBilheteService().atualizarBilhete(u.getId(), bu.getId(), bilheteUnico);
                call.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        StatusResponse res = response.body();

                        if(res!=null){
                        if (res.hasError()) {
                            Toast.makeText(EditBilheteActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(EditBilheteActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            onBackPressed();


                        }
                    }
                    else{
                        Toast.makeText(EditBilheteActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {

                        Toast.makeText(EditBilheteActivity.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });
           // update 26/03
                }


    }

    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if(failedView instanceof TextInputEditText){
            TextInputEditText input = (TextInputEditText)failedView;
            input.setError(failedRule.getFailureMessage());
        }
    }

}
