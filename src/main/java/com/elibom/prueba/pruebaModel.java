/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elibom.prueba;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USERPC
 */
public class pruebaModel {

    public Map<String, Object> ListarServidores() {
        Map<String, Object> salida = new HashMap();
        String sql = "";
        salida.put("mensaje", "OK");
        salida.put("respuesta", "OK");
        salida.put("data", "");
        salida.put("cantidad", "1");
        salida.put("estado", 1);
        return salida;

    }

    public Map<String, Object> AgregarServidores() {
        Map<String, Object> salida = new HashMap();
        String sql = "";
        salida.put("mensaje", "OK");
        salida.put("respuesta", "OK");
        salida.put("data", "");
        salida.put("cantidad", "1");
        salida.put("estado", 1);
        return salida;

    }

    public Map<String, Object> EditarServidores() {
        Map<String, Object> salida = new HashMap();
        String sql = "";
        salida.put("mensaje", "OK");
        salida.put("respuesta", "OK");
        salida.put("data", "");
        salida.put("cantidad", "1");
        salida.put("estado", 1);
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
