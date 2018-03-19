package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
//import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
//import br.com.consultai.post.RegisterCartaoRequest;
//import br.com.consultai.utils.UtilTempoDigitacao;
//import br.com.consultai.utils.Utility;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.AuthResponse;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.retrofit.RetrofitInitTestes;
import br.com.consultai.util.UtilTempoDigitacao;
import br.com.consultai.util.Utility;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroCartaoActivity extends AppCompatActivity {

    @BindView(R.id.edt_apelido)
    EditText mApelido;

    @BindView(R.id.edt_saldo)
    CurrencyEditText mSaldo;

    @BindView(R.id.edt_numero)
    EditText mNumero;

    private String apelido;
    private String numero;
    private double saldo;
    private boolean estudante;

    @BindView(R.id.btnProximo)
    Button btnProximo;



    @BindView(R.id.checkEstudante)
    CheckBox checkEstudante;

    public static ProgressDialog mDialog;

    public static String tempoApelido, tempoNumero, tempoSaldo;
    public static String coords;
    public static String tempoClique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cartao);

        ButterKnife.bind(this);

        MaskEditTextChangedListener maskNum = new MaskEditTextChangedListener("#########", mNumero);
        mNumero.addTextChangedListener(maskNum);


        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDataFromEditText();
            }
        });

        btnProximo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    UtilTempoDigitacao.inicioTempo();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    UtilTempoDigitacao.fimTempo();
                }
                tempoClique = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });


        mApelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoApelido = String.valueOf(UtilTempoDigitacao.dtfs);


            }

        });
        mSaldo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoSaldo = String.valueOf(UtilTempoDigitacao.dtfs);


            }

        });

        mNumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoNumero = String.valueOf(UtilTempoDigitacao.dtfs);


            }

        });
    }

    @Override
    public void onBackPressed() {
        Paper.book().destroy();

        CustomApplication customApplication = (CustomApplication) getApplicationContext();
        customApplication.destroySession();

        Intent intent = new Intent(CadastroCartaoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void validateDataFromEditText() {
        apelido = mApelido.getText().toString().trim();
        String saldoStr = mSaldo.getText().toString().trim();
        numero = mNumero.getText().toString().trim();

        if (TextUtils.isEmpty(apelido)) {
            mApelido.setError("O campo apelido está vazio.");
            return;
        }

        if (TextUtils.isEmpty(saldoStr)) {
            mSaldo.setError("O campo saldo está vazio.");
            return;
        }

        if (TextUtils.isEmpty(numero)) {
            mNumero.setError("O campo numero está vazio.");
            return;
        }

        if (numero.length() < 9) {
            mNumero.setError("O campo numero deve ter 9 digitos.");
            return;
        }

        if (checkEstudante.isChecked()) {
            estudante = true;
        }else{
            estudante = false;
        }

        if (Utility.stringToFloat(mSaldo.getText().toString()) > 300.00) {
            mSaldo.setError("O valor maximo de saldo é de R$300,00.");
            return;
        }

        saldo = Utility.stringToFloat(mSaldo.getText().toString().trim());

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde");
        mDialog.setMessage("Estamos verificando suas credenciais.");
        mDialog.show();

        createCartao(numero, apelido, saldo, estudante);
    }

    private void createCartao(String numero, String apelido, double saldo, boolean estudante) {

        final BilheteUnico bilheteUnico = new BilheteUnico();

        bilheteUnico.setApelido(apelido);
        bilheteUnico.setSaldo(saldo);
        bilheteUnico.setNumero(numero);
        bilheteUnico.setEstudante(estudante);
        bilheteUnico.setUsuarioID(CustomApplication.currentUser.getId());

        Call<StatusResponse> call = new RetrofitInit(this).getBilheteService().post(bilheteUnico);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if (res.hasError()) {
                    Toast.makeText(CadastroCartaoActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    CustomApplication.currentUser.setBilheteUnico(bilheteUnico);

                    //HashMap<String, String> userData = new HashMap<>();
                    //userData.put("email", user.getEmail());
                    //userData.put("senha", user.getSenha());

                    mDialog.dismiss();
                    Intent intent = new Intent(CadastroCartaoActivity.this, MainActivity.class);
                    //intent.putExtra("user_data", userData);

                    //validarEmail();

                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(CadastroCartaoActivity.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                case MotionEvent.ACTION_MOVE:
//                case MotionEvent.ACTION_UP:
        }

        coords = coords + " x: " + String.valueOf(x) + " y: " + String.valueOf(y) + " | ";

        Log.v("xy", String.valueOf(x) + " " + String.valueOf(y));
//        Toast.makeText(this, x + " " +y, Toast.LENGTH_SHORT).show();
        return false;

    }

    public void validarEmail() {
//        Call<StatusResponse> call = new RetrofitInitTestes(this).getUsuarioService().postEmail(CustomApplication.currentUser.getEmail());
//        call.enqueue(new Callback<StatusResponse>() {
//            @Override
//            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                StatusResponse res = response.body();
//
//                if (res.hasError()) {
//                    Toast.makeText(CadastroCartaoActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Toast.makeText(CadastroCartaoActivity.this, "foi :D", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StatusResponse> call, Throwable t) {
//                mDialog.dismiss();
//                Toast.makeText(CadastroCartaoActivity.this, "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
        Call<StatusResponse> call = new RetrofitInitTestes(this).getUsuarioService().getEmail(CustomApplication.currentUser.getEmail());
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if (res.hasError()) {
                    Toast.makeText(CadastroCartaoActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroCartaoActivity.this, "foi :D", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(CadastroCartaoActivity.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }

}
