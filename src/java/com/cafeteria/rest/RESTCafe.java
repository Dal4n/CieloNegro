package com.cafeteria.rest;

import com.cafeteria.core.cf.ControllerCafe;
import com.cafeteria.modelo.Cafe;
import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("cafes")
public class RESTCafe extends Application {
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response getAll()
    {
        String out = null;
        ControllerCafe ccf = null;
        List<Cafe> cafes = null;
        
        try {
            
            ccf = new ControllerCafe();
            cafes = ccf.getAll();
           
            out = new Gson().toJson(cafes);
            
        } catch (Exception e) {
        }
        
        return  Response.status(Response.Status.OK).entity(out).build();
    }
}
