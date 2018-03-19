package br.com.consultai.services.retrofit;

import br.com.consultai.dto.StatusResponse;
import br.com.consultai.dto.StatusRotinaResponse;
import br.com.consultai.model.Rotina;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by renan.boni on 24/01/2018.
 */

public interface RotinaService {

    @PUT("user/{user_id}/rotina")
    Call<StatusRotinaResponse> rotina(@Path("user_id") String userID, @Body Rotina rotina);

    @DELETE("user/{user_id}/rotina")
    Call<StatusResponse> delete(@Path("user_id") String userID);


}
