package com.leonardo.ars.Model;

public class Usuario {

    private String nome;
    private String email;
    private String username;
    private String bio;
    private String imagemUrl;
    private String id;

    public Usuario() {
    }

    public Usuario(String nome, String email, String username, String bio, String imagemUrl, String id) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.imagemUrl = imagemUrl;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
