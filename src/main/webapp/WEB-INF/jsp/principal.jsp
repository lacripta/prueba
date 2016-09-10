<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style>
            .glyphicon{
                font-size: large;
            }
            table{
                width: 100%;
            }
        </style>
        <%@include file="html/header.jsp" %>
    </head>
    <body class="" ng-app="prueba">
        <div ng-controller="MainController as mn">
            <div class="col-xs-8 col-xs-push-2">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="">Gestión de Servidores registrados</h3>
                    </div>
                    <div class="panel-body">
                        <div class="col-xs-12">
                            <button class="btn btn-raised btn-info" ng-click="mn.nuevoElemento()">
                                nuevo servidor
                            </button>
                        </div>
                        <div class="col-sm-12">
                            <table datatable="" dt-options="mn.dtOptions" dt-columns="mn.dtColumns" dt-instance="mn.dtInstance"
                                   class="table table-bordered table-condensed table-hover table-responsive table-striped">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="html/footer.jsp" %>
        <script>
            function MainController($timeout, $compile, $scope, $uibModal, DTOptionsBuilder, DTColumnBuilder, Notificar, ServidoresRest, SweetAlert) {
                var vm = this;
                ServidoresRest.listar().then(function (json) {
                    Notificar.ajax(json);
                    $scope.sevidores = json.data;
                }, function (err) {
                    Notificar.error();
                });
                $scope.selected = {};
                $scope.estados = [
                    {estado: 'INACTIVO', state: 'idle'},
                    {estado: 'DETENIDO', state: 'stopped'},
                    {estado: 'EN EJECUCIÓN', state: 'running'}
                ];
                vm.dtOptions = DTOptionsBuilder.fromFnPromise(ServidoresRest.listar())
                        .withOption('processing', true)
                        .withOption('order', [])
                        .withDataProp('data')
                        .withLanguageSource('/prueba/site/js/dtSpanish.json')
                        .withDisplayLength(15)
                        .withPaginationType('full_numbers')
                        .withOption('fnRowCallback',
                                function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                                    $compile(nRow)($scope);
                                })
                        .withSelect({
                            style: 'os',
                            selector: 'td'
                        });
                vm.dtColumns = [
                    DTColumnBuilder.newColumn('name').withTitle('Nombre'),
                    DTColumnBuilder.newColumn('state').withTitle('Estado'),
                    DTColumnBuilder.newColumn('id').withTitle('').renderWith(function (data, type, full, meta) {
                        return "<a class='text-info text-uppercase' ng-click='mn.verElemento(" + meta.row + ")'><b>ver</b></a>"
                                + "<a class='text-danger text-uppercase' ng-click='mn.borrarElemento(" + data + ")'><b><i class='pull-right glyphicon glyphicon-remove'></i></b></a>";
                    })
                ];
                vm.dtInstance = {};
                vm.verElemento = verElemento;
                vm.borrarElemento = borrarElemento;
                vm.nuevoElemento = nuevoElemento;
                function nuevoElemento() {
                    $scope.selected = {};
                    $scope.selected.accion = 'nuevo';
                    var modalInstance = $uibModal.open({
                        templateUrl: "/prueba/server_new",
                        controller: ServerModalController,
                        scope: $scope,
                        size: 'sm',
                        windowClass: "animated fadeIn"
                    });
                }
                function verElemento(id) {
                    $scope.selected = vm.dtInstance.DataTable.row(id).data();
                    $scope.selected.accion = 'ver';
                    var modalInstance = $uibModal.open({
                        templateUrl: "/prueba/server_details",
                        controller: ServerModalController,
                        scope: $scope,
                        size: 'sm',
                        windowClass: "animated fadeIn"
                    });
                }
                function borrarElemento(id) {
                    SweetAlert.swal({
                        title: "SE BORRARA EL ELEMENTO SELECCIONADO",
                        text: "Seguro de borrar este servidor?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: true,
                        closeOnCancel: true
                    }, function (isConfirm) {
                        if (isConfirm) {
                            ServidoresRest.borrar(id).then(function (json) {
                                $timeout(function () {
                                    Notificar.ajax(json);
                                    vm.dtInstance.changeData(ServidoresRest.listar());
                                }, 500);
                            }, function (err) {
                                console.log(err);
                                Notificar.error();
                            });
                        } else {
                            Notificar.cancelado();
                        }
                    });
                }
                function ServerModalController($scope, $uibModalInstance, Notificar) {
                    $scope.ok = function (ok) {
                        if (ok) {
                            switch ($scope.selected.accion) {
                                case "ver":
                                    ServidoresRest.editar($scope.selected).then(function (json) {
                                        Notificar.ajax(json);
                                        $scope.selected = {};
                                    }, function (err) {
                                        console.log(err);
                                        Notificar.error();
                                    });
                                    break;
                                case "nuevo":
                                    ServidoresRest.agregar($scope.selected).then(function (json) {
                                        Notificar.ajax(json);
                                        $scope.selected = {};
                                    }, function (err) {
                                        console.log(err);
                                        Notificar.error();
                                    });
                                    break;
                            }
                            $timeout(function () {
                                vm.dtInstance.changeData(ServidoresRest.listar());
                            }, 500);
                            $uibModalInstance.close();
                        } else {
                            Notificar.form();
                        }
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                }
            }
            angular.module('prueba', [
                'datatables',
                'datatables.select',
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