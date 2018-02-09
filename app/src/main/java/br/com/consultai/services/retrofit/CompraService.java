package br.com.consultai.services.retrofit;

import br.com.consultai.dto.StatusResponse;
import br.com.consultai.model.Compra;
import br.com.consultai.model.ConsultaFormaPagamento;
import br.com.consultai.model.ConsultaProduto;
import br.com.consultai.model.Credencial;
import br.com.consultai.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by leonardo.ribeiro on 08/02/2018.
 */

public interface CompraService {

    @POST("inicializar")
    Call<StatusResponse> inicializar(@Body Credencial credencial);

    @POST("consultarProduto")
    Call<StatusResponse> consultarProduto(@Body ConsultaProduto consultaProduto);

    @POST("consultarFormaPagamento")
    Call<StatusResponse> consultarFormaPagamento(@Body ConsultaFormaPagamento consultaFormaPagamento);

    @POST("pedido")
    Call<StatusResponse> pedido(@Body Compra compra);
}
