package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import br.com.consultai.R;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.DiasUso;
import br.com.consultai.model.Rotina;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
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

    @BindView(R.id.tv_time_ida)
    TextView mTimeIda;

    @BindView(R.id.tv_time_volta)
    TextView mTimeVolta;

    @BindView(R.id.tp_1)
    ImageView mRelogioIda;

    @BindView(R.id.tp_2)
    ImageView mRelogioVolta;

    @BindView(R.id.rb_onibus_trilho_ida)
    RadioButton mOnibusIda;

    @BindView(R.id.rb_integracao_ida)
    RadioButton mIntegracaoIda;

    @BindView(R.id.rb_onibus_trilho_volta)
    RadioButton mOnibusVolta;

    @BindView(R.id.rb_integracao_volta)
    RadioButton mIntegracaoVolta;

    private CustomToggleButton[] mWeekDays = new CustomToggleButton[7];

    private Rotina rotina = new Rotina();

    private String horaIDA;
    private String horaVOLTA;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cartao);

        ButterKnife.bind(this);

        initButtons();
        loadDataFromUser();

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Verificando suas credenciais");

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
                        rotina.setValorVolta(4.00);
                    }else{
                        rotina.setValorVolta(6.96);
                    }
                }else{
                    if(CustomApplication.currentUser.getBilheteUnico().isEstudante()){
                        rotina.setValorVolta(2.00);
                    }else{
                        rotina.setValorVolta(4.00);
                    }
                }
            }
        });
    }



    private void loadDataFromUser(){
        Usuario usuario = CustomApplication.currentUser;

        if(usuario.getRotinas().size() > 0){
            List<Rotina> rotina = usuario.getRotinas();

            Rotina ida = rotina.get(0);
            Rotina volta = rotina.get(1);

            this.rotina = new Rotina();
            this.rotina.setIdaID(ida.getIdaID());
            this.rotina.setVoltaID(volta.getVoltaID());
            this.rotina.setHoraIda(ida.getHoraIda());
            this.rotina.setHoraVolta(volta.getHoraVolta());
            this.rotina.setValorIda(ida.getValorIda());
            this.rotina.setValorVolta(volta.getValorVolta());
            this.rotina.setFlagIda(ida.isFlagIda());
            this.rotina.setFlagVolta(volta.isFlagVolta());
            this.rotina.setTipoIda(ida.getTipoIda());
            this.rotina.setTipoVolta(volta.getTipoVolta());
            this.rotina.setDiasUso(ida.getDiasUso());

            this.horaIDA = ida.getHoraIda();
            this.horaVOLTA = volta.getHoraVolta();

            DiasUso uso = rotina.get(0).getDiasUso();

            for(int i = 0; i < uso.getDiasUso().length; i++){
                if(uso.getDiasUso()[i]){
                    mWeekDays[i].setChecked(true);
                }else{
                    mWeekDays[i].setChecked(false);
                }
            }

            if(this.rotina.getHoraIda() != null){
                Log.i("HORAIDA", this.rotina.getHoraIda());
                mTimeIda.setText(this.rotina.getHoraIda());
                mTimeIda.setVisibility(View.VISIBLE);
                horaIDA = this.rotina.getHoraIda();
            }else{
                mTimeIda.setVisibility(View.GONE);
            }

            if(this.rotina.getHoraVolta() != null){
                mTimeVolta.setText(this.rotina.getHoraVolta());
                mTimeVolta.setVisibility(View.VISIBLE);
                horaVOLTA = this.rotina.getHoraVolta();
            }else{
                mTimeVolta.setVisibility(View.GONE);
            }

            if(this.rotina.getValorIda() == 4.0 || this.rotina.getValorIda() == 2.0){
                mOnibusIda.setChecked(true);
                mIntegracaoIda.setChecked(false);
            }else{
                mOnibusIda.setChecked(false);
                mIntegracaoIda.setChecked(true);
            }

            if(this.rotina.getValorVolta() == 4.0 || this.rotina.getValorVolta() == 2.0){
                mOnibusVolta.setChecked(true);
                mIntegracaoVolta.setChecked(false);
            }else {
                mOnibusVolta.setChecked(false);
                mIntegracaoVolta.setChecked(true);
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

        if(horaIDA == null || horaIDA.isEmpty()){
            Toast.makeText(this, "Você deve escolher um horário de ida.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(horaVOLTA == null || horaVOLTA.isEmpty()){
            Toast.makeText(this, "Você deve escolher um horário de volta.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean[] checked = new boolean[mWeekDays.length];

        int sum = 0;
        for(int i = 0; i < mWeekDays.length; i++){
            checked[i] = mWeekDays[i].isChecked();

            if(checked[i]){
                sum++;
            }
        }
        
        if(!mOnibusIda.isChecked() && !mIntegracaoIda.isChecked()){
            Toast.makeText(this, "Você deve escolher ônibus ou integração na ida.", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if(!mOnibusVolta.isChecked() && !mIntegracaoVolta.isChecked()){
            Toast.makeText(this, "Você deve escolher ônibus ou integração na volta.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(sum == 0){
            Toast.makeText(this, "Você deve escolher pelo menos um dia na sua rotina.", Toast.LENGTH_SHORT).show();
            return;
        }

        rotina.setFlagIda(true);
        rotina.setFlagVolta(false);

        rotina.setTipoIda(0);
        rotina.setTipoVolta(1);

        rotina.setHoraIda(horaIDA);
        rotina.setHoraVolta(horaVOLTA);

        rotina.getDiasUso().setDiasUso(checked);

        mDialog.show();

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
                mDialog.dismiss();
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

    private void showTimeDialog(final int routine){
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog time = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if(routine == 0){
                    horaIDA = h + ":" + m + ":00";
                    mTimeIda.setText(DateUtil.stringToTime(horaIDA));
                    mTimeIda.setVisibility(View.VISIBLE);
                }else{
                    horaVOLTA = h + ":" + m + ":00";
                    mTimeVolta.setText(DateUtil.stringToTime(horaVOLTA));
                    mTimeVolta.setVisibility(View.VISIBLE);
                }
            }
        }, hour, minute,true);

        time.show();
    }
}
