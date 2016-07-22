QQApp.controller('serviceController', function($scope, $http, $rootScope, userService) {

//    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/queuesWS");
//    webSocket.onmessage = function (msg) { $scope.updateQueues(); };
//    webSocket.onclose = function () { alert("WebSocket connection closed") };
    
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
                });
            }
        });
    }
    
    $scope.updateQueues = function(){
        $http.get("/queues", httpConfig).success(function(response) {
            $scope.queues = response.queues;
            angular.forEach($scope.queues, function (queue){
                $scope.updateQueueTickets(queue);
            } );
        });
    };
	
    $scope.submitServiceLoginForm = function(){
        $http.post("/serviceLogin", $scope.login, httpConfig).success(function(response){
            $http.get("/user", httpConfig).success(function(response) {
                $scope.user = response;
                userService.setUser($scope.user);
                $rootScope.$broadcast('userUpdated');
            });
        }); 
    };
    
    $scope.login={};
    $scope.updateQueues();

});