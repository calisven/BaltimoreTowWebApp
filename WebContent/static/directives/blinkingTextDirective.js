angular.module('mainApp.blinkingTextDirective', [])
    .directive('blink', function($timeout) {
    return {
        restrict: 'E',
        transclude: true,
        scope: {},
        controller: function($scope, $element) {
            function showElement() {
                $element.css("display", "inline");
                $timeout(hideElement, 750);
            }
 
            function hideElement() {
                $element.css("display", "none");
                $timeout(showElement, 750);
            }
            showElement();
        },
        template: '<span ng-transclude></span>',
        replace: true
    };
});