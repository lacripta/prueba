function selectConfirma() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/select-confirma.html',
        scope: {
            valor: '=',
            col: '@',
            readonly: '=',
            required: '=',
            titulo: '@'
        },
        link: function (scope, element) {
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function ($http, $scope) {
            $scope.acciones = [{codigo: "S", descripcion: "SI"}, {codigo: "N", descripcion: "NO"}];
        }
    };
}
function selectDatos() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/select-datos.html',
        scope: {
            valor: '=',
            datos: '=',
            titulo: '@',
            col: '@',
            readonly: '=',
            required: '=',
            value: '@',
            label: '@'
        },
        link: function (scope, element) {
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function ($scope) {
        }
    };
}
function selectSimple() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/select-simple.html',
        scope: {
            valor: '=',
            datos: '=',
            titulo: '@',
            col: '@',
            readonly: '=',
            required: '=',
            value: '@',
            label: '@'
        },
        link: function (scope, element) {
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function ($scope) {
        }
    };
}
function selectObjeto() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/select-datos.html',
        scope: {
            valor: '=',
            datos: '=',
            titulo: '@',
            col: '@',
            readonly: '=',
            required: '=',
            value: '@',
            label: '@'
        },
        link: function (scope, element) {
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function ($scope) {
        }
    };
}
function inputText() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/input-text.html',
        link: function (scope, element) {
            $.material.init(element);
        },
        scope: {
            valor: '=',
            col: '@',
            readonly: '=',
            required: '=',
            titulo: '@'
        },
        controller: function ($scope) {
            $scope.$watch('valor', function (nuevo) {
                if (typeof nuevo === 'string') {
                    $scope.valor = nuevo.toLocaleUpperCase();
                }
            });
        }
    };
}
function inputNumber() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/input-number.html',
        link: function (scope, element) {
            $.material.init(element);
        },
        scope: {
            valor: '=',
            col: '@',
            readonly: '=',
            required: '=',
            titulo: '@'
        },
        controller: function ($scope) {

        }
    };
}
function inputRadio() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/input-radio.html',
        link: function (scope, element) {
            $.material.init(element);
        },
        scope: {
            valor: '=',
            contenido: '=',
            col: '@',
            readonly: '=',
            required: '=',
            titulo: '@'
        },
        controller: function ($scope) {

        }
    };
}
function inputDate() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/input-date.html',
        scope: {
            options: '&',
            valor: '=',
            readonly: '=',
            required: '=',
            col: '@',
            titulo: '@'
        },
        link: function (scope, element, attrs, ctrl) {
            var input = element.find('.datePicker');
            $(input).datepicker({
                language: 'es',
                minView: 'days', //days,months,years
                view: 'days', //days,months,years
                autoClose: true,
                maxDate: new Date(),
                toggleSelected: false,
                dateFormat: "dd/mm/yyyy",
                onSelect: function (formattedDate, month, year) {
                    scope.valor = formattedDate;
                    scope.$applyAsync();
                }
            });
        },
        controller: function ($scope) {

        }
    };
}
function inputMeses() {
    return {
        restrict: 'E',
        templateUrl: '/ng_enelar/templates/input-date.html',
        scope: {
            valor: '=',
            readonly: '=',
            required: '=',
            col: '@',
            titulo: '@'
        },
        link: function (scope, element, attrs, ctrl) {
            var input = element.find('.datePicker');
            $(input).datepicker({
                language: 'es',
                minView: 'months', //days,months,years
                view: 'months', //days,months,years
                autoClose: true,
                maxDate: new Date(),
                toggleSelected: false,
                dateFormat: "dd/mm/yyyy",
                onSelect: function (formattedDate, month, year) {
                    scope.valor = formattedDate;
                    scope.$applyAsync();
                }
            });
        }
    };
}
angular.module('prueba')
        .directive('selectConfirma', selectConfirma)
        .directive('selectDatos', selectDatos)
        .directive('selectSimple', selectSimple)
        .directive('selectObjeto', selectObjeto)
        .directive('inputText', inputText)
        .directive('inputRadio', inputRadio)
        .directive('inputDate', inputDate)
        .directive('inputMeses', inputMeses)
        .directive('inputNumber', inputNumber);