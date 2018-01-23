package br.com.consultaiv2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan.boni on 19/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

    private String id;
    private String nome;
    private String sobrenome;

    @JsonProperty("data_nascimento")
    private String dataNascimento;

    private String sexo;
    private String CPF;
    private String telefone;
    private String email;
    private String senha;
    private String data_criacao;

    @JsonProperty("bilhete_unico")
    private BilheteUnico bilheteUnico;

    public Usuario() {}

    public Usuario(String id, String nome, String sobrenome, String dataNascimento, String sexo, String CPF, String telefone, String email, String senha, String data_criacao, BilheteUnico bilheteUnico) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.CPF = CPF;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.data_criacao = data_criacao;
        this.bilheteUnico = bilheteUnico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

    public BilheteUnico getBilheteUnico() {
        return bilheteUnico;
    }

    public void setBilheteUnico(BilheteUnico bilheteUnico) {
        this.bilheteUnico = bilheteUnico;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", sexo='" + sexo + '\'' +
                ", CPF='" + CPF + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", data_criacao='" + data_criacao + '\'' +
                ", bilheteUnico=" + bilheteUnico +
                '}';
    }
}
