package com.cafeteria.modelo;

import java.util.List;

public class Venta {
    
    private int id;
    private Usuario usuario;
    private String fecha;
    private int galletas;
    private int paquetesG;
    private double total;

    public Venta() {
    }

    public Venta(Usuario usuario, String fecha, int galletas, int paquetesG, double total) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.galletas = galletas;
        this.paquetesG = paquetesG;
        this.total = total;
    }

    public Venta(int id, Usuario usuario, String fecha, int galletas, int paquetesG, double total) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.galletas = galletas;
        this.paquetesG = paquetesG;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getGalletas() {
        return galletas;
    }

    public void setGalletas(int galletas) {
        this.galletas = galletas;
    }

    public int getPaquetesG() {
        return paquetesG;
    }

    public void setPaquetesG(int paquetesG) {
        this.paquetesG = paquetesG;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venta{");
        sb.append("id=").append(id);
        sb.append(", usuario=").append(usuario);
        sb.append(", fecha=").append(fecha);
        sb.append(", galletas=").append(galletas);
        sb.append(", paquetesG=").append(paquetesG);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
