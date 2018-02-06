package br.com.consultai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by renan.boni on 22/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponse {

    private boolean error;
    private String message;

    private Data data = new Data();

    public StatusResponse(){}

    public StatusResponse(boolean error, String message, Data data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public boolean hasError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
