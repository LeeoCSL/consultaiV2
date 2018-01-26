package br.com.consultaiv2.activities;

import android.app.TimePickerDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.consultaiv2.R;
import br.com.consultaiv2.application.CustomApplication;
import br.com.consultaiv2.dto.Data;
import br.com.consultaiv2.dto.RotinaResponse;
import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.model.DiasUso;
import br.com.consultaiv2.model.Rotina;
import br.com.consultaiv2.model.Usuario;
import br.com.consultaiv2.retrofit.RetrofitInit;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarCartaoActivity extends AppCompatActivity {

    @BindView(R.id.btn_domingo)
    CustomToggleButton mDomingo;

    @BindView(R.id.btn_segunda)
    CustomToggleButton mSegunda;

    @BindView(R.id.btn_terca)
    CustomToggleButton mTerca;

    @BindView(R.id.btn_quarta)
    CustomToggleButton mQuarta;

    @BindView(R.id.btn_quinta)
    CustomToggleButton mQuinta;

    @BindView(R.id.btn_sexta)
    CustomToggleButton mSexta;

    @BindView(R.id.btn_sabado)
    CustomToggleButton mSabado;

    @BindView(R.id.rgroup_ida)
    RadioGroup mGroupIda;

    @BindView(R.id.rgroup_volta)
    RadioGroup mGroupVolta;

    private CustomToggleButton[] mWeekDays = new CustomToggleButton[7];

    private Rotina rotina = new Rotina();

    private String horaIDA;
    private String horaVOLTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cartao);

        ButterKnife.bind(this);

        initButtons();
        loadDataFromUser();

        mGroupIda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rb_integracao_ida){
                    if(CustomApplication.currentUser.getBilheteUnico().isEstudante()){
                        rotina.setValorIda(4.00);
                    }else{
                        rotina.setValorIda(6.96);
                    }
                }else{
                    if(CustomApplication.currentUser.getBilheteUnico().isEstudante()){
                        rotina.setValorIda(2.00);
                    }else{
                        rotina.setValorIda(4.00);
                    }
                }
            }
        });

        mGroupVolta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rb_integracao_volta){
                    if(CustomApplication.currentUser.getBilheteUnico().isEstudante()){
                        rotina.setValorIda(4.00);
                    }else{
                        rotina.setValorIda(6.96);
                    }
                }else{
                    if(CustomApplication.currentUser.getBilheteUnico().isEstudante()){
                        rotina.setValorIda(2.00);
                    }else{
                        rotina.setValorIda(4.00);
                    }
                }
            }
        });
    }

    private void loadDataFromUser(){
        Usuario usuario = CustomApplication.currentUser;

        if(usuario.getRotinas().size() > 0){
            List<Rotina> rotina = usuario.getRotinas();

            this.rotina = rotina.get(0);

            DiasUso uso = rotina.get(0).getDiasUso();

            for(int i = 0; i < uso.getDiasUso().length; i++){
                if(uso.getDiasUso()[i]){
                    mWeekDays[i].setChecked(true);
                }else{
                    mWeekDays[i].setChecked(false);
                }
            }
        }
    }

    private void initButtons(){
        mWeekDays[0] = mDomingo;
        mWeekDays[1] = mSegunda;
        mWeekDays[2] = mTerca;
        mWeekDays[3] = mQuarta;
        mWeekDays[4] = mQuinta;
        mWeekDays[5] = mSexta;
        mWeekDays[6] = mSabado;
    }

    public void handlerBackToMainActivity(View v){
        finish();
    }

    public void handlerNewRoutine(View v){
        boolean[] checked = new boolean[mWeekDays.length];

        for(int i = 0; i < mWeekDays.length; i++){
            checked[i] = mWeekDays[i].isChecked();
        }

        rotina.setFlagIda(true);
        rotina.setFlagVolta(false);

        rotina.setTipoIda(0);
        rotina.setTipoVolta(1);

        rotina.setHoraIda(horaIDA);
        rotina.setHoraVolta(horaVOLTA);

        rotina.getDiasUso().setDiasUso(checked);

        Call<StatusResponse> call = new RetrofitInit(this).getRotinaService().rotina(CustomApplication.currentUser.getId(), rotina);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                StatusResponse res = response.body();

                if(rotina.getIdaID() == null && rotina.getVoltaID() == null){
                    rotina.setIdaID(res.getData().getIdRotinaIda());
                    rotina.setVoltaID(res.getData().getIdRotinaVolta());
                }

                CustomApplication.updateRoutine(rotina);

                Toast.makeText(EditarCartaoActivity.this, "Suas rotinas foram salvas.", Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(EditarCartaoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void handlerSetTimeRoutineIda(View view){
        showTimeDialog(0);
    }

    public void handlerSetTimeRoutineVolta(View view){
        showTimeDialog(1);
    }

    public void handlerValorIda(View v){

    }

    public void handlerValorVolta(View v){

    }

    private void checkForStudent(){
        if(CustomApplication.currentUser.getBilheteUnico().isEstudante()){

        }
    }

    private void showTimeDialog(final int routine){
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog time = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if(routine == 0){
                    Toast.makeText(EditarCartaoActivity.this, h + ":" + m + ":00", Toast.LENGTH_SHORT).show();
                    horaIDA = h + ":" + m + ":00";
                }else{
                    Toast.makeText(EditarCartaoActivity.this, h + ":" + m + ":00", Toast.LENGTH_SHORT).show();

                    horaVOLTA = h + ":" + m + ":00";
                }
            }
        }, hour, minute,true);

        time.show();
    }
}
