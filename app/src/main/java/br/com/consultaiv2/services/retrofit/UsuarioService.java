package br.com.consultaiv2.services.retrofit;

import br.com.consultaiv2.dto.AuthResponse;
import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by renan.boni on 19/01/2018.
 */

public interface UsuarioService {

    @POST("register")
    Call<StatusResponse> register(@Body Usuario usuario);

    @POST("auth")
    Call<AuthResponse> auth(@Body Usuario usuario);

    @PUT("user")
    Call<StatusResponse> update(@Body Usuario usuario);
}
