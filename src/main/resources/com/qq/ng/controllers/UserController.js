QQApp.controller('userController', function($scope, $http, $rootScope, userService) {

    $http.get("/user", httpConfig).success(function(response) {
        $scope.updateUser(response);
    });

    $scope.signout = function() {
        $http.get("/logout", httpConfig).success(function(response) {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function() {
                console.log('User signed out.');
            });
            userService.setUser(null);
            $scope.user = null;
            $rootScope.$broadcast('userUpdated');
        });
    }

    $scope.$on('event:google-plus-signin-success', function(event, authResult) {
        token = {
            "tokenId" : authResult.hg.id_token
        };
        $http.post("/login", token, httpConfig).success(function(response) {
            $scope.updateUser(response);
            $rootScope.$broadcast('userUpdated');
        });
    });

    $scope.updateUser = function(response) {
        $scope.user = response.user;
        $scope.setRoleCodeOnUser($scope.user);
        userService.setUser($scope.user);
    };

    $scope.setRoleCodeOnUser = function(user) {
        if (user != null) {
            $http.get("/roles/" + user.roleId, httpConfig).success(
                    function(response) {
                        user.roleCode = response.roles.roleCode;
                        userService.setUser(user);
                    });
        }
    };
    
    
});