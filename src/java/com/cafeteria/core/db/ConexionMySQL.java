package com.cafeteria.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionMySQL {
    
    Connection conn;
    
    public Connection open(){
        
        //Base de datos Local
//        String user = "root";            
//        String password = "1234";        
//        String bd = "cafeteria";
//        String url = "jdbc:mysql://127.0.0.1:3306/"+ bd + "?useSSL=false&useUnicode=true&characterEncoding=utf-8";
        
        //Base de datos Servidor Azure
        String user = "cafeteriaPrueba";            
        String password = "Landin1234";        
        String bd = "cafeteria";        
        String url = "jdbc:mysql://cafeteria.mysql.database.azure.com:3306/" + bd + "?useSSL=true";
                   
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(url, user, password);
            
            return conn;
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }
    
    public void close(){
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception controlada.");
            }
        }
        
    }
    
}
