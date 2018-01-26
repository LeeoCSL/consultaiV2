package br.com.consultaiv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan.boni on 26/01/2018.
 */

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data{

    @JsonProperty("id_rotina_ida")
    private int idRotinaIda;

    @JsonProperty("id_rotina_volta")
    private int idRotinaVolta;

    public Data(){}

    public Data(int idRotinaIda, int idRotinaVolta) {
        this.idRotinaIda = idRotinaIda;
        this.idRotinaVolta = idRotinaVolta;
    }

    public int getIdRotinaIda() {
        return idRotinaIda;
    }

    public void setIdRotinaIda(int idRotinaIda) {
        this.idRotinaIda = idRotinaIda;
    }

    public int getIdRotinaVolta() {
        return idRotinaVolta;
    }

    public void setIdRotinaVolta(int idRotinaVolta) {
        this.idRotinaVolta = idRotinaVolta;
    }

    @Override
    public String toString() {
        return "Data{" +
                "idRotinaIda=" + idRotinaIda +
                ", idRotinaVolta=" + idRotinaVolta +
                '}';
    }
}
