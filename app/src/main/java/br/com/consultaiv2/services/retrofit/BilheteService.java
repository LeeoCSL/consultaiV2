package br.com.consultaiv2.services.retrofit;

import br.com.consultaiv2.dto.StatusResponse;
import br.com.consultaiv2.model.BilheteUnico;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by renan.boni on 31/01/2018.
 */

public interface BilheteService {

    @PUT("user/{user_id}/bilhete")
    Call<StatusResponse> update(@Path("user_id") String userID,@Body BilheteUnico bilheteUnico);

}
