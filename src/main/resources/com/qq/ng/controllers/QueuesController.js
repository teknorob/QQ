QQApp.controller('queuesController', function($scope, $http, userService) {

	$http.get("/queues", httpConfig).success(function(response) {
		$scope.queues = response.queues;
		angular.forEach($scope.queues, function (queue){
		    $scope.updateQueueTickets(queue);
		} );
		$scope.user = userService.getUser();
		$scope.showCreateForm = false;
	});
	
	$scope.submitCreateQueueForm = function(){
		var newQueue = $scope.newQueue;
		$http.post("/queues", newQueue, httpConfig ).success(function(response)
		{
			$scope.queues.push(response.queues);
		});
	};
	
	$scope.submitDeleteQueueForm = function(form){
		var queue = form.queue.$modelValue;
		$http.delete("/queues/" + queue.queueId, httpConfig ).success(function(response){
			$http.get("/queues", httpConfig).success(function(response) {
				$scope.queues = response.queues;
			});
		});
	}
	
	$scope.toggleUserInQueue = function(form){
	    var queue = form.queue.$modelValue;
	    queue.tickets = null;
	    $http.post("/tickets", queue, httpConfig).success(function(response){
            $scope.updateQueueTickets(queue);
	    });
	}
	
	$scope.updateQueueTickets = function(queue){
	    $http.get("/tickets?queueId=" +queue.queueId, httpConfig).success(function(response){
            queue.tickets = response.tickets;
        });
	}
	
	$scope.$on('userUpdated', function(event, args) {
		$scope.user = userService.getUser();
	});
});