package br.com.consultaiv2.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import br.com.consultaiv2.application.CustomApplication;
import br.com.consultaiv2.eventbus.events.UpdateUserSaldoEventt;

/**
 * Created by renan.boni on 29/01/2018.
 */

public class ConsultaiMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> mensagem = remoteMessage.getData();

        if(mensagem.containsKey("novo_saldo")){
            String novoSaldo = mensagem.get("novo_saldo");

            Log.i("MSGRECEBIDA", novoSaldo);
            CustomApplication.currentUser.getBilheteUnico().setSaldo(Double.valueOf(novoSaldo));

            EventBus eventBus = EventBus.getDefault();
            eventBus.post(new UpdateUserSaldoEventt());
        }
    }
}
