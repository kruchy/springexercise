angular.module('app', ['ngMessages'])
    .controller('mainController', function ($scope, $http, $q) {
        $scope.signup = {};
        $scope.submit = function () {
            $http.post('/user/', {username: $scope.register.username, password: $scope.register.password}).then(
                function (response) {
                    $scope.registerForm.password.$setValidity('password.invalid', true);
                    $scope.registerForm.username.$setValidity('username.exists', true);
                    $scope.registerForm.username.$setValidity('username.invalid', true);
                    $scope.registered = true;
                    // alert('User registered successfully');

                }).catch(function (err) {
                if (err && err.data && err.data[0]) {
                    $scope.registerForm.password.$setValidity(err.data[0].code, false);
                    $scope.registerForm.username.$setValidity(err.data[0].code, false);
                }
                $q.defer().reject();
            })

        };
    })
    .directive('usernameValidator', function ($http, $q) {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModel) {
                ngModel.$asyncValidators.username = function (modelValue, viewValue) {
                    var defer = $q.defer();

                    return $http.post('/username/', {username: viewValue}).then(
                        function (response) {
                            scope.registerForm.username.$setValidity('username.exists', true);
                            scope.registerForm.username.$setValidity('username.invalid', true);
                            return true;
                        }
                    ).catch(function (err) {
                        if (err && err.data[0]) {
                            console.log(err.data[0].code);
                            scope.registerForm.username.$setValidity(err.data[0].code, false);
                        }
                        defer.reject();
                    });
                };
            }
        };
    })
    .directive('passwordValidator', function ($http, $q) {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModel) {
                ngModel.$asyncValidators.password = function (modelValue, viewValue) {
                    var defer = $q.defer();

                    return $http.post('/password/', {password: viewValue}).then(
                        function (response) {
                            scope.registerForm.password.$setValidity('password.invalid', true);
                            return true;
                        }
                    ).catch(function (err) {
                        if (err && err.data[0]) {
                            scope.registerForm.password.$setValidity(err.data[0].code, false);
                        }
                        defer.reject();
                    });
                };
            }
        };
    });