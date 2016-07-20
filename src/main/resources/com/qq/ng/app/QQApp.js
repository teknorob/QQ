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
     .when('/serviceLogin', {
         templateUrl: 'pages/serviceLogin.html',
         controller: 'serviceLoginController'
     })
     .otherwise(
     {
    	 templateUrl: '404.html',
     });
 });
 
 var httpConfig = {headers: {'Accept': 'application/json;odata=verbose'}};
 