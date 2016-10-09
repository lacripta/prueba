/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author lacripta
 */
public class Resultado {

    String mensaje;
    String respuesta;
    Object data;
    boolean estado;
    Integer cantidad;

    public Resultado(String mensaje, String respuesta, Object data, boolean estado, Integer cantidad) {
        this.mensaje = mensaje;
        this.respuesta = respuesta;
        this.data = data;
        this.estado = estado;
        this.cantidad = cantidad;
    }

    public Resultado(Object data, boolean estado, Integer cantidad) {
        this.mensaje = "CORRECTO";
        this.respuesta = "Sin errores";
        this.data = data;
        this.estado = estado;
        this.cantidad = cantidad;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public Object getData() {
        return data;
    }

    public boolean isEstado() {
        return estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
