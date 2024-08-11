package com.leonardo.ars.Model;

public class Notificacao {
    private String usuarioId;
    private String text;
    private String obraId;
    private boolean postado;

    public Notificacao() {
    }

    public Notificacao(String usuarioId, String text, String obraId, boolean postado) {
        this.usuarioId = usuarioId;
        this.text = text;
        this.obraId = obraId;
        this.postado = postado;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getObraId() {
        return obraId;
    }

    public void setObraId(String obraId) {
        this.obraId = obraId;
    }

    public boolean getPostado() {
        return postado;
    }

    public void setPostado(boolean post) {
        postado = post;
    }
}
