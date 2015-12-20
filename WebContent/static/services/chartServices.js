'use strict'

angular.module('mainApp.chartServices', []).factory('ChartServices', [ '$http', function($http) {
	
	return {
		
		getHeartbeat: function() {
			return $http.get('/CSWebProject/dataApi/heartbeat');
		},
		
		getVehicles: function(vehicles) {
		
			if ( vehicles && vehicles.length > 0) {
				return $http.get(apiPath + '/getVehicles', vehicles)
			}
		},
		
		getMonthlyTows: function() {
			return $http.get(apiPath + '/monthlyTows');
		},
		
		getVehicle: function(vMake, vModel) {
			
			return $http({
				url: apiPath + '/vehicle',
				method: "GET",
				params: {vehicleMake: vMake, vehicleModel: vModel}
			});
		}
	}
}]);