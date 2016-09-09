/*
 * PROGRAMA PROPIEDAD DE LA EMPRESA DE ENERGIA DE ARAUCA ENELAR E.S.P.
 * Este codigo fuente es de uso exclusivo de la empresa de energia de arauca
 */
package com.elibom.prueba;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.view.Viewable;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ENELAR E.S.P.
 */
@Stateless
@Path("")
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
    @Path("{tipo}")
    public Response Vistas(
            @PathParam("tipo") String tipo,
            @Context HttpServletRequest request
    ) {
        switch (tipo) {
            case "principal":
                return Response.ok(new Viewable("/principal")).build();
            default:
                return Response.status(204).build();
        }
    }

    @GET
    @Path("servidores/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListarServidores(
            @Context HttpServletRequest request) {
        return Response.ok().entity(
                gson.toJson(model.ListarServidores())
        ).build();
    }

    @POST
    @Path("servidores/agregar")
    public Response AgergarServidores(
            @Context HttpServletRequest request) {
        return Response.ok().entity(
                gson.toJson(model.AgregarServidores())
        ).build();
    }

    @PUT
    @Path("servidores/editar/{tipo}")
    public Response ActualizarServidores(
            @PathParam("tipo") String tipo,
            @Context HttpServletRequest request) {
        return Response.ok().entity(
                gson.toJson(model.EditarServidores())
        ).build();
    }

    @DELETE
    @Path("servidores/borrar/{tipo}")
    public Response BorrarServidores(
            @PathParam("tipo") String tipo,
            @Context HttpServletRequest request) {
        return Response.ok().entity(
                gson.toJson(model.BorrarServidores())
        ).build();

    }
}
