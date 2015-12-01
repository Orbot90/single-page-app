app.controller('LoginController', ['$scope', '$http', 'TokenStorage', '$location', function($scope, $http, TokenStorage, $location) {

    $scope.login = function() {
        var model = {
            username: $scope.user.username,
            password: $scope.user.password
        }

        $scope.lock = true;
        $http({
            method: 'POST',
            url: '/login',
            headers: {'Content-Type': 'application/www-form-urlencoded'},
            data: model
        }).success(function(data, status, headers) {
            TokenStorage.store(headers('X-AUTH-TOKEN'));
            localStorage.setItem('authenticated', true);
            $scope.lock = false;
            $location.path('/application');
        }).error(function() {
            $scope.lock = false;
            localStorage.removeItem('authenticated');
        });

    }

    $scope.init = function() {
        $http({
            method: 'GET',
            url: '/current'
        }).success(function (data) {
            if(data.username == 'anonymousUser') {
                TokenStorage.clear();
                localStorage.removeItem('authenticated');
            }
        })
    }
}]);