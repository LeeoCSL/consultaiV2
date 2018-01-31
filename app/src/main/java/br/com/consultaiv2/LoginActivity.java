package br.com.consultaiv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import br.com.consultaiv2.activities.MainActivity;
import br.com.consultaiv2.activities.RegisterActivity;
import br.com.consultaiv2.application.CustomApplication;
import br.com.consultaiv2.dto.AuthResponse;
import br.com.consultaiv2.dto.RegisterResponse;
import br.com.consultaiv2.model.Usuario;
import br.com.consultaiv2.retrofit.RetrofitInit;
import br.com.consultaiv2.util.InputValidator;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private boolean comingFromRegister = false;

    @BindView(R.id.et_email)
    EditText mEmail;

    @BindView(R.id.et_senha)
    EditText mSenha;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Verificando suas credenciais");

        // VERIFICA SE O USUÁRIO VEIO DA TELA DE REGISTRO, ASSIM LOGA AUTOMATICAMENTE
        HashMap<String, String> userData = (HashMap<String, String>) getIntent().getSerializableExtra("user_data");

        if(userData != null){
            String email = userData.get("email");
            String senha = userData.get("password");

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);

            comingFromRegister = true;
            logUser(usuario);
        }else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }
    }

    private void logUser(Usuario usuario){
        mDialog.show();

        Call<AuthResponse> call = new RetrofitInit(this).getUsuarioService().auth(usuario);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if(!comingFromRegister){
                    blockUI(false);
                }

                AuthResponse authResponse = response.body();

                if(authResponse.hasError()){
                    Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }else{
                    CustomApplication customApplication = (CustomApplication) getApplicationContext();

                    Usuario u = authResponse.getUsuario();

                    CustomApplication.currentUser = u;
                    customApplication.setAPItoken(authResponse.getToken());

                    mDialog.dismiss();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

                mDialog.dismiss();

                if(!comingFromRegister){
                    blockUI(false);
                    Toast.makeText(LoginActivity.this, "Desculpe, não conseguimos nos conectar com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validateDataFromInput(){
        String email = mEmail.getText().toString().trim();
        String password = mSenha.getText().toString().trim();

        if(!InputValidator.isEmailValid(email)){
            mEmail.setError("Email inválido.");
            return;
        }

        if(password.length() < 1 || password.length() > 32){
            mSenha.setError("Senha inválida.");
            return;
        }

        final Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setSenha(password);

        logUser(usuario);
    }

    public void handlerToMainActivity(View v){
        validateDataFromInput();
    }

    private void blockUI(boolean block){
        if(block){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void handlerToRegisterActivity(View v){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
