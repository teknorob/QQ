var QQApp = angular.module("QQApp",[
 'ngRoute',
 'directive.g+signin'
 ]);

 QQApp.config(function ($routeProvider) {
     $routeProvider.when('/', {
         templateUrl: 'pages/queues.html',
         controller: 'queuesController'
     })
     .when('/queues', {
         templateUrl: 'pages/queues.html',
         controller: 'queuesController'
     })
 });
 
 var httpConfig = {headers: {'Accept': 'application/json;odata=verbose'}};
 