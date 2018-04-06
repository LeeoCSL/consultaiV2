package br.com.consultai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan on 18/03/2018.
 */

public class StatusRotinaResponse {

    private boolean error;
    private String message;

    @JsonProperty("id")
    private String id;



    public StatusRotinaResponse(){}

    public StatusRotinaResponse(boolean error, String message, String id) {
        this.error = error;
        this.message = message;
        this.id = id;

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

    public String getId() {
        return id;
    }

    public void setRotinaIdaID(String id) {
            this.id = id;
    }



    @Override
    public String toString() {
        return "StatusRotinaResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", id=" + id +
                '}';
    }
}
