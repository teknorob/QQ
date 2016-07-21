QQApp.controller('serviceController', function($scope, $http, $rootScope, userService) {

    $scope.login={};
    
//    $http.get("/queues", httpConfig).success(function(response) {
//        $scope.queues = response.queues;
//        angular.forEach($scope.queues, function (queue){
//            $scope.updateQueueTickets(queue);
//        } );
//    });
	
    $scope.submitServiceLoginForm = function(){
        $http.post("/serviceLogin", $scope.login, httpConfig).success(function(response){
            $http.get("/user", httpConfig).success(function(response) {
                $scope.user = response;
                userService.setUser($scope.user);
                $rootScope.$broadcast('userUpdated');
            });
        }); 
    } 
    
});