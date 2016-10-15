/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.prueba;

import datos.Resultado;
import datos.Server;
import datos.ServerDAO;

/**
 *
 * @author lacripta
 */
public class pruebaModel {

    public Resultado ListarServidores() {
        Resultado servidores = ServerDAO.findAll();
        if (servidores.isEstado()) {
            servidores.setMensaje("SE HA ENCONTRADO LOS SERVIDORES");
            servidores.setRespuesta("Lista de los servidores registrados en la base de datos");
        }
        return servidores;
    }

    public Resultado AgregarServidores(Server nuevo) {
        Resultado creados = ServerDAO.addServer(nuevo);
        if (creados.isEstado()) {
            creados.setMensaje("SERVIDOR CREADO CORRECTAMENTE");
            creados.setRespuesta("Se creo el nuevo servidor: " + nuevo.getName());
        }
        return creados;
    }

    public Resultado EditarServidores(Server edita) {
        Resultado editados = ServerDAO.editServer(edita);
        if (editados.isEstado()) {
            editados.setMensaje("CAMBIOS REALIZADOS CORRECTAMENTE");
            editados.setRespuesta("Se cambio la información del servidor: " + edita.getName());
        }
        return editados;
    }

    public Resultado BorrarServidores(Integer id) {
        Resultado editados = ServerDAO.findServerById(id);
        Server edita = (Server) editados.getData();
        editados = ServerDAO.deleteServer(id);
        if (editados.isEstado()) {
            editados.setMensaje("SERVIDOR ELIMINADO");
            editados.setRespuesta("Se borro el servidor: " + edita.getName());
        }
        return editados;
    }
}
