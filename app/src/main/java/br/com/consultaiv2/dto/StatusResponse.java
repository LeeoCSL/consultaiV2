package br.com.consultaiv2.dto;

/**
 * Created by renan.boni on 22/01/2018.
 */

public class StatusResponse {

    private boolean error;
    private String message;

    public StatusResponse(){}

    public StatusResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
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

    @Override
    public String toString() {
        return "StatusResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
