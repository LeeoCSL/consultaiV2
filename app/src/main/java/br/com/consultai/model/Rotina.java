package br.com.consultai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan.boni on 24/01/2018.
 */
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rotina {

    @JsonProperty("id")
    private String id;
//
//    @JsonProperty("id_volta")
//    private Integer voltaID;

    @JsonProperty("flag_ida")
    private boolean flagIda;

    @JsonProperty("flag_volta")
    private boolean flagVolta;

//    @JsonProperty("tipo_ida")
//    private int tipoIda;
//
//    @JsonProperty("tipo_volta")
//    private int tipoVolta;

    @JsonProperty("hora_ida")
    private String horaIda;

    @JsonProperty("hora_volta")
    private String horaVolta;

    @JsonProperty("valor_ida")
    private double valorIda;

    @JsonProperty("valor_volta")
    private double valorVolta;

//    @JsonProperty("dia_uso")
//    private DiasUso diasUso = new DiasUso();

    @JsonProperty("domingo")
    private boolean domingo;

    @JsonProperty("segunda")
    private boolean segunda;

    @JsonProperty("terca")
    private boolean terca;

    @JsonProperty("quarta")
    private boolean quarta;

    @JsonProperty("quinta")
    private boolean quinta;

    @JsonProperty("sexta")
    private boolean sexta;

    @JsonProperty("sabado")
    private boolean sabado;



    public Rotina() {}

    public Rotina(String id, boolean flagIda, boolean flagVolta, int tipoIda, int tipoVolta, String horaIda, String horaVolta, DiasUso diasUso, boolean domingo,boolean segunda,boolean terca,boolean quarta,boolean quinta,boolean sexta,boolean sabado) {
//        this.idaID = idaID;
//        this.voltaID = voltaID;
        this.id = id;
        this.flagIda = flagIda;
        this.flagVolta = flagVolta;
//        this.tipoIda = tipoIda;
//        this.tipoVolta = tipoVolta;
        this.horaIda = horaIda;
        this.horaVolta = horaVolta;
//        this.diasUso = diasUso;
        this.domingo = domingo;
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//
//    public Integer getVoltaID() {
//        return voltaID;
//    }
//
//    public void setVoltaID(Integer voltaID) {
//        this.voltaID = voltaID;
//    }

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

//    public int getTipoIda() {
//        return tipoIda;
//    }
//
//    public void setTipoIda(int tipoIda) {
//        this.tipoIda = tipoIda;
//    }
//
//    public int getTipoVolta() {
//        return tipoVolta;
//    }
//
//    public void setTipoVolta(int tipoVolta) {
//        this.tipoVolta = tipoVolta;
//    }

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

//    public DiasUso getDiasUso() {
//        return diasUso;
////    }
//
//    public void setDiasUso(DiasUso diasUso) {
//        this.diasUso = diasUso;
//    }

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

    public boolean getDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean getSegunda() {
        return segunda;
    }

    public void setSegunda(boolean segunda) {
        this.segunda = segunda;
    }

    public boolean getTerca() {
        return terca;
    }

    public void setTerca(boolean terca) {
        this.terca = terca;
    }

    public boolean getQuarta() {
        return quarta;
    }

    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean getQuinta() {
        return quinta;
    }

    public void setQuinta(boolean quinta) {
        this.quinta = quinta;
    }

    public boolean getSexta() {
        return sexta;
    }

    public void setSexta(boolean sexta) {
        this.sexta = sexta;
    }

    public boolean getSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    @Override
    public String toString() {
        return "Rotina{" +
                "id=" + id +
//                ", voltaID=" + voltaID +
                ", flagIda=" + flagIda +
                ", flagVolta=" + flagVolta +
//                ", tipoIda=" + tipoIda +
//                ", tipoVolta=" + tipoVolta +
                ", horaIda='" + horaIda + '\'' +
                ", horaVolta='" + horaVolta + '\'' +
                ", domingo='" + domingo + '\'' +
                ", segunda='" + segunda + '\'' +
                ", terca='" + terca + '\'' +
                ", quarta='" + quarta + '\'' +
                ", quinta='" + quinta + '\'' +
                ", sexta='" + sexta + '\'' +
                ", sabado='" + sabado + '\'' +
//                ", diasUso=" + diasUso +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Rotina rotina = (Rotina) o;
//
//        return idaID.equals(rotina.idaID);
//    }
//
//    @Override
//    public int hashCode() {
//        return idaID.hashCode();
//    }
}
