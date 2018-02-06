package br.com.consultai.dto;

/**
 * Created by renan.boni on 19/01/2018.
 */

public class RegisterResponse {

    private String message;
    private boolean error;

    public RegisterResponse(){}

    public RegisterResponse(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
