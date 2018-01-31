package br.com.consultaiv2.services.retrofit;

import java.util.List;

import br.com.consultaiv2.dto.RotinaResponse;
import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.model.Rotina;
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
    Call<StatusResponse> rotina(@Path("user_id") String userID, @Body Rotina rotina);

    @DELETE("user/{user_id}/rotina")
    Call<StatusResponse> delete(@Path("user_id") String userID);
}
