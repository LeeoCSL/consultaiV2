package br.com.consultai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import br.com.consultai.R;
import br.com.consultai.activities.ComprarActivity;
import br.com.consultai.activities.EditarCartaoActivity;
import br.com.consultai.activities.MainActivity;
import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.eventbus.events.UpdateUserSaldoEventt;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Credencial;
import br.com.consultai.model.Pedidos;
import br.com.consultai.model.Usuario;
import br.com.consultai.retrofit.RetrofitInit;
import br.com.consultai.util.AdapterPedidos;
import br.com.consultai.util.MonetaryUtil;
import br.com.consultai.util.Utility;
import info.hoang8f.widget.FButton;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PedidosFragment extends Fragment {

    ListView listPedidos;

    public PedidosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        listPedidos = (ListView) view.findViewById(R.id.listPedidos);

        loadUI(view);
        return view;

    }


    private void loadUI(View v) {


//
//        mProgressBar = v.findViewById(R.id.progress_bar);
//
//        mApelidoBilhete = v.findViewById(R.id.txt_nome_bilhete);
//        mEditar = v.findViewById(R.id.btn_editar);
//        mSaldoAtual = v.findViewById(R.id.tv_saldo_atual);
//        btn_limpar = v.findViewById(R.id.btn_limpar);
//        btn_comprar = v.findViewById(R.id.btn_comprar);
//
//        mExcluir = v.findViewById(R.id.btn_excluir);
//
//        mBtnRecarga = v.findViewById(R.id.btn_recarga);
//
//        mDomingo = v.findViewById(R.id.btn_domingo);
//        mSegunda = v.findViewById(R.id.btn_segunda);
//        mTerca = v.findViewById(R.id.btn_terca);
//        mQuarta = v.findViewById(R.id.btn_quarta);
//        mQuinta = v.findViewById(R.id.btn_quinta);
//        mSexta = v.findViewById(R.id.btn_sexta);
//        mSabado = v.findViewById(R.id.btn_sabado);
//
//        mWeekDays[0] = mDomingo;
//        mWeekDays[1] = mSegunda;
//        mWeekDays[2] = mTerca;
//        mWeekDays[3] = mQuarta;
//        mWeekDays[4] = mQuinta;
//        mWeekDays[5] = mSexta;
//        mWeekDays[6] = mSabado;
//
//        Log.i("token_firebase", FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshUI();

        Log.i("CURRENT USER", CustomApplication.currentUser.toString());
    }


    public void refreshUI() {
//        Usuario usuario = CustomApplication.currentUser;
//
//        if (usuario.getRotinas().size() > 0) {
//            boolean[] diasUso = usuario.getRotinas().get(0).getDiasUso().getDiasUso();
//
//            for (int i = 0; i < diasUso.length; i++) {
//                mWeekDays[i].setChecked(diasUso[i]);
//            }
//        } else {
//            for (int i = 0; i < mWeekDays.length; i++) {
//                mWeekDays[i].setChecked(false);
//            }
//        }
//
//        mApelidoBilhete.setText(usuario.getBilheteUnico().getApelido());
//        mSaldoAtual.setText(MonetaryUtil.doubleToMonetary(usuario.getBilheteUnico().getSaldo()));
    }

    private void carregaLista() {
        Pedidos pedido = new Pedidos();
        AdapterPedidos adapter = new AdapterPedidos(getContext(), pedido);
        listPedidos.setAdapter(adapter);
    }

}