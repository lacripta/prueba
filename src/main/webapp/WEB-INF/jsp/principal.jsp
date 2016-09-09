<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style>
            md-input-container .md-errors-spacer {
                min-height: 0 !important;
            }
        </style>
        <%@include file="html/header.jsp" %>
    </head>
    <body class="" ng-app="prueba">
        <div ng-controller="MainController as mn">
            <div class="col-xs-8 col-xs-push-2">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="">Ingreso de Convenio de Facturación</h3>
                    </div>
                    <div class="panel-body">
                        <form name="datosEmpresa" class="">
                            <fieldset>
                                <select-datos titulo="Nit." valor="mn.convenio.NITEMP" col="12" required="true" datos="empresas" label="NOMEMP" value="NITEMP"></select-datos>
                                <input-number titulo="Número de Convenio" valor="mn.convenio.NUMCON" col="12" required="true"></input-number>
                                <input-text titulo="Representante" valor="mn.convenio.REPEMP" col="12" required="true"></input-text>
                                <input-text titulo="Dirección" valor="mn.convenio.OBJETO" col="12" required="true"></input-text>
                                <input-date titulo="Fecha de Inicio" valor="mn.convenio.FECINI" col="12" required="true"></input-date>
                                <input-date titulo="Fecha de Terminación" valor="mn.convenio.FECFIN" col="12" required="true"></input-date>
                            </fieldset>
                            <div class="col-xs-12 text-center">
                                <button class="btn btn-raised btn-info" ng-click="mn.guardar(datosEmpresa.$valid)">guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="html/footer.jsp" %>
        <script>
            function MainController($scope, $http, Notificar, ServidoresRest) {
                var vm = this;
                ServidoresRest.listar().then(function (json) {
                    Notificar.ajax(json);
                    $scope.sevidores = json.data;
                }, function (err) {
                    Notificar.error();
                });
            }
            angular.module('prueba', [
                'oitozero.ngSweetAlert',
                'ngSanitize',
                'ui.bootstrap',
                'ngAnimate'
            ]).controller('MainController', MainController);
        </script>
        <script src="site/directives/directives.js"></script>
        <script src="site/services/services.js"></script>
        <script src="site/factories/factories.js"></script>
    </body>
</html>