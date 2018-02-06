package br.com.consultai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.consultai.model.Usuario;

/**
 * Created by renan.boni on 22/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private boolean error;
    private String message;
    private String token;

    private Usuario usuario;

    public AuthResponse(){}

    public AuthResponse(boolean error, String message, Usuario usuario) {
        this.error = error;
        this.message = message;
        this.usuario = usuario;
    }

    public boolean hasError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
