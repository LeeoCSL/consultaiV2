package br.com.consultai.dto;

import br.com.consultai.model.Usuario;

/**
 * Created by renan on 17/03/2018.
 */

public class AuthFacebookResponse {

    private String message;
    private String token;

    private boolean collapse;
    private boolean error;

    private Usuario usuario;

    public AuthFacebookResponse(){}

    public AuthFacebookResponse(String message, String token, boolean collapse, boolean error, Usuario usuario) {
        this.message = message;
        this.token = token;
        this.collapse = collapse;
        this.error = error;
        this.usuario = usuario;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthFacebookResponse{" +
                "message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", collapse=" + collapse +
                ", error=" + error +
                ", usuario=" + usuario +
                '}';
    }
}
