package br.com.consultaiv2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renan.boni on 24/01/2018.
 */
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rotina {

    @JsonProperty("id_ida")
    private Integer idaID;

    @JsonProperty("id_volta")
    private Integer voltaID;

    @JsonProperty("flag_ida")
    private boolean flagIda;

    @JsonProperty("flag_volta")
    private boolean flagVolta;

    @JsonProperty("tipo_ida")
    private int tipoIda;

    @JsonProperty("tipo_volta")
    private int tipoVolta;

    @JsonProperty("hora_ida")
    private String horaIda;

    @JsonProperty("hora_volta")
    private String horaVolta;

    @JsonProperty("valor_ida")
    private double valorIda;

    @JsonProperty("valor_volta")
    private double valorVolta;

    @JsonProperty("dia_uso")
    private DiasUso diasUso = new DiasUso();

    public Rotina() {}

    public Rotina(Integer idaID, Integer voltaID, boolean flagIda, boolean flagVolta, int tipoIda, int tipoVolta, String horaIda, String horaVolta, DiasUso diasUso) {
        this.idaID = idaID;
        this.voltaID = voltaID;
        this.flagIda = flagIda;
        this.flagVolta = flagVolta;
        this.tipoIda = tipoIda;
        this.tipoVolta = tipoVolta;
        this.horaIda = horaIda;
        this.horaVolta = horaVolta;
        this.diasUso = diasUso;
    }

    public Integer getIdaID() {
        return idaID;
    }

    public void setIdaID(Integer idaID) {
        this.idaID = idaID;
    }

    public Integer getVoltaID() {
        return voltaID;
    }

    public void setVoltaID(Integer voltaID) {
        this.voltaID = voltaID;
    }

    public boolean isFlagIda() {
        return flagIda;
    }

    public void setFlagIda(boolean flagIda) {
        this.flagIda = flagIda;
    }

    public boolean isFlagVolta() {
        return flagVolta;
    }

    public void setFlagVolta(boolean flagVolta) {
        this.flagVolta = flagVolta;
    }

    public int getTipoIda() {
        return tipoIda;
    }

    public void setTipoIda(int tipoIda) {
        this.tipoIda = tipoIda;
    }

    public int getTipoVolta() {
        return tipoVolta;
    }

    public void setTipoVolta(int tipoVolta) {
        this.tipoVolta = tipoVolta;
    }

    public String getHoraIda() {
        return horaIda;
    }

    public void setHoraIda(String horaIda) {
        this.horaIda = horaIda;
    }

    public String getHoraVolta() {
        return horaVolta;
    }

    public void setHoraVolta(String horaVolta) {
        this.horaVolta = horaVolta;
    }

    public DiasUso getDiasUso() {
        return diasUso;
    }

    public void setDiasUso(DiasUso diasUso) {
        this.diasUso = diasUso;
    }

    public double getValorIda() {
        return valorIda;
    }

    public double getValorVolta() {
        return valorVolta;
    }

    public void setValorIda(double valorIda) {
        this.valorIda = valorIda;
    }

    public void setValorVolta(double valorVolta) {
        this.valorVolta = valorVolta;
    }

    @Override
    public String toString() {
        return "Rotina{" +
                "idaID=" + idaID +
                ", voltaID=" + voltaID +
                ", flagIda=" + flagIda +
                ", flagVolta=" + flagVolta +
                ", tipoIda=" + tipoIda +
                ", tipoVolta=" + tipoVolta +
                ", horaIda='" + horaIda + '\'' +
                ", horaVolta='" + horaVolta + '\'' +
                ", diasUso=" + diasUso +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rotina rotina = (Rotina) o;

        return idaID.equals(rotina.idaID);
    }

    @Override
    public int hashCode() {
        return idaID.hashCode();
    }
}
