function Fechas() {
    var fecha = {};
    fecha.toString = function (fecha) {
        moment.locale('es');
        return moment(fecha).format('L');
    };
    fecha.nombreArchivo = function (fecha) {
        moment.locale('es');
        return moment(fecha).format('DDMMYYYY');
    };
    fecha.toDate = function () {

    };
    return fecha;
}
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
    alerta.required = function (lista) {
        var errores = [];
        $.each(lista, function (k, v) {
            $.each(v, function (kk, vv) {
                $.each(vv.$error, function (kkk, vvv) {
                    errores.push(vvv[0].$name.replace('f_', '').split('_').join(' '));
                });
            });
        });
        SweetAlert.swal("EL FORMULARIO NO ESTA COMPLETO", "Faltan los campos: " + errores.toString(), "error");
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
function Base64() {
    var keyStr = 'ABCDEFGHIJKLMNOP' +
            'QRSTUVWXYZabcdef' +
            'ghijklmnopqrstuv' +
            'wxyz0123456789+/' +
            '=';
    return {
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }

                output = output +
                        keyStr.charAt(enc1) +
                        keyStr.charAt(enc2) +
                        keyStr.charAt(enc3) +
                        keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);

            return output;
        },
        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                alert("There were invalid base64 characters in the input text.\n" +
                        "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                        "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            do {
                enc1 = keyStr.indexOf(input.charAt(i++));
                enc2 = keyStr.indexOf(input.charAt(i++));
                enc3 = keyStr.indexOf(input.charAt(i++));
                enc4 = keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }

                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";

            } while (i < input.length);

            return output;
        }
    };
}
angular.module('prueba')
        .factory('Fechas', Fechas)
        .factory('Base64', Base64)
        .factory('Notificar', Notificar);