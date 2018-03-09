package br.com.consultai.services.retrofit;

import br.com.consultai.dto.AuthResponse;
import br.com.consultai.dto.CadCompResponse;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("user/{user_id}/cadastro")
    Call<CadCompResponse> getCad(@Path("user_id") String userID);

    @PUT("user/{user_id}/completar")
    Call<StatusResponse> put(@Path("user_id") String userID, @Body Usuario usuario);

}
