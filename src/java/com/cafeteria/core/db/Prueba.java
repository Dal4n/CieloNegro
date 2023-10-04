package com.cafeteria.core.db;


public class Prueba {
    public static void main(String[] args) {
        
        ConexionMySQL connMySQl = new ConexionMySQL();     
        
        try {
                        
            connMySQl.open();
            System.out.println("Conexion establecida con MySQl!");
            connMySQl.close();           
            System.out.println("Conexion Cerrada correctamente con MySQL!");
           
        } catch (Exception e) {
        }
    }
}
