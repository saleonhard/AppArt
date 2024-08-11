package com.leonardo.ars.Model;

public class Comentario {

    private String id;
    private String comentario;
    private String usuario;

    public Comentario() {
    }

    public Comentario(String id, String comentario, String usuario) {
        this.id = id;
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String Comentario) {
        this.comentario = Comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
