var app = angular.module('myApp', ['ngRoute'])
    .config(['$routeProvider',
    function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/application', {
            templateUrl: '/app.html'
        })
        .when('/login', {
            templateUrl: '/login.html',
            controller: 'LoginController'
        })
        .otherwise({redirectTo: '/application'})
    }])
    .run(function($rootScope, $location, TokenStorage) {
        $rootScope.$on("$routeChangeStart", function(event, next, current) {
            if(next.templateUrl != '/login.html'
                && !eval(localStorage.getItem('authenticated'))) {
                $location.path('/login')
            }
        })
    })