package br.com.consultai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan on 18/03/2018.
 */

public class StatusRotinaResponse {

    private boolean error;
    private String message;

    @JsonProperty("rotina_ida_id")
    private Integer rotinaIdaID;

    @JsonProperty("rotina_volta_id")
    private Integer rotinaVoltaID;

    public StatusRotinaResponse(){}

    public StatusRotinaResponse(boolean error, String message, Integer rotinaIdaID, Integer rotinaVoltaID) {
        this.error = error;
        this.message = message;
        this.rotinaIdaID = rotinaIdaID;
        this.rotinaVoltaID = rotinaVoltaID;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRotinaIdaID() {
        return rotinaIdaID;
    }

    public void setRotinaIdaID(Integer rotinaIdaID) {
        this.rotinaIdaID = rotinaIdaID;
    }

    public Integer getRotinaVoltaID() {
        return rotinaVoltaID;
    }

    public void setRotinaVoltaID(Integer rotinaVoltaID) {
        this.rotinaVoltaID = rotinaVoltaID;
    }

    @Override
    public String toString() {
        return "StatusRotinaResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", rotinaIdaID=" + rotinaIdaID +
                ", rotinaVoltaID=" + rotinaVoltaID +
                '}';
    }
}
