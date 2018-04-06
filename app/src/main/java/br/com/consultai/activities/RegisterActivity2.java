package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.ValidarCPF;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity2 extends AppCompatActivity {


    @BindView(R.id.et_nasc)
    MaterialEditText mNascimento;

    @BindView(R.id.et_cpf)
    MaterialEditText mCPF;

    @BindView(R.id.et_cel)
    MaterialEditText mCelular;

    @BindView(R.id.btn_nao_cadastrar)
    Button mBtnNaoCadastrar;

    Button btn_cadastrar;


    public static ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

//        mNascimento = (MaterialEditText) findViewById(R.id.et_nasc);
//        mCPF = (MaterialEditText) findViewById(R.id.et_cpf);
//        mCelular = (MaterialEditText) findViewById(R.id.et_cel);
        ButterKnife.bind(this);

        Usuario u = CustomApplication.currentUser;

        MaskEditTextChangedListener maskCel = new MaskEditTextChangedListener("(##) #####-####", mCelular);
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", mCPF);
        MaskEditTextChangedListener maskNasc = new MaskEditTextChangedListener("##/##/####", mNascimento);

        mCelular.addTextChangedListener(maskCel);
        mCPF.addTextChangedListener(maskCPF);
        mNascimento.addTextChangedListener(maskNasc);

        if(u.getCPF() != null){
            mCPF.setText(u.getCPF());
        }
        if(u.getTelefone() != null){
            mCelular.setText(u.getTelefone());
        }
        if(u.getDataNascimento() != null){
            mNascimento.setText(u.getDataNascimento());
        }

        mBtnNaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usuario usuario = CustomApplication.currentUser;

                if (usuario.getBilheteUnico() == null) {

                    Intent intent = new Intent(RegisterActivity2.this, CadastroCartaoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog = new ProgressDialog(RegisterActivity2.this);
                mDialog.setTitle("Aguarde");
                mDialog.setMessage("Estamos verificando suas credenciais.");
                mDialog.show();
                validateDataFromInput2();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Paper.book().destroy();

        CustomApplication customApplication = (CustomApplication) getApplicationContext();
        customApplication.destroySession();
finish();
//        Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }

    private void validateDataFromInput2() {

        Usuario usuario = CustomApplication.currentUser;

        final String nasc = mNascimento.getText().toString();
        final String cpf = mCPF.getText().toString();
        final String celular = mCelular.getText().toString();

        if (nasc.isEmpty()) {
            mNascimento.setError("Preencha sua data de nascimento");
            mDialog.dismiss();
            return;
        }


        if (celular.length() < 15 || celular.length() > 15) {
            mCelular.setError("celular no formato inválido.");
            mDialog.dismiss();
            return;
        }
        if (ValidarCPF.isCPF(cpf) == false) {
            mCPF.setError("CPF invalido");
            mDialog.dismiss();
            return;
        }

        String envioCpf = cpf.replace(".", "");
        envioCpf = envioCpf.replace("-", "");

        String envioTel = celular.replace("(", "");
        envioTel = envioTel.replace(")", "");
        envioTel = envioTel.replace(" ", "");
        envioTel = envioTel.replace("-", "");

        String envioNasc = nasc.replace("/", "");

        usuario.setDataNascimento(nasc);
        usuario.setCPF(envioCpf);
        usuario.setTelefone(envioTel);


        Call<StatusResponse> call = new RetrofitInit(this).getUsuarioService().atualizaUser(CustomApplication.currentUser.getId(), usuario);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if (res != null) {
                    if (res.hasError()) {
                        Toast.makeText(RegisterActivity2.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else {

                        Usuario u = CustomApplication.currentUser;

//                    CustomApplication customApplication = (CustomApplication) getApplicationContext();
//
//                    Usuario u = StatusResponse.getUsuario();
//
//                    CustomApplication.currentUser = u;
//                    customApplication.setAPItoken(StatusResponse.getToken());
                        Toast.makeText(RegisterActivity2.this, res.getMessage(), Toast.LENGTH_SHORT).show();


                        if (u.getBilheteUnico() == null) {
                            mDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity2.this, CadastroCartaoActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            mDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(RegisterActivity2.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(RegisterActivity2.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void handlerToMainActivity(View v) {
//        validateDataFromInput2();
    }

}
