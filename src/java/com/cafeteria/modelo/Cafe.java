package com.cafeteria.modelo;

public class Cafe {
    private int id;
    private String nombre;
    private String tam;
    private double precio;
    private Promocion promo;

    public Cafe() {
    }

    public Cafe(String nombre, String tam, double precio, Promocion promo) {
        this.nombre = nombre;
        this.tam = tam;
        this.precio = precio;
        this.promo = promo;
    }

    public Cafe(int id, String nombre, String tam, double precio, Promocion promo) {
        this.id = id;
        this.nombre = nombre;
        this.tam = tam;
        this.precio = precio;
        this.promo = promo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Promocion getPromo() {
        return promo;
    }

    public void setPromo(Promocion promo) {
        this.promo = promo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cafe{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", tam=").append(tam);
        sb.append(", precio=").append(precio);
        sb.append(", promo=").append(promo);
        sb.append('}');
        return sb.toString();
    }
}
