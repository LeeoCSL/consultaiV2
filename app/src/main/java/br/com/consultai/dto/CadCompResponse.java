package br.com.consultai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.consultai.model.Usuario;



@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CadCompResponse {

    private boolean error;
    private String message;
    private String cpf;
    private String telefone;
    private String data_nascimento;

    public CadCompResponse(){}

    public CadCompResponse(boolean error, String message, String cpf, String telefone, String data_nascimento) {
        this.error = error;
        this.message = message;
        this.cpf = cpf;
        this.telefone = telefone;
        this.data_nascimento = data_nascimento;
    }

    public boolean hasError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }


    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone=" + telefone +
                ", data_nascimento=" + data_nascimento +
                '}';
    }
}
