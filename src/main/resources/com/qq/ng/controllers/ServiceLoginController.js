QQApp.controller('serviceLoginController', function($scope, $http, userService) {

	$scope.user = userService.getUser();
	
	
	$scope.submitServiceLoginForm = function(form){
		$http.post("/serviceLogin", form.$modelValue, httpConfig).success(function(response){
			$http.get("/user", httpConfig).success(function(response) {
		        userService.setUser($scope.user);
			});
		});	
	}
});