app.controller('LoginController', ['$scope', '$http', function($scope, $http) {
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
            data: $.param(model)
        })

    }

    $scope.init = function() {
        $http({
            method: 'GET',
            url: '/current'
        }).success(function (data) {
            JSON.stringify(data);
        })
    }
}]);