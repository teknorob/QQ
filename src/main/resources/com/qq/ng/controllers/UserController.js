QQApp.controller('userController', function($scope, $http, userService) {
    
    $http.get("/user", httpConfig).success(function(response) {
        $scope.user = response.user;
        setRoleCodeOnUser($scope.user);
        userService.setUser($scope.user);
    });
    
    setRoleCodeOnUser = function(user) {
        if (user != null) {
            $http.get("/roles/" + user.roleId, httpConfig).success(
                    function(response) {
                        user.roleCode = response.roles.roleCode;
                        userService.setUser(user);
                    });
        }
    };
    
    $scope.signout = function()
    {
        $http.get("/logout", httpConfig).success(function(response) {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
              console.log('User signed out.');
            });
            userService.setUser(null);
            $scope.user = null;
        });
    }
    
});