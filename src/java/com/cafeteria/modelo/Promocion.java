package com.cafeteria.modelo;

/**
 *
 * @author Diego Guadalupe Alonso Landín
 */
public class Promocion {
    
    private int id;
    private String tam;
    private int cantGalletas;

    public Promocion() {
    }

    public Promocion(String tam, int cantGalletas) {
        this.tam = tam;
        this.cantGalletas = cantGalletas;
    }

    public Promocion(int id, String tam, int cantGalletas) {
        this.id = id;
        this.tam = tam;
        this.cantGalletas = cantGalletas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public int getCantGalletas() {
        return cantGalletas;
    }

    public void setCantGalletas(int cantGalletas) {
        this.cantGalletas = cantGalletas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Promocion{");
        sb.append("id=").append(id);
        sb.append(", tam=").append(tam);
        sb.append(", cantGalletas=").append(cantGalletas);
        sb.append('}');
        return sb.toString();
    }
    
}
