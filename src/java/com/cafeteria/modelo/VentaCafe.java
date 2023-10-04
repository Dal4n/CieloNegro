package com.cafeteria.modelo;

public class VentaCafe {
    
    private Cafe cafe;
    private int cantidad;
    private double precioUnitario;

    public VentaCafe() {
    }

    public VentaCafe(Cafe cafe, int cantidad, double precioUnitario) {
        this.cafe = cafe;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VentaCafe{");
        sb.append("cafe=").append(cafe);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", precioUnitario=").append(precioUnitario);
        sb.append('}');
        return sb.toString();
    }       
    
}
