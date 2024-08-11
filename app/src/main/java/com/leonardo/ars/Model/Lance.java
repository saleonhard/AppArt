package com.leonardo.ars.Model;

public class Lance {
    private String id;
    private Float  valorLance;
    private String usuario;
    private long   timestamp;

    public Lance() {

    }

    public Lance(String id, Float valorLance, String usuario, long timestamp) {
        this.id = id;
        this.valorLance = valorLance;
        this.usuario = usuario;
        this.timestamp = timestamp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getValorLance() {
        return valorLance;
    }

    public void setValorLance(Float valorLance) {
        this.valorLance = valorLance;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
