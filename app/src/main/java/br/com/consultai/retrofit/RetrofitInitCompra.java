package br.com.consultai.retrofit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;


import br.com.consultai.application.CustomApplication;
import br.com.consultai.services.retrofit.BilheteService;
import br.com.consultai.services.retrofit.CompraService;
import br.com.consultai.services.retrofit.RotinaService;
import br.com.consultai.services.retrofit.UsuarioService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by leonardo.ribeiro on 08/02/2018.
 */

public class RetrofitInitCompra {

    private Retrofit RetrofitInitCompra;

    public RetrofitInitCompra(Context activity){

        final CustomApplication customApplication = (CustomApplication)activity.getApplicationContext();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

                Log.i("CHAMOU INTERCEPTOR", "SIM");

                if(CustomApplication.isUserLoggedIn()){
                    requestBuilder.header("Authorization", customApplication.getAPItoken());
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        client.addInterceptor(interceptor);

        this.RetrofitInitCompra = new Retrofit.Builder()
                .baseUrl("https://maquina.intranet:8080/mobile-server-integracao-web/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public CompraService getCompraService(){
        return RetrofitInitCompra.create(CompraService.class);
    }

}
