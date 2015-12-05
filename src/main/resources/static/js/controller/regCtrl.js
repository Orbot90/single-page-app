app.controller('RegistrationController', ['$scope', '$http', 'TokenStorage', '$location', function($scope, $http, TokenStorage, $location) {

    $scope.register = function() {

        var reg = $scope.reg;

        if(reg.password != reg.passwordrep) {
            $scope.passIncorrect = true;
            return;
        }

        $scope.passIncorrect = false;
        $scope.lock = true;

        var model = {
            username: reg.username,
            password: reg.password
        }

        $http({
            method: 'POST',
            url: '/join',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $.param(model)
        }).success(function(data) {
            if(data.success) {
                TokenStorage.store(data.token);
                localStorage.setItem('authenticated', true);
                $scope.lock = false;
                $location.path('/application');
            }
        }).error(function(data) {
            $scope.lock = false;
           $scope.fieldErrors = data.fieldErrors;
        });


    }

}]);