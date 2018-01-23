package br.com.consultaiv2.retrofit;

/**
 * Created by renan.boni on 19/01/2018.
 */

import java.io.IOException;


import br.com.consultaiv2.services.retrofit.UsuarioService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by renan on 13/01/18.
 */

public class RetrofitInit {

    private Retrofit retrofit;

    public RetrofitInit(){

        //final CustomApplication customApplication = (CustomApplication)activity.getApplicationContext();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

/*                if(customApplication.isUserLoggedIn()){
                    requestBuilder.header("Authorization", customApplication.getAPItoken());
                }*/

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        client.addInterceptor(interceptor);

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.17:3000/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public UsuarioService getUsuarioService(){
        return retrofit.create(UsuarioService.class);
    }
}
