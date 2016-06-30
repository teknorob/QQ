QQApp.controller('queuesController', function($scope, $http, userService) {

	$http.get("/queues", httpConfig).success(function(response) {
		$scope.user = userServuce.getUser();
	});

});