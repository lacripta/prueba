function selectConfirma(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/select-confirma.html',
        scope: {
            valor: '=',
            col: '@',
            readonly: '=',
            required: '=',
            titulo: '@'
        },
        link: function (scope) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function ($scope) {
            $scope.acciones = [{codigo: "S", descripcion: "SI"}, {codigo: "N", descripcion: "NO"}];
        }
    };
}
function selectDatos(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/select-datos.html',
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
        link: function (scope) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function () {
        }
    };
}
function selectSimple(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/select-simple.html',
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
        link: function (scope) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function () {
        }
    };
}
function selectObjeto(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/select-datos.html',
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
        link: function (scope) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
            $('.select').select2({
                placeholder: "Escoja una opcion",
                language: 'es'
            });
        },
        controller: function () {
        }
    };
}
function inputText(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/input-text.html',
        link: function (scope, element) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
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
function inputNumber(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/input-number.html',
        link: function (scope, element) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
            $.material.init(element);
        },
        scope: {
            valor: '=',
            col: '@',
            readonly: '=',
            required: '=',
            titulo: '@'
        },
        controller: function () {

        }
    };
}
function inputRadio(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/input-radio.html',
        link: function (scope, element) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
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
function inputDate(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/input-date.html',
        scope: {
            options: '&',
            valor: '=',
            readonly: '=',
            required: '=',
            col: '@',
            titulo: '@'
        },
        link: function (scope, element, attrs, ctrl) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
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
function inputMeses(QuitarAcentos) {
    return {
        restrict: 'E',
        templateUrl: 'site/templates/input-date.html',
        scope: {
            valor: '=',
            readonly: '=',
            required: '=',
            col: '@',
            titulo: '@'
        },
        link: function (scope, element, attrs, ctrl) {
            scope.nombre = QuitarAcentos(scope.titulo).split(' ').join('_');
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