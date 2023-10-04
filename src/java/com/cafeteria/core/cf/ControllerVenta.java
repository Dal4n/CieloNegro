package com.cafeteria.core.cf;

import com.cafeteria.core.db.ConexionMySQL;
import com.cafeteria.modelo.DetalleVenta;
import com.cafeteria.modelo.VentaCafe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerVenta {
    
    public boolean transaccionarVenta(DetalleVenta dv){
        boolean r = false;
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmnt = null;
        ResultSet rs = null;
        
        try {
            conn.setAutoCommit(false);
            
            String query1 = "INSERT INTO venta (idUsuario, fecha, cantGalletas, cantPaquetes, total) VALUES (?, NOW(), ?, ?, ?)";
            
            pstmnt = conn.prepareStatement(query1);
            
            pstmnt.setInt(1, dv.getVenta().getUsuario().getId());
            pstmnt.setInt(2, dv.getVenta().getGalletas());
            pstmnt.setInt(3, dv.getVenta().getPaquetesG());
            pstmnt.setDouble(4, dv.getVenta().getTotal());
            
            int res = pstmnt.executeUpdate();
            
            rs = pstmnt.executeQuery("SELECT LAST_INSERT_ID();");
            
            if (rs.next()) {
                dv.getVenta().setId(rs.getInt(1));
            }
            
            System.out.println("LISTA DE PRODUCTOS: " + dv.getVc());
            
            for (VentaCafe vc : dv.getVc()) {
                String query2 = "INSERT INTO detalleventa (idVenta, idCafe, cantidad ) VALUES (?, ?, ?)";
                
                pstmnt = conn.prepareStatement(query2);
                
                pstmnt.setInt(1, dv.getVenta().getId());
                pstmnt.setInt(2, vc.getCafe().getId());
                pstmnt.setInt(3, vc.getCantidad());
                
                pstmnt.execute();
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            r = true;
            
            rs.close();
            pstmnt.close();
            conn.close();
            connMySQL.close();
                    
                    
        } catch (SQLException ex) {
            Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex);
            try {                                
                conn.rollback();
                conn.setAutoCommit(true);
                r = false;
                
                rs.close();
                pstmnt.close();
                conn.close();
                connMySQL.close();
            } catch (SQLException ex1) {
                Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex1);
                return r = false;
            }
        }
        return r;  
    }
}
