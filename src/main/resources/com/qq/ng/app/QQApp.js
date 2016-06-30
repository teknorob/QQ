 var QQApp = angular.module("QQApp",[
 'ngRoute'
 ]);

 QQApp.config(function ($routeProvider) {
     $routeProvider.when('/', {
         templateUrl: 'pages/queues.html',
         controller: 'queuesController'
     })
     .when('/players', {
         templateUrl: 'pages/queues.html',
         controller: 'queuesController'
     })
 });
 
 var httpConfig = {headers: {'Accept': 'application/json;odata=verbose'}};
 