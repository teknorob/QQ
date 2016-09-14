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
		    $scope.queues.splice($scope.queues.indexOf(queue),1);
		});
	}
	
	$scope.toggleUserInQueue = function(form){
	    var queue = form.queue.$modelValue;
	    if(queue.userTicket == null){
            $http.post("/tickets", queue, httpConfig).success(function(response){
                $scope.updateQueueTickets(queue);
            });	        
	    }
	    else{
            $http.delete("/tickets/"+queue.userTicket.ticketId, httpConfig).success(function(response){
                $scope.updateQueueTickets(queue);
            });
	    }
	    
	}
	
	$scope.updateQueueTickets = function(queue){
	    $http.get("/tickets?queueId=" +queue.queueId, httpConfig).success(function(response){
            queue.tickets = response.tickets;
            queue.userTicket = null;
            var user = userService.getUser();
            if(user != null){
                angular.forEach(queue.tickets, function (ticket){
                    if(ticket.userId == user.userId){
                        queue.userTicket = ticket;
                    }
                    $scope.updateTicketUser(ticket);
                });
            }
        });
	}
	
	$scope.updateTicketUser = function(ticket){
        $http.get("/users/" + ticket.userId, httpConfig).success(function(response){
            ticket.user = response.user;
        });
    }
	
	$scope.testSms = function()
	{
		$http.get("/testSms", httpConfig).success(function(response){
			console.log("message id #" + response.messageId);
		});
	}
	
	$scope.$on('userUpdated', function(event, args) {
		$scope.user = userService.getUser();
	});
});