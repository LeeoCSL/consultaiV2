package br.com.consultaiv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import br.com.consultaiv2.LoginActivity;
import br.com.consultaiv2.R;
import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.model.BilheteUnico;
import br.com.consultaiv2.model.Usuario;
import br.com.consultaiv2.retrofit.RetrofitInit;
import br.com.consultaiv2.util.InputValidator;
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

    @BindView(R.id.et_apelido)
    MaterialEditText mApelidoBilhete;

    @BindView(R.id.et_numero_bilhete)
    MaterialEditText mNumeroBilhete;

    @BindView(R.id.et_saldo)
    MaterialEditText mSaldoBilhete;

    @BindView(R.id.cb_estudante)
    CheckBox mEstudante;

    @BindView(R.id.sp_sexo)
    Spinner mSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Masculino", "Feminino"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSexo.setAdapter(adapter);

    }

    private void validateDataFromInput(){
        String nome = mNome.getText().toString();
        final String email = mEmail.getText().toString();
        final String senha = mSenha.getText().toString();
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

        BilheteUnico bilheteUnico = new BilheteUnico();
        bilheteUnico.setApelido(mApelidoBilhete.getText().toString());
        bilheteUnico.setSaldo(Double.parseDouble(mSaldoBilhete.getText().toString()));
        bilheteUnico.setNumero(mNumeroBilhete.getText().toString());
        bilheteUnico.setEstudante(mEstudante.isChecked());

        Usuario usuario = new Usuario();
        usuario.setBilheteUnico(bilheteUnico);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setSexo((String)(mSexo.getSelectedItem()).toString().substring(0,1).toUpperCase());

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
                    userData.put("password", senha);

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

    public void handlerToMainActivity(View v){
        validateDataFromInput();
    }
}
