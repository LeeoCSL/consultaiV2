package br.com.consultai.firebase;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.consultai.application.CustomApplication;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.retrofit.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by renan.boni on 29/01/2018.
 */

public class ConsultaiInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.i("mandouagora", refreshedToken);

        if (refreshedToken != null) {
            CustomApplication.currentUser.setNotificationToken(refreshedToken);
        }

        Call<StatusResponse> call = new RetrofitInit(getBaseContext()).getUsuarioService().update(CustomApplication.currentUser);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse res = response.body();

                if (res.hasError()) {
                    Toast.makeText(ConsultaiInstanceIDService.this, "Erro: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(ConsultaiInstanceIDService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
