package br.com.consultai.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.RelativeLayout;


import android.widget.CheckBox;

import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.InputValidator;
import br.com.consultai.util.ValidarCPF;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_nome)
    MaterialEditText mNome;

    @BindView(R.id.et_email)
    MaterialEditText mEmail;

    @BindView(R.id.et_senha)
    MaterialEditText mSenha;

    @BindView(R.id.et_confirmar_senha)
    MaterialEditText mConfirmaSenha;



    @BindView(R.id.sp_sexo)
    Spinner mSexo;

    @BindView(R.id.et_nasc)
    MaterialEditText mNascimento;

    @BindView(R.id.et_cpf)
    MaterialEditText mCPF;

    @BindView(R.id.et_cel)
    MaterialEditText mCelular;

    private Usuario usuario = new Usuario();

    RelativeLayout rel1, rel2;

     String email = "";
     String senha = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        rel1 = (RelativeLayout) findViewById(R.id.rel1);
        rel2 = (RelativeLayout) findViewById(R.id.rel2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Masculino", "Feminino"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSexo.setAdapter(adapter);

        MaskEditTextChangedListener maskCel = new MaskEditTextChangedListener("(##) #####-####", mCelular);
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", mCPF);
        MaskEditTextChangedListener maskNasc = new MaskEditTextChangedListener("##/##/####", mNascimento);

        mCelular.addTextChangedListener(maskCel);
        mCPF.addTextChangedListener(maskCPF);
        mNascimento.addTextChangedListener(maskNasc);


    }

    private void validateDataFromInput(){
        String nome = mNome.getText().toString();
        email = mEmail.getText().toString();
        senha = mSenha.getText().toString();
        String confirmaSenha = mConfirmaSenha.getText().toString();

        if(nome.length() > 32){
            mNome.setError("Seu nome deve ter no máximo 32 caracteres.");
            return;
        }

        if(!InputValidator.isValidName(nome)){
            mNome.setError("Nome em branco ou com formato inválido.");
            return;
        }

        if(!InputValidator.isEmailValid(email)){
            mEmail.setError("Email no formato inválido.");
            return;
        }

        if(senha.length() < 6 || senha.length() > 60){
            mSenha.setError("Sua senha deve ter no minímmo 6 e no máximo 60 caracteres.");
            return;
        }

        if(!senha.equals(confirmaSenha)){
            mConfirmaSenha.setError("As senhas digitadas não coincidem.");
            return;
        }



//        BilheteUnico bilheteUnico = new BilheteUnico();
//        bilheteUnico.setApelido(mApelidoBilhete.getText().toString());
//        bilheteUnico.setSaldo(Double.parseDouble(mSaldoBilhete.getText().toString()));
//        bilheteUnico.setNumero(mNumeroBilhete.getText().toString());
//        bilheteUnico.setEstudante(mEstudante.isChecked());
//
//        usuario.setBilheteUnico(bilheteUnico);

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        rel1.setVisibility(View.GONE);
        rel2.setVisibility(View.VISIBLE);

    }

    private void validateDataFromInput2(){
        final String sexo = mSexo.getSelectedItem().toString().substring(0,1).toUpperCase();
        final String nasc = mNascimento.getText().toString();
        final String cpf = mCPF.getText().toString();
        final String celular = mCelular.getText().toString();

        if(nasc.isEmpty()){
            mNascimento.setError("Preencha sua data de nascimento");
            return;
        }


        if(celular.length() < 14 || celular.length() > 14 ){
            mCelular.setError("celular no formato inválido.");
            return;
        }
        if(ValidarCPF.isCPF(mCPF.toString()) == false){
            mCPF.setError("CPF invalido");
        }


        usuario.setSexo(sexo);
        usuario.setDataNascimento(nasc);
        usuario.setCPF(cpf);
        usuario.setTelefone(celular);



        BilheteUnico bilheteUnico = new BilheteUnico();
        bilheteUnico.setApelido("XXXXX");
        bilheteUnico.setSaldo(Double.parseDouble("00"));
        bilheteUnico.setNumero("XXXXX");
        bilheteUnico.setEstudante(true);
        usuario.setBilheteUnico(bilheteUnico);


        Call<StatusResponse> call = new RetrofitInit(this).getUsuarioService().register(usuario);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if(res.hasError()){
                    Toast.makeText(RegisterActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("email", email);
                    userData.put("senha", senha);

                    Intent intent = new Intent(RegisterActivity.this, CadastroCartaoActivity.class);
                    intent.putExtra("user_data", userData);

                    startActivity(intent);
                    finish();


                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlerToRegister(View v){
        validateDataFromInput();
    }

    public void handlerToMainActivity(View v){
        validateDataFromInput2();
    }
}
