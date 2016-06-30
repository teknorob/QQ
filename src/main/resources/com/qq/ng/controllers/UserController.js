QQApp.controller('userController', function($scope, $http, userService) {
	var url = "/players";

	$http.get(url, httpConfig).success(function(response) {
		$scope.user = response.user;
		setRoleCodeOnUser($scope.user);
		userService.setUser($scope.user);
	});

	$scope.$on("updateUser", function(event, user) {
		if (user.playerId == $scope.user.playerId) {
			$scope.user = user;
			setRoleCodeOnUser($scope.user);
		}
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
});