'use strict'

/*
Gets a simple heartbeat from the server
*/
angular.module('mainApp.homeController', []).controller('homeCtrl', ['$scope', 'ChartServices', 'SharedData',
     function($scope, ChartServices, SharedData) {
		
	 	$scope.dummy = ChartServices.getHeartbeat().success(function(err, data) {
            if ( err ) {
                console.log("Failed to get a heartbeat from server: " + err);
            }
	 		return data;
	 	}).error(function(status, data){
	 		console.log(data);
	 	});

}]);