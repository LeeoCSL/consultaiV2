package br.com.consultai.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.InputValidator;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmaCadActivity extends AppCompatActivity {

    @BindView(R.id.et_nome)
    EditText mNome;

    @BindView(R.id.et_email)
    EditText mEmail;

    @BindView(R.id.sp_sexo)
    Spinner mSexo;

    @BindView(R.id.btn_continuar)
    Button mConfirma;

    String sex;

    String nome;

    String email = "";
    String senha = "";

    private Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_cad);

        ButterKnife.bind(this);

        mNome.setText(LoginActivity.nameGoogle);
        mEmail.setText(LoginActivity.emailGoogle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Sexo", "Masculino", "Feminino"}){

            @Override
            public boolean isEnabled(int position){

                if(position == 0){

                    // Disabilita a primeira posição (hint)
                    return false;

                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if(position == 0){

                    // Deixa o hint com a cor cinza ( efeito de desabilitado)
                    tv.setTextColor(Color.GRAY);

                }else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };


        mSexo.setAdapter(adapter);

        mSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    sex = mSexo.getSelectedItem().toString().substring(0,1).toUpperCase();
                    // Ação realizada quando um elemento diferente
                    // do hint é selecionado
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDataFromInput();
            }
        });
    }

    private void testeNome(String nome){
        Character char1 = nome.charAt(nome.length()-1);
        String str1 =  " ";
        Character char2 = str1.charAt(0);
        if(char1.equals(char2)){
            nome = nome.substring(0, nome.length()-1);
            mNome.setText(nome);
            testeNome(nome);
        }
        return;
    }

    private void validateDataFromInput(){
        nome = mNome.getText().toString();
        final String sexo = sex;
        email = mEmail.getText().toString();




        if(nome.length() > 32){
            mNome.setError("Seu nome deve ter no máximo 32 caracteres.");
            return;
        }

        if(!InputValidator.isValidName(nome)){
            mNome.setError("Nome em branco ou com formato inválido.");
            return;
        }

        testeNome(nome);

        if(mSexo.getSelectedItem().equals("Sexo")){
            TextView errorText = (TextView) mSexo.getSelectedView();
            errorText.setError("Por Favor, selecione o sexo.");
            errorText.setTextColor(Color.RED);
            errorText.setText("Por Favor, selecione o sexo.");
            return;
        }

        if(!InputValidator.isEmailValid(email)){
            mEmail.setError("Email no formato inválido.");
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
        usuario.setSexo(sexo);
        usuario.setEmail(email);
        usuario.setVersao_app(String.valueOf(R.string.versao));

//        Call<StatusResponse> call = new RetrofitInit(this).getUsuarioService().register(usuario);
//        call.enqueue(new Callback<StatusResponse>() {
//            @Override
//            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
//                StatusResponse res = response.body();
//
//                if(res!=null){
//                    if (res.hasError()) {
//                        Toast.makeText(RegisterActivity.this, "Desculpe, o seguinte erro ocorreu: " + res.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        mDialog.show();
//
////                    CustomApplication customApplication = (CustomApplication) getApplicationContext();
////
////                    Usuario u = StatusResponse.getUsuario();
////
////                    CustomApplication.currentUser = u;
////                    customApplication.setAPItoken(StatusResponse.getToken());
//
//
//                        HashMap<String, String> userData = new HashMap<>();
//                        userData.put("email", email);
//                        userData.put("senha", senha);
//
////                    LoginActivity.mEmail.setText(email);
////                    LoginActivity.mSenha.setText(senha);
//
//                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                        intent.putExtra("user_data", userData);
//                        mDialog.dismiss();
//                        startActivity(intent);
//                        finish();
//
//
//                    }
//                }else{
//                    Toast.makeText(RegisterActivity.this, "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StatusResponse> call, Throwable t) {
//                Toast.makeText(RegisterActivity.this, "Falha na comunicação com o servidor. Erro: " +t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        Toast.makeText(ConfirmaCadActivity.this, "YaY", Toast.LENGTH_SHORT).show();


    }


}
