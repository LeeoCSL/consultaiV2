package br.com.consultai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonardo.ribeiro on 23/01/2018.
 */

public class Credencial {

    @SerializedName("checksum")
    private String checksum;

    @SerializedName("gcmID")
    private String gcmID;

    @SerializedName("idSistemaOperacional")
    private String idSistemaOperacional;

    @SerializedName("modeloComercial")
    private String modeloComercial;

    @SerializedName("modeloDispositivo")
    private String modeloDispositivo;

    @SerializedName("nomeOperadora")
    private String nomeOperadora;

    @SerializedName("serialTerminal")
    private String serialTerminal;

    @SerializedName("simCardSerialNumber")
    private String simCardSerialNumber;

    @SerializedName("versaoAPP")
    private String versaoAPP;

    @SerializedName("versaoDLL")
    private String versaoDLL;

    @SerializedName("versaoOS")
    private String versaoOS;

    @SerializedName("email")
    private String email;

    @SerializedName("idAplicacao")
    private String idAplicacao;


    public Credencial(){}

    public Credencial(String checksum, String gcmID, String idSistemaOperacional, String modeloComercial,
                      String modeloDispositivo, String nomeOperadora, String serialTerminal, String simCardSerialNumber,
                      String versaoAPP, String versaoDLL, String versaoOS, String email, String idAplicacao) {
        this.checksum = checksum;
        this.gcmID = gcmID;
        this.idSistemaOperacional = idSistemaOperacional;
        this.modeloComercial = modeloComercial;
        this.modeloDispositivo = modeloDispositivo;
        this.nomeOperadora = nomeOperadora;
        this.serialTerminal = serialTerminal;
        this.simCardSerialNumber = simCardSerialNumber;
        this.versaoAPP = versaoAPP;
        this.versaoDLL = versaoDLL;
        this.versaoOS = versaoOS;
        this.email = email;
        this.idAplicacao = idAplicacao;
    }


    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getGcmID() {
        return gcmID;
    }

    public void setGcmID(String gcmID) {
        this.gcmID = gcmID;
    }

    public String getIdSistemaOperacional() {
        return idSistemaOperacional;
    }

    public void setIdSistemaOperacional(String idSistemaOperacional) {
        this.idSistemaOperacional =idSistemaOperacional ;
    }

    public String getModeloComercial() {
        return modeloComercial;
    }

    public void setModeloComercial(String modeloComercial ) {
        this.modeloComercial =modeloComercial ;
    }

    public String getModeloDispositivo() {
        return modeloDispositivo;
    }

    public void setModeloDispositivo(String modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo ;
    }

    public String getNomeOperadora() {
        return nomeOperadora;
    }

    public void setNomeOperadora(String nomeOperadora) {
        this.nomeOperadora =nomeOperadora ;
    }

    public String getSerialTerminal() {
        return serialTerminal;
    }

    public void setSerialTerminal(String serialTerminal) {
        this.serialTerminal = serialTerminal;
    }

    public String getSimCardSerialNumber() {
        return simCardSerialNumber;
    }

    public void setSimCardSerialNumber(String simCardSerialNumber) {
        this.simCardSerialNumber =simCardSerialNumber ;
    }

    public String getVersaoAPP() {
        return versaoAPP;
    }

    public void setVersaoAPP(String versaoAPP) {
        this.versaoAPP =versaoAPP ;
    }

    public String getVersaoDLL() {
        return versaoDLL;
    }

    public void setVersaoDLL(String versaoDLL) {
        this.versaoDLL =versaoDLL ;
    }

    public String getVersaoOS() {
        return versaoOS;
    }

    public void setVersaoOS(String versaoOS) {
        this.versaoOS =versaoOS ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email =email ;
    }

    public String getIdAplicacao() {
        return idAplicacao;
    }

    public void setIdAplicacao(String idAplicacao) {
        this.idAplicacao = idAplicacao ;
    }

    @Override
    public String toString() {
        return "Credencial{" +
                "checksum='" + checksum + '\'' +
                ", gcmID='" + gcmID + '\'' +
                ", idSistemaOperacional='" + idSistemaOperacional + '\'' +
                ", modeloComercial='" + modeloComercial + '\'' +
                ", modeloDispositivo=" + modeloDispositivo + '\'' +
                ", nomeOperadora=" + nomeOperadora + '\'' +
                ", serialTerminal=" + serialTerminal + '\'' +
                ", simCardSerialNumber=" + simCardSerialNumber + '\'' +
                ", versaoAPP=" + versaoAPP + '\'' +
                ", versaoDLL=" + versaoDLL + '\'' +
                ", versaoOS=" + versaoOS + '\'' +
                ", email=" + email + '\'' +
                ", idAplicacao=" + idAplicacao + '\'' +
                '}';
    }

}
