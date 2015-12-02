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
        .when('/join', {
            templateUrl: '/registration.html',
            controller: 'RegistrationController'
        })
        .otherwise({redirectTo: '/application'})
    }])
    .run(function($rootScope, $location, TokenStorage) {
        $rootScope.$on("$routeChangeStart", function(event, next, current) {
            if(!(next.templateUrl == '/login.html' || next.templateUrl == '/registration.html')
                && !eval(localStorage.getItem('authenticated'))) {
                $location.path('/login')
            }
        })
    })