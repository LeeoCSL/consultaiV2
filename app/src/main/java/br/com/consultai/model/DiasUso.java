package br.com.consultai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by renan.boni on 24/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiasUso {

    private boolean domingo;
    private boolean segunda;
    private boolean terca;
    private boolean quarta;
    private boolean quinta;
    private boolean sexta;
    private boolean sabado;

    public DiasUso(){

    }

    public DiasUso(boolean domingo, boolean segunda, boolean terca, boolean quarta, boolean quinta, boolean sexta, boolean sabado) {
        this.domingo = domingo;
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;
    }

    public void setDiasUso(boolean[] uso){
        domingo = uso[0];
        segunda = uso[1];
        terca = uso[2];
        quarta = uso[3];
        quinta = uso[4];
        sexta = uso[5];
        sabado = uso[6];
    }

    @JsonIgnore
    public boolean[] getDiasUso(){
        return new boolean[]{domingo, segunda, terca, quarta, quinta, sexta, sabado};
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean isSegunda() {
        return segunda;
    }

    public void setSegunda(boolean segunda) {
        this.segunda = segunda;
    }

    public boolean isTerca() {
        return terca;
    }

    public void setTerca(boolean terca) {
        this.terca = terca;
    }

    public boolean isQuarta() {
        return quarta;
    }

    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean isQuinta() {
        return quinta;
    }

    public void setQuinta(boolean quinta) {
        this.quinta = quinta;
    }

    public boolean isSexta() {
        return sexta;
    }

    public void setSexta(boolean sexta) {
        this.sexta = sexta;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    @Override
    public String toString() {
        return "DiasUso{" +
                "domingo=" + domingo +
                ", segunda=" + segunda +
                ", terca=" + terca +
                ", quarta=" + quarta +
                ", quinta=" + quinta +
                ", sexta=" + sexta +
                ", sabado=" + sabado +
                '}';
    }
}
