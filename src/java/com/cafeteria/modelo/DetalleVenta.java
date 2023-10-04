package com.cafeteria.modelo;

import java.util.List;

public class DetalleVenta {
    
    private Venta venta;
    private List<VentaCafe> vc;

    public DetalleVenta(Venta venta, List<VentaCafe> vc) {
        this.venta = venta;
        this.vc = vc;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<VentaCafe> getVc() {
        return vc;
    }

    public void setVc(List<VentaCafe> vc) {
        this.vc = vc;
    }

    @Override
    public String toString() {
        
        String mensaje = "";
        
        for (VentaCafe ventaCafe : vc) {
            mensaje += "\n" + ventaCafe ;
        }
                          
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleVenta{");
        sb.append("venta=").append(venta);
        sb.append(", vc=").append(mensaje);
        sb.append('}');
        
        return sb.toString();
    }

   
}
