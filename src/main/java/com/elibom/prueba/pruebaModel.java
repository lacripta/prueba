/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elibom.prueba;

import datos.Server;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USERPC
 */
public class pruebaModel {

    public Map<String, Object> ListarServidores() {
        Map<String, Object> salida = new HashMap();
        Server server = new Server();
        List<Server> servidores = server.findAll();
        if (servidores.isEmpty()) {
            salida.put("mensaje", "NO SE HAN ENCONTRADO DATOS");
            salida.put("respuesta", "No existen servidores registrados actualmente");
            salida.put("data", servidores);
            salida.put("cantidad", servidores.size());
            salida.put("estado", 0);
        } else {
            salida.put("mensaje", "SE HA ENCONTRADO LOS SERVIDORES");
            salida.put("respuesta", "Lista de los servidores registrados");
            salida.put("data", servidores);
            salida.put("cantidad", servidores.size());
            salida.put("estado", 1);
        }

        return salida;

    }

    public Map<String, Object> AgregarServidores() {
        Map<String, Object> salida = new HashMap();
        salida.put("mensaje", "OK");
        salida.put("respuesta", "OK");
        salida.put("data", "");
        salida.put("cantidad", "1");
        salida.put("estado", 1);
        return salida;

    }

    public Map<String, Object> EditarServidores(Server edita) {
        Map<String, Object> salida = new HashMap();
        Integer editados = edita.editServer(edita);
        if (editados > 0) {
            salida.put("mensaje", "CAMBIOS REALIZADOS CORRECTAMENTE");
            salida.put("respuesta", "Se cambio la información del servidor: " + edita.getName());
            salida.put("data", "-");
            salida.put("cantidad", editados);
            salida.put("estado", 1);
        } else {
            salida.put("mensaje", "NO SE HAN REALIZADO CAMBIOS");
            salida.put("respuesta", "no se hicieron cambios en el servidor: " + edita.getName());
            salida.put("data", "-");
            salida.put("cantidad", editados);
            salida.put("estado", 1);
        }
        return salida;

    }

    public Map<String, Object> BorrarServidores() {
        Map<String, Object> salida = new HashMap();
        String sql = "";
        salida.put("mensaje", "OK");
        salida.put("respuesta", "OK");
        salida.put("data", "");
        salida.put("cantidad", "1");
        salida.put("estado", 1);
        return salida;

    }
}
