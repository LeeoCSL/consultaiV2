package br.com.consultaiv2.services.retrofit;

import br.com.consultaiv2.dto.AuthResponse;
import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by renan.boni on 19/01/2018.
 */

public interface UsuarioService {

    @POST("register")
    Call<StatusResponse> register(@Body Usuario usuario);

    @POST("auth")
    Call<AuthResponse> auth(@Body Usuario usuario);

    @GET("cartao/[numero]")
    Call<AuthResponse> cartao(@Path("numero")String numero );
}