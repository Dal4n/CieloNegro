package com.cafeteria.rest;

import com.cafeteria.core.cf.ControllerVenta;
import com.cafeteria.modelo.DetalleVenta;
import com.cafeteria.modelo.Venta;
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 *
 * @author Diego Guadalupe Alonso Landín
 */

@Path("save")
public class RESTVenta extends Application {
    
    @POST
    @Path("venta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarVenta(@FormParam("datos") @DefaultValue("") String datos){
        
        String out = null;
        Gson gson = null;
        DetalleVenta dv = null;
        ControllerVenta crtV = null;
 
        
        try {
            
            gson = new Gson();
            
            dv = gson.fromJson(datos, DetalleVenta.class);
            
            crtV = new ControllerVenta();
            
            boolean res = crtV.transaccionarVenta(dv);
            
            if(res){
                out = """
                        {"respuesta":"%s"}
                      """;
                
                out = String.format(out,"Venta realizada con éxito");
            } else {
                out = """
                        {"error":"%s"}
                      """;
                
                out = String.format(out,"Error al efectuar la venta");
            }
           
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return  Response.status(Response.Status.OK).entity(out).build();
    }
    
}
