package com.elibom.prueba;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.view.Viewable;
import datos.Server;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
public class pruebaController {

    pruebaModel model;
    Gson gson;

    public pruebaController() {

        model = new pruebaModel();
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    @GET
    @Path("/{tipo}")
    public Response Vistas(
            @PathParam("tipo") String tipo,
            @Context HttpServletRequest request
    ) {
        switch (tipo) {
            case "principal":
                return Response.ok(new Viewable("/principal")).build();
            case "server_details":
                return Response.ok(new Viewable("/server_modal")).build();
            case "server_new":
                return Response.ok(new Viewable("/server_modal_crear")).build();
            default:
                return Response.status(204).build();
        }
    }

    @GET
    @Path("/server")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListarServidores(
            @Context HttpServletRequest request) {
        if (VerificaPeticion(request.getHeader("authorization"))) {
            return Response.ok().entity(gson.toJson(AccesoDenegado())).header("content-type", MediaType.APPLICATION_JSON).build();
        }
        return Response.ok().entity(
                gson.toJson(model.ListarServidores()
                )).build();
    }

    @POST
    @Path("/server")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response AgergarServidores(
            @FormParam("nuevo") String nuevo,
            @Context HttpServletRequest request) {
        if (VerificaPeticion(request.getHeader("authorization"))) {
            return Response.ok().entity(gson.toJson(AccesoDenegado())).header("content-type", MediaType.APPLICATION_JSON).build();
        }
        Server server = gson.fromJson(nuevo, Server.class);
        return Response.ok().entity(
                gson.toJson(model.AgregarServidores(server)
                )).build();
    }

    @PUT
    @Path("/server/{id: [0-9]+}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ActualizarServidores(
            @FormParam("editar") String editar,
            @Context HttpServletRequest request) {
        if (VerificaPeticion(request.getHeader("authorization"))) {
            return Response.ok().entity(gson.toJson(AccesoDenegado())).header("content-type", MediaType.APPLICATION_JSON).build();
        }
        Server server = gson.fromJson(editar, Server.class);
        return Response.ok().entity(
                gson.toJson(model.EditarServidores(server)
                )).build();
    }

    @DELETE
    @Path("/server/{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response BorrarServidores(
            @PathParam("id") Integer id,
            @Context HttpServletRequest request) {
        if (VerificaPeticion(request.getHeader("authorization"))) {
            return Response.ok().entity(gson.toJson(AccesoDenegado())).header("content-type", MediaType.APPLICATION_JSON).build();
        }
        return Response.ok().entity(
                gson.toJson(model.BorrarServidores(id)
                )).build();

    }

    public boolean VerificaPeticion(String auth) {
        return !auth.equals("Basic YWRtaW46YWJjMTIzNDU=");
    }

    public Map<String, Object> AccesoDenegado() {
        Map<String, Object> salida = new HashMap();
        salida.put("mensaje", "ACCESO DENEGADO");
        salida.put("respuesta", "No tiene suficientes privilegios para acceder a este contenido");
        salida.put("data", null);
        salida.put("cantidad", "0");
        salida.put("estado", "0");
        return salida;
    }
}
