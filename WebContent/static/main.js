var loginApp = angular.module('mainApp',['ui.router', 'ngResource', 'angularCharts', 'mainApp.chartControllers',
                                         'mainApp.homeController', 'mainApp.chartServices', 'mainApp.sharedData',
                                        'mainApp.blinkingTextDirective']);
var apiPath = '/CSWebProject/dataApi';

// This is dependency injection along with annotating (annotating is needed for minified)
loginApp.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider) {
        
        // Any other URL given, go to '/'
        $urlRouterProvider.otherwise('/');
        
        $stateProvider
            // If you get 'home' in the URL, return 'index.html'
            // state name = what is referenced by $state variable (i.e. state.go('nav.home'))
            // The 'nav' state will always return 'nav-panel.html', and at least one of the
            // child pages below
            .state('nav', {
                url: '/',
                views: {
                    "mainDisplay": {
                        templateUrl: 'static/angular-templates/nav-panel.html',
                    }
                },
                abstract: true
            })
            .state('nav.home', {
                    url: '',
                    templateUrl: "static/angular-templates/home.html",
                    controller: 'homeCtrl'
            })
            .state('nav.charts', {
                    url: '',
                    templateUrl: "static/angular-templates/charts.html",
                    controller: 'chartCtrl',
                    directive: 'blink'
            })
            .state('nav.about', {
                url: '',
                templateUrl: "static/angular-templates/about.html"
            });
}]);
