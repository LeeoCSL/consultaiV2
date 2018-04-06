package br.com.consultai.services.retrofit;

import java.util.HashMap;

import br.com.consultai.dto.AuthFacebookResponse;
import br.com.consultai.dto.AuthResponse;
import br.com.consultai.dto.CadCompResponse;
import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import br.com.consultai.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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

    @POST("auth/facebook")
    Call<AuthFacebookResponse> authFacebook(@Body Usuario usuario);

    @PUT("user")
    Call<StatusResponse> update(@Body Usuario usuario);

//    @GET("user/{user_id}/cadastro")
//    Call<CadCompResponse> getCad(@Path("user_id") String userID);

//    @PATCH("user/{user_id}/completar")
//    Call<StatusResponse> patchUser(@Path("user_id") String userID, @Body Usuario usuario);

//    @GET("teste/{email}")
//    Call<StatusResponse> getEmail(@Path ("email") String email);

    @PATCH("user/{user_id}")
    Call<StatusResponse> setToken(@Path("user_id") String userID, @Body HashMap map);

    @PATCH("user/{user_id}")
    Call<StatusResponse> atualizaUser(@Path("user_id") String userID, @Body Usuario usuario);

}
