package br.com.consultai.services.retrofit;

import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.BilheteUnico;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by renan.boni on 31/01/2018.
 */

public interface BilheteService {

    @PUT("user/{id_usuario}/bilhete")
    Call<StatusResponse> update(@Path("id_usuario") String userID,@Body BilheteUnico bilheteUnico);

    @POST("user/{id_usuario}/bilhete")
    Call<StatusResponse> post(@Path("id_usuario") String userID,@Body BilheteUnico bilheteUnico);

    @POST("bilhete")
    Call<StatusResponse> post(@Body BilheteUnico bilheteUnico);

    @PATCH("users/{user_id}/bilhetes/{bilhete_id}")
    Call<StatusResponse> atualizarBilhete(@Path("user_id") String userID, @Path("bilhete_id") String bilheteID,
                                          @Body BilheteUnico bilheteUnico);

}
