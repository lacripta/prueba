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
    <body class="" ng-app="enelar">  
		<div ng-controller="MainController as mn">
			<h4>Titulo de la acción actual</h4>
			<input-text titulo="Hola" valor="mn.texto"></input-text>
			<input-date titulo="Prueba" valor="mn.fecha"></input-date>			
			<table datatable="" dt-options="mn.dtOptions" dt-columns="mn.dtColumns" dt-instance="mn.dtInstance" 
			       class="table table-bordered table-condensed table-hover table-responsive table-striped"></table>
		</div>
  <%@include file="html/footer.jsp" %>
    <script>
        function MainController($scope, DTOptionsBuilder, DTColumnBuilder) {
            var vm = this;
            vm.zona;
            vm.municipio;
            vm.acepta = 'N';
            vm.texto = 'viejo';
            vm.fecha = '01/07/2016';
            vm.dtOptions = DTOptionsBuilder.fromFnPromise(Zonas.listar())
                    .withOption('processing', true)
                    .withPaginationType('full_numbers')
                    .withSelect({
                        style: 'os',
                        selector: 'td'
                    });
            vm.dtColumns = [
                DTColumnBuilder.newColumn('CODZON').withTitle('CODZON'),
                DTColumnBuilder.newColumn('NOMZON').withTitle('NOMZON'),
                DTColumnBuilder.newColumn('MUNZON').withTitle('MUNZON'),
                DTColumnBuilder.newColumn('ZONATCS').withTitle('ZONATCS'),
                DTColumnBuilder.newColumn('DANE').withTitle('DANE')
            ];
            vm.dtInstance = {};            
        }
        angular.module('enelar', [
				'datatables',
				'datatables.select',
				'datatables.scroller',
				'datatables.buttons',
                'oitozero.ngSweetAlert',
                'ngSanitize',
                'ui.bootstrap',
                'flow',
                'ngAnimate'
            ]).controller('MainController', MainController);
        </script>
        <script src="/ng_enelar/directives/directives.js"></script>
        <script src="/ng_enelar/services/services.js"></script>
        <script src="/ng_enelar/factories/factories.js"></script>
</body>
</html>