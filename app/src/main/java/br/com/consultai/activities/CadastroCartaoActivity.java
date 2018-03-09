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
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.UtilTempoDigitacao;
import br.com.consultai.util.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    String apelido, numero;
    double  saldo;

    @BindView(R.id.btnProximo)
    Button btnProximo;

    Boolean estudante;

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


        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDataFromEditText();
            }
        });

        btnProximo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
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
            mNumero.setError("O campo numero deve ter ao menos 9 digitos.");
            return;
        }

        if (checkEstudante.isChecked()) {
            estudante = true;
        }

        if (!checkEstudante.isChecked()) {
            estudante = false;
        }

        saldo = Utility.stringToFloat(mSaldo.getText().toString().trim());

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde");
        mDialog.setMessage("Estamos verificando suas credenciais.");
        mDialog.show();

        createCartao(numero, apelido, String.valueOf(saldo), String.valueOf(estudante));
    }

    private void createCartao(String numero, String apelido, String saldo, String estudante) {
        BilheteUnico bilheteUnico = new BilheteUnico();
        bilheteUnico.setApelido(mApelido.getText().toString());
        bilheteUnico.setSaldo(Double.parseDouble(mSaldo.getText().toString().substring(2, mSaldo.length())));
        bilheteUnico.setNumero(mNumero.getText().toString());
        bilheteUnico.setEstudante(checkEstudante.isChecked());
        bilheteUnico.setOperacao(null);
        bilheteUnico.setId_desconto(null);

        final Usuario user = CustomApplication.currentUser;

        bilheteUnico.setId_usuario(CustomApplication.currentUser.getId());

        user.setBilheteUnico(bilheteUnico);

        Call<StatusResponse> call = new RetrofitInit(this).getBilheteService().post(CustomApplication.currentUser.getId(),bilheteUnico);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if (res.hasError()) {
                    Toast.makeText(CadastroCartaoActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("email", user.getEmail());
                    userData.put("senha", user.getSenha());

                    mDialog.dismiss();
                    Intent intent = new Intent(CadastroCartaoActivity.this, MainActivity.class);
                    intent.putExtra("user_data", userData);

                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(CadastroCartaoActivity.this, "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();

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

}
