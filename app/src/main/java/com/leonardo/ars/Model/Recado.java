package com.leonardo.ars.Model;

import java.util.Date;

public class Recado {

    private String id;
    private String recado;
    private String remetente;
    private String destinatario;
    private long timestamp;
    private Boolean status;


    public Recado() {
    }

    public Recado(String id, String recado, String remetente, String destinatario, long timestamp, Boolean status) {
        this.id = id;
        this.recado = recado;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecado() {
        return recado;
    }

    public void setRecado(String recado) {
        this.recado = recado;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public long getTmestamp() {
        return timestamp;
    }

    public void setData(long data) {
        this.timestamp = timestamp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
