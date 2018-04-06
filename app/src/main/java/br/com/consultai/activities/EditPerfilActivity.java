package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.InputValidator;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPerfilActivity extends AppCompatActivity {

    @BindView(R.id.edt_nasc)
    MaterialEditText mNascimento;

    @BindView(R.id.edt_tel)
    MaterialEditText mCelular;

    @BindView(R.id.edt_nome)
    MaterialEditText mNome;

    @BindView(R.id.btn_voltar)
    Button mBtn_voltar;

    @BindView(R.id.btn_continuar)
    Button mBtn_continuar;

    @BindView(R.id.edt_email)
    MaterialEditText mEmail;

    @BindView(R.id.edt_cpf)
    MaterialEditText mCpf;

    String nome;

    public String config;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);
        ButterKnife.bind(this);
        Usuario usuario = CustomApplication.currentUser;

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Verificando suas credenciais");

        MaskEditTextChangedListener maskCel = new MaskEditTextChangedListener("(##) #####-####", mCelular);
        MaskEditTextChangedListener maskNasc = new MaskEditTextChangedListener("##/##/####", mNascimento);

        mCelular.addTextChangedListener(maskCel);
        mNascimento.addTextChangedListener(maskNasc);

        mNome.setText(usuario.getNome());
        mCelular.setText(usuario.getTelefone());
        mNascimento.setText(usuario.getDataNascimento());
        mCpf.setText(usuario.getCPF());
        mEmail.setText(usuario.getEmail());


        mBtn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                MainActivity.mViewPager.setCurrentItem(2);
            }
        });

        mBtn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
                final Usuario user = new Usuario();
                final String celular = mCelular.getText().toString();
                String envioTel = celular.replace("(", "");
                envioTel = envioTel.replace(")", "");
                envioTel = envioTel.replace(" ", "");
                envioTel = envioTel.replace("-", "");


                testeNome();

                if (nome.length() > 32) {
                    mNome.setError("Seu nome deve ter no máximo 32 caracteres.");
                    return;
                }

                if (!InputValidator.isValidName(nome)) {
                    mNome.setError("Nome em branco ou com formato inválido.");
                    return;
                }



                user.setNome(mNome.getText().toString());
                user.setTelefone(envioTel);
                user.setDataNascimento(mNascimento.getText().toString());
                user.setEmail(mEmail.getText().toString());
                Call<StatusResponse> call = new RetrofitInit(EditPerfilActivity.this).getUsuarioService().atualizaUser(CustomApplication.currentUser.getId(), user);
                call.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        StatusResponse res = response.body();

                        if (res != null) {
                            if (res.hasError()) {
                                Toast.makeText(EditPerfilActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            } else {

                                CustomApplication.currentUser.setNome(user.getNome());
                                CustomApplication.currentUser.setTelefone(user.getTelefone());
                                CustomApplication.currentUser.setDataNascimento(user.getDataNascimento());
                                Toast.makeText(EditPerfilActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                                onBackPressed();

                            }
                        } else {
                            Toast.makeText(EditPerfilActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        mDialog.dismiss();
                        Toast.makeText(EditPerfilActivity.this, "Falha na comunicação com o servidor. Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

        private void testeNome(){
            nome = mNome.getText().toString();
            Character char1 = nome.charAt(nome.length()-1);
            String str1 =  " ";
            Character char2 = str1.charAt(0);
            if(char1.equals(char2)){
                nome = nome.substring(0, nome.length()-1);
                mNome.setText(nome);
                testeNome();
            }
            return;
        }
    }

