package com.leonardo.ars.Model;

public class Obra {


    private String nome;
    private String descricao;
    private String imagemUrl;
    private String obraId;
    private String artista;
    private String valor;
    private Boolean leilao;
    private Boolean vendido;

    public Obra() {
    }

    public Obra(String nome,String descricao, String imagemUrl, String obraId, String artista,String valor,Boolean leilao, Boolean vendido ) {

        this.nome = nome;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.obraId = obraId;
        this.artista = artista;
        this.valor = valor;
        this.leilao = leilao;
        this.vendido = vendido;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getObraId() {
        return obraId;
    }

    public void setObraId(String obraid) {
        this.obraId = obraid;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getLeilao() {
        return leilao;
    }

    public void setLeilao(Boolean leilao) {
        this.leilao = leilao;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

}
