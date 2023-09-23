package com.cafeteria.rest;

import com.cafeteria.core.cf.ControllerUsuario;
import com.cafeteria.modelo.Usuario;
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("acceso")
public class RESTUsuario extends Application {
    
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response logear( @FormParam("datos") @DefaultValue("") String datos)
    {
        String out = null;
        ControllerUsuario ce = null;
        Gson gson = null;
        Usuario us = null;
        String user = "";
        String pass = "";
        
        try {
             
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(datos);         
            
            user = (String) json.get("user");
            pass = (String) json.get("pass");          
            
            ce = new ControllerUsuario();          
            gson = new Gson();
            
            us = ce.getUsuario(user, pass);
            
            if(us.getId() != 0) {
                
                String token = ce.codificar(ce.crearToken());
                
                us.setLastToken(token);
                
                ce.guardarToken(us);
                
                out = new Gson().toJson(us);
            } else {
                out = """
                    {"acceso":"%s"}
                  """;
                
                out = String.format(out, "Acceso Invalido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                    {"exception":"%s"}
                  """;
            out = String.format(out, e);
        }
        
        return  Response.status(Response.Status.OK).entity(out).build();
    }
    
    
    @Path("cerrar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response deslogear( @FormParam("user") @DefaultValue("") String user)
    {
        String out = null;
        
        ControllerUsuario ce = null;
        Gson gson = new Gson();
       
        Usuario us = gson.fromJson(user, Usuario.class);
        
        boolean res = false;
        
        try {
            ce = new ControllerUsuario();          
            
            res = ce.eliminarToken(us.getLastToken());
            
            if(res) {
                out = """
                        {"res": "%s"}
                      """;
                out = String.format(out, res);
            } else {
                out = """
                        {"error": "%s"}
                      """;
                out = String.format(out, "Error al cerrar sesión");
            }
           
            
            
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                    {"exception":"%s"}
                  """;
            out = String.format(out, e);
        }
        
        return  Response.status(Response.Status.OK).entity(out).build();
    }
}
