package br.com.consultai.activities;

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

    Button btn_cadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

//        mNascimento = (MaterialEditText) findViewById(R.id.et_nasc);
//        mCPF = (MaterialEditText) findViewById(R.id.et_cpf);
//        mCelular = (MaterialEditText) findViewById(R.id.et_cel);
        ButterKnife.bind(this);

        MaskEditTextChangedListener maskCel = new MaskEditTextChangedListener("(##) #####-####", mCelular);
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", mCPF);
        MaskEditTextChangedListener maskNasc = new MaskEditTextChangedListener("##/##/####", mNascimento);

        mCelular.addTextChangedListener(maskCel);
        mCPF.addTextChangedListener(maskCPF);
        mNascimento.addTextChangedListener(maskNasc);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDataFromInput2();
            }
        });

    }

    private void validateDataFromInput2(){

        Usuario usuario = CustomApplication.currentUser;

        final String nasc = mNascimento.getText().toString();
        final String cpf = mCPF.getText().toString();
        final String celular = mCelular.getText().toString();

        if(nasc.isEmpty()){
            mNascimento.setError("Preencha sua data de nascimento");
            return;
        }


        if(celular.length() < 15 || celular.length() > 15 ){
            mCelular.setError("celular no formato inválido.");
            return;
        }
        if(ValidarCPF.isCPF(mCPF.toString()) == false){
            mCPF.setError("CPF invalido");
        }



        usuario.setDataNascimento(nasc);
        usuario.setCPF(cpf);
        usuario.setTelefone(celular);


        Call<StatusResponse> call = new RetrofitInit(this).getUsuarioService().put(CustomApplication.currentUser.getId(), usuario);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if(res.hasError()){
                    Toast.makeText(RegisterActivity2.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                }else{

                    Usuario u = CustomApplication.currentUser;

//                    CustomApplication customApplication = (CustomApplication) getApplicationContext();
//
//                    Usuario u = StatusResponse.getUsuario();
//
//                    CustomApplication.currentUser = u;
//                    customApplication.setAPItoken(StatusResponse.getToken());




                    if (u.getBilheteUnico() == null) {
                        Intent intent = new Intent(RegisterActivity2.this, CadastroCartaoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {

                        Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }




                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity2.this, "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void handlerToMainActivity(View v){
//        validateDataFromInput2();
    }

}
