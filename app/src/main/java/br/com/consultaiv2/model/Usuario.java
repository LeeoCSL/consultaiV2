package br.com.consultaiv2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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

    @JsonProperty("notification_token")
    private String notificationToken;

    @JsonProperty("data_criacao")
    private String dataCriacao;

    @JsonProperty("bilhete_unico")
    private BilheteUnico bilheteUnico;

    private List<Rotina> rotinas = new ArrayList<>();

    public Usuario() {}

    public Usuario(String id, String nome, String sobrenome, String dataNascimento, String sexo, String CPF, String telefone, String email, String senha, String dataCriacao, BilheteUnico bilheteUnico, List<Rotina> rotinas) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.CPF = CPF;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.bilheteUnico = bilheteUnico;
        this.rotinas = rotinas;
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

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BilheteUnico getBilheteUnico() {
        return bilheteUnico;
    }

    public void setBilheteUnico(BilheteUnico bilheteUnico) {
        this.bilheteUnico = bilheteUnico;
    }

    public List<Rotina> getRotinas() {
        return rotinas;
    }

    public void setRotinas(List<Rotina> rotinas) {
        this.rotinas = rotinas;
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
                ", dataCriacao='" + dataCriacao + '\'' +
                ", bilheteUnico=" + bilheteUnico +
                ", rotinas=" + rotinas +
                '}';
    }
}
