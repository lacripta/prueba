function ServidoresRest($http, $q, Base64) {
    return {
        listar: listar,
        agregar: agregar,
        editar: editar,
        borrar: borrar
    };
    function listar() {
        var defered = $q.defer();
        var promise = defered.promise;
        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode('admin' + ':' + 'abc12345');
        $http({
            cache: false,
            method: 'GET',
            url: '/prueba/server'
        }).success(function (data) {
            defered.resolve(data);
        }).error(function (err) {
            defered.reject(err);
        });
        return promise;
    }
    function agregar(data) {
        var defered = $q.defer();
        var promise = defered.promise;
        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode('admin' + ':' + 'abc12345');
        $http({
            cache: true,
            method: 'POST',
            url: '/prueba/server',
            data: $.param({nuevo: JSON.stringify(data)}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            defered.resolve(data);
        }).error(function (err) {
            defered.reject(err);
        });
        return promise;
    }
    function editar(data) {
        var defered = $q.defer();
        var promise = defered.promise;
        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode('admin' + ':' + 'abc12345');
        $http({
            cache: true,
            method: 'PUT',
            url: '/prueba/server/' + data.id,
            data: $.param({editar: JSON.stringify(data)}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            defered.resolve(data);
        }).error(function (err) {
            defered.reject(err);
        });
        return promise;
    }
    function borrar(id) {
        var defered = $q.defer();
        var promise = defered.promise;
        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode('admin' + ':' + 'abc12345');
        $http({
            cache: true,
            method: 'DELETE',
            url: '/prueba/server/' + id
        }).success(function (data) {
            defered.resolve(data);
        }).error(function (err) {
            defered.reject(err);
        });
        return promise;
    }
}
function QuitarAcentos() {
    var in_chrs = 'àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ',
            out_chrs = 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY',
            chars_rgx = new RegExp('[' + in_chrs + ']', 'g'),
            transl = {}, i,
            lookup = function (m) {
                return transl[m] || m;
            };
    for (i = 0; i < in_chrs.length; i++) {
        transl[ in_chrs[i] ] = out_chrs[i];
    }

    return function (s) {
        return s.replace(chars_rgx, lookup);
    };
}
function Promesa($q) {
    return function (datos) {
        var defered = $q.defer();
        var promise = defered.promise;
        if (datos) {
            defered.resolve(datos);
        } else {
            defered.resolve({});
        }
        return promise;
    };
}
angular.module('prueba')
        .service('QuitarAcentos', QuitarAcentos)
        .service('Promesa', Promesa)
        .service('ServidoresRest', ServidoresRest);