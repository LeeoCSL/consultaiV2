package br.com.consultai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import java.util.HashMap;

import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.MainActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.activities.RegisterActivity2;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.AuthResponse;
import br.com.consultai.dto.CadCompResponse;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.InputValidator;
import br.com.consultai.util.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private boolean comingFromRegister = false;

    @BindView(R.id.et_email)
    EditText mEmail;

    @BindView(R.id.et_senha)
    EditText mSenha;

    @BindView(R.id.cb_remember_me)
    CheckBox mRememberMe;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Verificando suas credenciais");

        Paper.init(this);

        // VERIFICA SE O USUÁRIO VEIO DA TELA DE REGISTRO, ASSIM LOGA AUTOMATICAMENTE
        HashMap<String, String> userData = (HashMap<String, String>) getIntent().getSerializableExtra("user_data");

        String emailRememberMe = Paper.book().read("email");
        String passwordRememberMe = Paper.book().read("password");

        if(emailRememberMe != null && passwordRememberMe != null){
            if(!emailRememberMe.isEmpty() && !passwordRememberMe.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setEmail(emailRememberMe);
                usuario.setSenha(passwordRememberMe);

                comingFromRegister = true;
                logUser(usuario);
            }
        }
        else if(userData != null){
            String email = userData.get("email");
            String senha = userData.get("senha");

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

    private void logUser(final Usuario usuario){
        mDialog.show();
        if(!Utility.isNetworkAvailable(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Você não está conectado a internet");
            builder.setMessage("Por favor conecte-se à internet para continuar");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    mDialog.dismiss();
                }
            });
            builder.show();
        }
        else {

            Call<AuthResponse> call = new RetrofitInit(this).getUsuarioService().auth(usuario);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                    if (!comingFromRegister) {
                        blockUI(false);
                    }

                    AuthResponse authResponse = response.body();

                    if (authResponse.hasError()) {
                        Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else {
                        CustomApplication customApplication = (CustomApplication) getApplicationContext();

                        Usuario u = authResponse.getUsuario();

                        CustomApplication.currentUser = u;
                        customApplication.setAPItoken(authResponse.getToken());


                        mDialog.dismiss();

                        if (mRememberMe.isChecked()) {
                            Paper.book().write("email", usuario.getEmail());
                            Paper.book().write("password", usuario.getSenha());
                        }

                        //verificação infos complementares
                        //requisição, set dados ao currentuser

                        testeCadComp();


                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {

                    mDialog.dismiss();

                    if (!comingFromRegister) {
                        blockUI(false);
                        Toast.makeText(LoginActivity.this, "Desculpe, não conseguimos nos conectar com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void testeCadComp(){
        Toast.makeText(this, "chegou aqui", Toast.LENGTH_SHORT).show();

        CustomApplication customApplication = (CustomApplication) getApplicationContext();

        Usuario u = CustomApplication.currentUser;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String loginToken = sharedPref.getString("token", null);

        Call<CadCompResponse> call = new RetrofitInit(this).getUsuarioService().getCad(CustomApplication.currentUser.getId());
        call.enqueue(new Callback<CadCompResponse>() {
            @Override
            public void onResponse(Call<CadCompResponse> call, Response<CadCompResponse> response) {
                CadCompResponse res = response.body();

                if(res.hasError()){
                    Toast.makeText(LoginActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "funfou", Toast.LENGTH_SHORT).show();

                    CustomApplication customApplication = (CustomApplication) getApplicationContext();

                    Usuario u = CustomApplication.currentUser;

                    String cpf = res.getCpf();
                    String telefone = res.getTelefone();
                    String dtn = res.getData_nascimento();
                    u.setCPF(cpf);
                    u.setTelefone(telefone);
                    u.setDataNascimento(dtn);


                    if (u.getCPF() == null || u.getDataNascimento() == null || u.getTelefone() == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Cadastro complementar");
                        builder.setMessage("Você deseja cadastrar as informações complementares do cadastro?");

                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(LoginActivity.this, RegisterActivity2.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Faço isso depois", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Usuario us = CustomApplication.currentUser;
                                if (us.getBilheteUnico() == null) {
                                    Intent intent = new Intent(LoginActivity.this, CadastroCartaoActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                dialogInterface.dismiss();
                            }
                        });

                        builder.show();
                    } else {

                        if (u.getBilheteUnico() == null) {
                            Intent intent = new Intent(LoginActivity.this, CadastroCartaoActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<CadCompResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void handlerToRegisterActivity(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void blockUI(boolean block){
        if(block){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


}
