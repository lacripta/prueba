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
        $http({
            cache: true,
            method: 'POST',
            url: '/prueba/server',
            data: $.param(data),
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
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            defered.resolve(data);
        }).error(function (err) {
            defered.reject(err);
        });
        return promise;
    }
    function borrar(data) {
        var defered = $q.defer();
        var promise = defered.promise;
        $http({
            cache: true,
            method: 'DELETE',
            url: '/prueba/server/' + data.id,
            data: $.param(data),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            defered.resolve(data);
        }).error(function (err) {
            defered.reject(err);
        });
        return promise;
    }
}
angular.module('prueba')
        .service('ServidoresRest', ServidoresRest);