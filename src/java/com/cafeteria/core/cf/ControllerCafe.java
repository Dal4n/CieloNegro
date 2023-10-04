package com.cafeteria.core.cf;

import com.cafeteria.core.db.ConexionMySQL;
import com.cafeteria.modelo.Cafe;
import com.cafeteria.modelo.Promocion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerCafe {
    
    public List<Cafe> getAll() {      
        try {
            
            //La consulta SQL a ejecutar:
            String sql = "SELECT * FROM vista_cafe";
            
            //Con este objeto nos vamos a conectar a la Base de Datos:
            ConexionMySQL connMySQL = new ConexionMySQL();
            
            //Abrimos la conexión con la Base de Datos:
            Connection conn = connMySQL.open();
            
            //Con este objeto ejecutaremos la consulta:
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            //Aquí guardaremos los resultados de la consulta:
            ResultSet rs = pstmt.executeQuery();
            
            List<Cafe> cafes = new ArrayList<>();
        
            while (rs.next()) {
                
                Cafe c = new Cafe();
                Promocion pr = new Promocion();
                
                c.setId(rs.getInt("idCafe"));
                c.setNombre(rs.getString("nombreCafe"));
                c.setTam(rs.getString("tamCafe"));
                c.setPrecio(rs.getDouble("precio"));
                
                pr.setId(rs.getInt("idPromocion"));
                pr.setTam(rs.getString("tamPromo"));
                pr.setCantGalletas(rs.getInt("cantGalletas"));
                
                c.setPromo(pr);
                
                cafes.add(c);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            connMySQL.close();
            
            return cafes;
            
        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;       
    }
    
}
