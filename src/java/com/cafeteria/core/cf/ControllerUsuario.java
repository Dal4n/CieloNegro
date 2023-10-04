package com.cafeteria.core.cf;

import com.cafeteria.core.db.ConexionMySQL;
import com.cafeteria.modelo.Usuario;
import java.security.SecureRandom;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerUsuario {
    
    public Usuario getUsuario(String user, String pass) {      
        
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM usuario WHERE usuario= '" + user + "' AND contrasenia= '" + pass + "'";
        
        ConexionMySQL connMySQL = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario u = null;
        
        try {
                                  
            //Con este objeto se va conectar a la Base de Datos:
            connMySQL = new ConexionMySQL();
            
            //Se abre la conexión con la Base de Datos:
            conn = connMySQL.open();
            
            //Con este objeto ejecutaremos la consulta:
            pstmt = conn.prepareStatement(sql);
            
            //Aquí se guardará los resultados de la consulta:
            rs = pstmt.executeQuery();
            
            u = new Usuario();
            
            if (rs.next()) {
                u.setId(rs.getInt("idUsuario"));
                u.setUser(rs.getString("usuario"));
                u.setPassword(rs.getString("contrasenia"));
                u.setNombre(rs.getString("nombre"));
                u.setLastToken(rs.getString("lastToken"));
                u.setDateLastToken(rs.getString("dateLastToken"));
            }

            return u;
            
        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if (rs != null && pstmt != null && conn != null && connMySQL != null) {
                    rs.close();                    
                    pstmt.close();                   
                    conn.close();
                    connMySQL.close();
                }                                               
            } catch (SQLException ex) {
                Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;       
    }

    public void guardarToken(Usuario u) {
        String query = "UPDATE usuario SET lastToken = ?, dateLastToken = NOW() WHERE idUsuario = ?";
        ConexionMySQL connMySQL = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {                       
            //Con este objeto nos vamos a conectar a la Base de Datos:
            connMySQL = new ConexionMySQL();
            
            //Abrimos la conexión con la Base de Datos:
            conn = connMySQL.open();
            
            //Con este objeto ejecutaremos la consulta:
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, u.getLastToken());
            pstmt.setInt(2, u.getId());
            
            //Ver que valor retorna
            //Ver que valor retorna
            //Ver que valor retorna
            //Ver que valor retorna
            int rowsAffected = pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally{
            try {
                if (pstmt != null && conn != null && connMySQL != null) {
                    pstmt.close();
                    conn.close();
                    connMySQL.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean eliminarToken(String token) {
        String query = "UPDATE usuario SET lastToken = '' WHERE lastToken = '" + token + "'";
        ConexionMySQL connMySQL = null;
        Connection conn = null;
        CallableStatement cstm = null;
        
        try {
            connMySQL = new ConexionMySQL();
            
            //Abrimos la conexión con la Base de Datos:
            conn = connMySQL.open();
            
            //Con este objeto ejecutaremos la consulta:
            cstm = conn.prepareCall(query);
            
            cstm.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (cstm != null && conn != null && connMySQL != null) {
                    cstm.close();
                    conn.close();
                    connMySQL.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean validarToken(String token) {
        
        boolean r = false;
        
        if (!"".equals(token) || token != null) {
            
            String query = "SELECT * FROM usuario WHERE lastToken = ?";
            ConexionMySQL connMySQL = null;
            Connection conn = null;   
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try {
                //Con este objeto nos vamos a conectar a la Base de Datos:
                connMySQL = new ConexionMySQL();
                
                //Abrimos la conexión con la Base de Datos:
                conn = connMySQL.open();
                
                //Con este objeto ejecutaremos la consulta:
                pstmt = conn.prepareCall(query);
                
                pstmt.setString(1, token);
                
                //pstmt.executeUpdate();
                rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    r = true;
                }

                return r;
            } catch (SQLException ex) {
                Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null && pstmt != null && conn != null & connMySQL != null) {
                        rs.close();
                        pstmt.close();
                        conn.close();
                        connMySQL.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        } else {
            
            return r;
        }
        
        return false;        
    }
    
    public String crearToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        
        secureRandom.nextBytes(tokenBytes);
        
        StringBuilder tokenHex = new StringBuilder();
        for (byte b : tokenBytes) {
            tokenHex.append(String.format("%02x", b));
        }
        
        return tokenHex.toString();
    }

    // Codifica una cadena en Base64
    public String codificar(String input) {
        byte[] inputBytes = input.getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(inputBytes);
        return new String(encodedBytes);
    }
}
