'use strict'

angular.module('mainApp.chartServices', []).factory('ChartServices', [ '$http', function($http) {
	
	return {
		
		getHeartbeat: function() {
			return $http.get('/CSWebProject/dataApi/heartbeat');
		},
        getAvailableYears: function() {
            return $http.get('/CSWebProject/dataApi/years');  
        },
		getVehicles: function(vehicles) {
		
			if ( vehicles && vehicles.length > 0) {
				return $http.get(apiPath + '/getVehicles', vehicles)
			}
		},
		
		getMonthlyTows: function() {
			return $http.get(apiPath + '/monthlyTows');
		},
		
		getVehicle: function(vMake, vModel, searchType) {
			
            var path;
            
            if ( searchType === "vSearch" ){
                path = '/vehicle';
            }
            else if( searchType === "sSearch" ){
                path = '/stolen';
            }
            else {
                return;
            }
            
			return $http({
				url: apiPath + path,
				method: "GET",
				params: {vehicleMake: vMake, vehicleModel: vModel}
			});
		}
	}
}]);