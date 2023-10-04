package com.cafeteria.modelo;

public class Usuario {
    
    private int id;
    private String usuario;
    private String contrasenia;
    private String nombre;
    private String lastToken;
    private String dateLastToken;

    public Usuario() {
    }

    public Usuario(String user, String password, String nombre, String lastToken, String dateLastToken) {
        this.usuario = user;
        this.contrasenia = password;
        this.nombre = nombre;
        this.lastToken = lastToken;
        this.dateLastToken = dateLastToken;
    }

    public Usuario(int id, String user, String password, String nombre, String lastToken, String dateLastToken) {
        this.id = id;
        this.usuario = user;
        this.contrasenia = password;
        this.nombre = nombre;
        this.lastToken = lastToken;
        this.dateLastToken = dateLastToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return usuario;
    }

    public void setUser(String user) {
        this.usuario = user;
    }

    public String getPassword() {
        return contrasenia;
    }

    public void setPassword(String password) {
        this.contrasenia = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLastToken() {
        return lastToken;
    }

    public void setLastToken(String lastToken) {
        this.lastToken = lastToken;
    }

    public String getDateLastToken() {
        return dateLastToken;
    }

    public void setDateLastToken(String dateLastToken) {
        this.dateLastToken = dateLastToken;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{");
        sb.append("id=").append(id);
        sb.append(", user=").append(usuario);
        sb.append(", password=").append(contrasenia);
        sb.append(", nombre=").append(nombre);
        sb.append(", lastToken=").append(lastToken);
        sb.append(", dateLastToken=").append(dateLastToken);
        sb.append('}');
        return sb.toString();
    }

}
