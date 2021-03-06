package br.com.consultai.fragments;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.Validator;

import br.com.consultai.LoginActivity;
import br.com.consultai.R;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContaFragment extends Fragment implements Validator.ValidationListener {


    @Required(order = 1, message = "Insira o apelido do cartão")
    private TextInputEditText mCartaoApelido;

    @Required(order = 1, message = "Insira o número do cartão")
    private TextInputEditText mCartaoNumero;

    private CheckBox mEstudante;

    private FButton mSalvar;

    private BilheteUnico bilheteUnico;

    private Validator validator;

    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_conta_fragment, container, false);



        mCartaoApelido = view.findViewById(R.id.et_apelido_cartao);
        mCartaoNumero = view.findViewById(R.id.et_numero_cartao);
        mEstudante = view.findViewById(R.id.cb_estudante);

        MaskEditTextChangedListener maskNum = new MaskEditTextChangedListener("#########", mCartaoNumero);
        mCartaoNumero.addTextChangedListener(maskNum);


        mSalvar = view.findViewById(R.id.btn_salvar);
        mProgressBar = view.findViewById(R.id.progress_bar);
        updateUI();
        mSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    public void updateUI(){

        Usuario usuario = CustomApplication.currentUser;

        if(usuario.getBilheteUnico() != null){
            bilheteUnico = usuario.getBilheteUnico();

            mCartaoApelido.setText(bilheteUnico.getApelido());
            mCartaoNumero.setText(bilheteUnico.getNumero());
            if(bilheteUnico.isEstudante()) {
                mEstudante.setChecked(true);
            }else{
                mEstudante.setChecked(false);
            }
        }
    }


    @Override
    public void onValidationSucceeded() {
        if(bilheteUnico.getId() != null){

            mProgressBar.setVisibility(View.VISIBLE);

            bilheteUnico.setApelido(mCartaoApelido.getText().toString());
            bilheteUnico.setNumero(mCartaoNumero.getText().toString());
            bilheteUnico.setEstudante(mEstudante.isChecked());
            bilheteUnico.setOperacao("3");
            bilheteUnico.setId_desconto(null);
//            Toast.makeText(getContext(), "est: " + mEstudante.isChecked(), Toast.LENGTH_SHORT).show();
            Call<StatusResponse> call = new RetrofitInit(getActivity()).getBilheteService().update(CustomApplication.currentUser.getId(), bilheteUnico);
            call.enqueue(new Callback<StatusResponse>() {
                @Override
                public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                    StatusResponse res = response.body();

                    if(res!=null){
                    if (res.hasError()) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        CustomApplication.currentUser.setBilheteUnico(bilheteUnico);
                        updateUI();
                        Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(getContext(), "Erro de comunicação com o servidor", Toast.LENGTH_SHORT).show();

                }
                }

                @Override
                public void onFailure(Call<StatusResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if(failedView instanceof TextInputEditText){
            TextInputEditText input = (TextInputEditText)failedView;
            input.setError(failedRule.getFailureMessage());
        }
    }
}
