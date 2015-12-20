'use strict'

angular.module('mainApp.homeController', []).controller('homeCtrl', ['$scope', 'ChartServices', 'SharedData',
     function($scope, ChartServices, SharedData) {
		
	 	$scope.dummy = ChartServices.getHeartbeat().success(function(data) {
	 		
	 		console.log(data);
            
	 		return data;
	 	}).error(function(status, data){
	 		
	 		console.log("well shit");
	 		//console.log(status);
	 		console.log(data);
	 	});

	 	
$scope.getData = function() {
	        
		ChartServices.getMonthlyTows().success(function(data) { 
	    		
				var series2014 = [];
	    		var series2015 = [];
	    		var labels = [];
	    		var averagePaid2014 = [];
	    		var averagePaid2015 = [];
	    		
	    		      
	    		angular.forEach(data['2014'], function(value, key) {
	    			  this.push(key);
	    			}, labels);
	    		
	    		angular.forEach(data['2014'], function(value, key) {
	    			this.push(value);
	    		}, series2014);
	    		
	    		angular.forEach(data['2015'], function(value, key) {
	    			this.push(value);
	    		}, series2015);
	    		
	    		angular.forEach(data['2014Paid'], function(value, key) {
	    			this.push(value);
	    		}, averagePaid2014);
	    		
	    		angular.forEach(data['2015Paid'], function(value, key) {
	    			this.push(value);
	    		}, averagePaid2015);
	    		
	    		
	    		SharedData.setYear2014(series2014);
	    		SharedData.setYear2015(series2015);
	    		
	    		SharedData.addTotal(series2014);
	    		SharedData.addTotal(series2015);
	    		
	    		SharedData.setLabels(labels);
	    		
	    		SharedData.setAveragePaid2014(averagePaid2014);
	    		SharedData.setAveragePaid2015(averagePaid2015);
	    		
	    		SharedData.setDataReady(true);
	    		
	    		
	    		
	    	}).error(function(status, data) {
	    		return status;
	    	});
	    
	};

}]);