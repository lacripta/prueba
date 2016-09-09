function Notificar(SweetAlert) {
    var alerta = {};

    alerta.ok = function () {
        SweetAlert.swal("TAREA REALIZADA CORRECTAMENTE", "Se completo satisfactoriamente la tarea solicitada", "success");
    };
    alerta.ajax = function (data) {
        SweetAlert.swal(data.mensaje, data.respuesta, data.estado == "1" ? "success" : "error");
    };
    alerta.form = function () {
        SweetAlert.swal("EL FORMULARIO NO ESTA COMPLETO", "Debe llenar todos los campos del formulario", "error");
    };
    alerta.limpiar = function () {
        SweetAlert.swal("SE HAN LIMPIADO LOS CAMPOS", "Se acaban de borrar todos los datos del formulario", "warning");
    };
    alerta.error = function () {
        SweetAlert.swal("NO SE PUEDE REALIZAR LA TAREA", "El componenete solicitado ha arrojado un error", "error");
    };
    alerta.seleccionar = function () {
        SweetAlert.swal({
            title: "No puede Realizar la accion Solicitada",
            text: "Debe marcar uno de los elementos de la tabla como seleccionado",
            type: "warning",
            showCancelButton: false,
            confirmButtonText: "Aceptar",
            closeOnConfirm: true,
            closeOnCancel: true
        });
    };
    alerta.incompleto = function () {
        SweetAlert.swal({
            title: "No puede Realizar la accion Solicitada",
            text: "Debe indicar todos los campos para ver el reporte",
            type: "warning",
            showCancelButton: false,
            confirmButtonText: "Aceptar",
            closeOnConfirm: true,
            closeOnCancel: true
        });
    };
    alerta.cancelado = function () {
        SweetAlert.swal("Operación Cancelada", "La tarea ha sido cancelada por el usuario", "warning");
    };
    alerta.mensaje = function (texto, estado) {
        SweetAlert.swal("Respuesta del Servidor", texto, estado == "1" ? "success" : "error");
    };

    return alerta;
}

angular.module('prueba')
        .factory('Notificar', Notificar);