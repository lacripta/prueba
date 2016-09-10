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
            data: JSON.stringify(data),
            headers: {'Content-Type': 'application/json'}
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
            data: JSON.stringify(data),
            headers: {'Content-Type': 'application/json'}
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
angular.module('prueba')
        .service('ServidoresRest', ServidoresRest);