package br.com.consultai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mukeshsolanki.sociallogin.facebook.FacebookHelper;
import com.mukeshsolanki.sociallogin.facebook.FacebookListener;
import com.rey.material.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.MainActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.activities.RegisterActivity2;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.AuthFacebookResponse;
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

public class LoginActivity extends AppCompatActivity implements FacebookListener {

    private boolean comingFromRegister = false;

    @BindView(R.id.et_email)
    EditText mEmail;

    @BindView(R.id.et_senha)
    EditText mSenha;

    @BindView(R.id.cb_remember_me)
    CheckBox mRememberMe;

    private ProgressDialog mDialog;

    private FacebookHelper mFacebookHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        // FACEBOOK SETTINGS
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(this);
        mFacebookHelper = new FacebookHelper(this);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Verificando suas credenciais");

        Paper.init(this);

        // VERIFICA SE O USUÁRIO VEIO DA TELA DE REGISTRO, ASSIM LOGA AUTOMATICAMENTE
        HashMap<String, String> userData = (HashMap<String, String>) getIntent().getSerializableExtra("user_data");


        String emailRememberMe = Paper.book().read("email");
        String passwordRememberMe = Paper.book().read("password");

        if (emailRememberMe != null && passwordRememberMe != null) {
            if (!emailRememberMe.isEmpty() && !passwordRememberMe.isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(emailRememberMe);
                usuario.setSenha(passwordRememberMe);

                mEmail.setText(emailRememberMe);
                mSenha.setText(passwordRememberMe);

                comingFromRegister = true;
                logUser(usuario);
            }
        } else if (userData != null) {
            String email = userData.get("email");
            String senha = userData.get("senha");

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);

            comingFromRegister = true;
            logUser(usuario);
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }
    }

    private void logUser(final Usuario usuario) {

        mDialog.show();

        if (!Utility.isNetworkAvailable(this)) {
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
        } else {
            Call<AuthResponse> call = new RetrofitInit(this).getUsuarioService().auth(usuario);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                    if (!comingFromRegister) {
                        blockUI(false);
                    }

                    AuthResponse authResponse = response.body();
                    if (authResponse != null) {
                        if (authResponse.hasError()) {
                            Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else {
                            CustomApplication customApplication = (CustomApplication) getApplicationContext();

                            Usuario u = authResponse.getUsuario();

                            CustomApplication.currentUser = u;


                            customApplication.setAPItoken(authResponse.getToken());
                            String notification_token = FirebaseInstanceId.getInstance().getToken();

                            //update 26/03
                            if (customApplication.getAPItoken() != notification_token) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("notification_token", notification_token);
//                            map.put("email", CustomApplication.currentUser.getEmail());
                                patchNotification(map);
                            }
                            //update 26/03


                            mDialog.dismiss();


                            if (mRememberMe.isChecked()) {
                                Paper.book().write("email", usuario.getEmail());
                                Paper.book().write("password", usuario.getSenha());
                            }


                            if (u.getBilheteUnico() == null) {
                                Intent intent = new Intent(LoginActivity.this, CadastroCartaoActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }


                            //verificação infos complementares
                            //requisição, set dados ao currentuser

//                        testeCadComp();


                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
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

    public void testeCadComp() {

        CustomApplication customApplication = (CustomApplication) getApplicationContext();

        Usuario u = CustomApplication.currentUser;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String loginToken = sharedPref.getString("token", null);

        Call<CadCompResponse> call = new RetrofitInit(this).getUsuarioService().getCad(CustomApplication.currentUser.getId());
        call.enqueue(new Callback<CadCompResponse>() {
            @Override
            public void onResponse(Call<CadCompResponse> call, Response<CadCompResponse> response) {
                CadCompResponse res = response.body();

                if (res != null) {
                    if (res.hasError()) {
                        Toast.makeText(LoginActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {

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
                } else {
                    Toast.makeText(LoginActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(Call<CadCompResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void validateDataFromInput() {
        String email = mEmail.getText().toString().trim();
        String password = mSenha.getText().toString().trim();

        if (!InputValidator.isEmailValid(email)) {
            mEmail.setError("Email inválido.");
            return;
        }

        if (password.length() < 1 || password.length() > 32) {
            mSenha.setError("Senha inválida.");
            return;
        }

        final Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setSenha(password);

        logUser(usuario);
    }

    public void handlerToMainActivity(View v) {
        validateDataFromInput();
    }

    public void handlerToRegisterActivity(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void blockUI(boolean block) {
        if (block) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String mail = Paper.book().read("email");
        String pass = Paper.book().read("password");

        mEmail.setText(mail);
        mSenha.setText(pass);
    }

    public void handlerFacebookLogin(View v) {
        mFacebookHelper.performSignIn(LoginActivity.this);
    }

    @Override
    public void onFbSignInFail(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFbSignInSuccess(String s, String s1) {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        try {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String gender = object.getString("gender");

                            // SE O USUARIO TIVER O EMAIL PRIVADO
                            if (email == null || email.isEmpty()) {

                            }

                            final Usuario usuario = new Usuario();
                            usuario.setId(id);
                            usuario.setNome(name);
                            usuario.setEmail(email);
                            usuario.setSexo(String.valueOf(gender.charAt(0)));

                            Log.i("USERFACEBOOK", usuario.toString());


                            Call<AuthFacebookResponse> call = new RetrofitInit(LoginActivity.this).getUsuarioService().authFacebook(usuario);
                            call.enqueue(new Callback<AuthFacebookResponse>() {
                                @Override
                                public void onResponse(Call<AuthFacebookResponse> call, Response<AuthFacebookResponse> response) {
                                    AuthFacebookResponse res = response.body();

                                    if (res != null) {
                                        if (res.isCollapse()) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle("Email em uso");
                                            builder.setMessage("Este email já está sendo usado por uma conta normal. Deseja vincular sua conta com o email já existente?");

                                            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    usuario.setOverlap(true);

                                                    // CHAMADA PRA SUBSTITUIR O EMAIL
                                                    Call<AuthFacebookResponse> callOverlap = new RetrofitInit(LoginActivity.this).getUsuarioService().authFacebook(usuario);
                                                    callOverlap.enqueue(new Callback<AuthFacebookResponse>() {
                                                        @Override
                                                        public void onResponse(Call<AuthFacebookResponse> call, Response<AuthFacebookResponse> response) {
                                                            AuthFacebookResponse overlapRes = response.body();

                                                            Usuario usuarioFacebook = overlapRes.getUsuario();
                                                            //SE O USUARIO FOI SUBSTITUIDO VERIFICA SE POSSUI BILHETE
                                                            Log.i("CHEGOU AQUI", usuarioFacebook.toString());
                                                            if (usuarioFacebook.getBilheteUnico() != null) {
                                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                            } else {
                                                                startActivity(new Intent(LoginActivity.this, CadastroCartaoActivity.class));
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<AuthFacebookResponse> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            });

                                            builder.setNegativeButton("Não", null);
                                            builder.show();
                                        } else {
                                            if (res.isError()) {

                                            } else {
                                                Usuario usuarioFacebook = res.getUsuario();
                                                CustomApplication.currentUser = usuarioFacebook;

                                                if (usuarioFacebook.getBilheteUnico() != null) {
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                } else {
                                                    startActivity(new Intent(LoginActivity.this, CadastroCartaoActivity.class));
                                                }
                                            }
                                        }

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<AuthFacebookResponse> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onFBSignOut() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mFacebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void patchNotification(HashMap map) {
        Call<StatusResponse> call = new RetrofitInit(this).getUsuarioService().setToken(CustomApplication.currentUser.getId(), map);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if (res != null) {
                    if (res.hasError()) {
                        Toast.makeText(LoginActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else {

                        String message = res.getMessage();
                        Toast.makeText(LoginActivity.this, "notification token ok", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
