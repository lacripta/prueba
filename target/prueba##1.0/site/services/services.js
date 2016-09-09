function ServidoresRest($http, $q) {
    return {
        listar: listar,
        agregar: agregar,
        editar: editar,
        borrar: borrar
    };
    function listar() {
        var defered = $q.defer();
        var promise = defered.promise;
        $http({
            cache: true,
            method: 'GET',
            url: '/servidores/listar',
            //data: $.param({numord: $scope.BUSCARORDEN}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
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
            url: '/servidores/agregar',
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
        $http({
            cache: true,
            method: 'PUT',
            url: '/servidores/editar/' + data.id,
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
            url: '/servidores/borrar/' + data.id,
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