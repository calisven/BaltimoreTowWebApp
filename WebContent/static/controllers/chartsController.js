'use strict'

const MONTHS_IN_YEAR = 12;

angular.module('mainApp.chartControllers', []).controller('chartCtrl', ['$scope', 'SharedData', 'ChartServices',
        function($scope, SharedData, ChartServices){
    
    $scope.dataReady = false;
    
    $scope.drawCharts = false;
            
    $scope.searchType = "vSearch";
    
    // The database query takes time, and therefore requires
    // Angular to hide the charts until they are ready
    $scope.$watch("dataReady", function(newValue, oldValue) {
        
        // Prevents the watcher from firing right away
    	if ( newValue === oldValue ) { return; }
    	
        // Get all data results from a shared data
        // service
        $scope.labels = SharedData.getLabels();
        $scope.data = [];
        $scope.dataStolenPie = [];
        
        var series = SharedData.getAvailableYears();

         // getNumYears should return the total number of years
         for ( var i=0; i < SharedData.getAvailableYears().length; i++ ) {
             
            $scope.data.push(SharedData.getYearTow(series[i]));
            $scope.dataStolenPie.push(SharedData.getYearStolen(series[i]));
            
         }

        $scope.series = SharedData.getAvailableYears();

        $scope.onClick = function (points, evt) {
            console.log(points, evt);   
        };
    
    	$scope.dataTotalsPie = {
    		
    		series: ['Sales', 'Income', 'Expense', 'Laptops', 'Keyboards'],
            data: []
        };
        
        for( var i=0; i < SharedData.getAvailableYears().length; i++ ) {
            
            var tempJson = {
                            x: SharedData.getAvailableYears()[i] + " Total",
                            y: SharedData.getYearTotal(SharedData.getAvailableYears()[i])
                           }
            $scope.dataTotalsPie.data.push(tempJson);
        }
    
        $scope.configTotalPaid = {
            title: "Average Dollar Amount Charged Per Tow",
            tooltips: true,
            labels: true,
            mouseover: function() {},
            mouseout: function() {},
            click: function() {},
            legend: {
                display: true,
                position: 'right'
            }
        };
    	
        $scope.dataAverageCharge = [];
        $scope.dataAverageCharge["series"] =  SharedData.getAvailableYears();
        var dataVals = [];
    	
    	
        $scope.configTotalPaid.title = "Monthly Tow Charge Average (Dollars)";


        for( var i=0; i<MONTHS_IN_YEAR; i++ ){
        		
   	        var yArray = [];
            
            for ( var k=0; k < SharedData.getAvailableYears().length; k++ ){
                yArray.push(SharedData.getYearAveragePaid(SharedData.getAvailableYears()[k])[i]);
            }
       
            var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
            dataVals.push(jsonObj);
        	
        }
    	
        $scope.dataAverageCharge["data"] = dataVals;
        
        $scope.data = [];
        $scope.data["series"] = [];
        $scope.stolenCharts = [];
        $scope.charts = [];
        
        var yearStr = SharedData.getAvailableYears();
        
        for( var i=0; i<yearStr.length; i++ ) {
            $scope.charts.push(yearStr[i] + ' Tows');
            $scope.stolenCharts.push(yearStr[i] + ' Stolen');
            $scope.data["series"].push('Vehicles Towed ' + yearStr[i])
        }
        
        // Set default value; Otherwise blank is shown
        $scope.yearChart = yearStr[0] + ' Tows';
        $scope.yearStolenChart = yearStr[0] + ' Stolen';
    
        // Force event listeners to fire
        $scope.changeStolenPie();
        
        var dataVals = [];
    	
        $scope.config.title = "Vehicles Towed: " + SharedData.getDisplayString();
    		
        for( var i=0; i<MONTHS_IN_YEAR; i++ ){
        		
            var yArray = [];
            for ( var k=0; k < SharedData.getAvailableYears().length; k++ ){
                yArray.push(SharedData.getYearTow(SharedData.getAvailableYears()[k])[i]);
            }

            var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
            dataVals.push(jsonObj);
        }

        $scope.data["data"] = dataVals;
        
        $scope.drawCharts = true;
    
    });
    
    $scope.doWork = function() {
        
        ChartServices.getAvailableYears()
            .success(function(data) {
                SharedData.setAvailableYears(data); 
            }).error(function(err) { 
                console.log(err);
        });
        
    	ChartServices.getMonthlyTows().success(function(data) { 
	    		
            
            var yearArray = SharedData.getAvailableYears();
            
                var allSeries = {};
	    		var labels = [];
                var allAveragePaid = {};
                var allStolen = {};
            
                for ( var i=0; i < yearArray.length; i++ ) {
                    allSeries[yearArray[i]] = [];
                    allAveragePaid[yearArray[i]] = [];
                    allStolen[yearArray[i]] = [];
                }
	    		
                for( var i=0; i < yearArray.length; i++ ) {
                    
//	               angular.forEach(data[yearArray[i]], function(value, key) {
//	                   this.push(key);
//	               }, labels);
                   
                   angular.forEach(data[yearArray[i]], function(value, key) {
	                   this.push(value);
	               }, allSeries[yearArray[i]]);
                    
                   angular.forEach(data[yearArray[i] + 'Paid'], function(value, key) {
	                   this.push(value);
	    		   }, allAveragePaid[yearArray[i]]);
                    
                   angular.forEach(data[yearArray[i] + 'Stolen'], function(value, key) {
	                   this.push(value);
	    		   }, allStolen[yearArray[i]]);
                    
                }
	    		      
	    		angular.forEach(data[SharedData.getAvailableYears()[0]], function(value, key) {
	               this.push(key);
	            }, labels);

	    		for( var i=0; i < yearArray.length; i++ ) {
                    
                    SharedData.setYearTow(yearArray[i], allSeries[yearArray[i]]);
                    SharedData.setYearTotal(yearArray[i], data[yearArray[i] + 'Total']);
                    
                    SharedData.setYearAveragePaid(yearArray[i], allAveragePaid[yearArray[i]]);
                    
                    SharedData.setYearStolen(yearArray[i], allStolen[yearArray[i]]);
                }	
	    		
	    		SharedData.setLabels(labels);
            
	    		SharedData.setDataReady(true);
	    		
	    		$scope.dataReady = true;
	    		
	    	}).error(function(status, data) {
	    		
	    		return status;
	    	});		
    }
    
    $scope.vehicles = ['Lincoln', 'Ford'];
    

    $scope.config = {
            title: "Vehicles Towed",
            tooltips: true,
            labels: true,
            mouseover: function() {},
            mouseout: function() {},
            click: function() {},
            legend: {
                display: true,
                position: 'right'
            }
        };
    
    $scope.configTotalsPie = {
        title: "Total Vehicles Towed: " + SharedData.getDisplayString(),
        tooltips: true,
        labels: true,
        mouseover: function() {},
        mouseout: function() {},
        click: function() {},
        legend: {
            display: true,
            position: 'right'
        },
        "innerRadius": "0",
        "lineLegend": "lineEnd"
    };
                                                                            
    $scope.configStolenPie = {
        title: "Stolen Vehicles Towed by Month: " + SharedData.getDisplayString(),
        tooltips: true,
        labels: false,
        mouseover: function() {},
        mouseout: function() {},
        click: function() {},
        legend: {
            display: true,
            position: 'right'
        },
        "innerRadius": "0",
        "lineLegend": "lineEnd"
    };
    
    $scope.configVehicleSearch = {
        title: "Please Initiate A Search",
        tooltips: true,
        labels: false,
        mouseover: function() {},
        mouseout: function() {},
        click: function() {},
        legend: {
            display: true,
            position: 'right'
        }
    };
                                                                            
    
    $scope.submitVehicle = function() {
        
        if (! /\S/.test($scope.model)) {
            // string is empty or just whitespace
            $scope.model = "";
        }
    	
    	ChartServices.getVehicle($scope.make, $scope.model, $scope.searchType).success(function(data) {
            
            if ( $scope.make === undefined || ! $scope.make ) {
                console.log("Bad request: No make found");
                return;
            }
            
			$scope.dataVehicleSearch = [];
			$scope.configVehicleSearch.title = $scope.make;
            
            // Prevents 'undefined' from displaying in the chart title
            // should the user not use vehicle model as part of their search
            // This also (interestingly, thanks AngularJS...) forces the 
            // charts to update once new data is received
            if ( typeof $scope.model != 'undefined' ) {
                $scope.configVehicleSearch.title += " " + $scope.model;
            }
            else {
                $scope.configVehicleSearch.title += " ";
            }
            
		
            if ( $scope.searchType === 'vSearch' ) {
                $scope.configVehicleSearch.title += " Vehicles Towed";
            }
            else if ( $scope.searchType === 'sSearch' ) {
                $scope.configVehicleSearch.title += " Vehicles Towed and Reported Stolen";
            }
            else {
                $scope.configVehicleSearch.title = "Bad search type request";
            }
            
			$scope.dataVehicleSearch['series'] = SharedData.getAvailableYears();
            
			var dataVals = [];

			for( var i=0; i < MONTHS_IN_YEAR; i++ ){
        
        		var yArray = [];
                
                for( var k=0; k < SharedData.getAvailableYears().length; k++ ) {
                    yArray.push(data[SharedData.getAvailableYears()[k]][i]);
                }
        		
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
        		dataVals.push(jsonObj);       	
    		}
			
			$scope.dataVehicleSearch["data"] = dataVals;
            
    	}).error(function(error) {
    		$scope.vehicleMessage = "Please fill in the 'Make' field";
    	});
    };
    
    
    $scope.changeStolenPie = function() {
    	
    	$scope.dataStolenPie = [];
    	$scope.dataStolenPie["series"] =  ['Vehicles Stolen'];
    	var dataVals = [];
        
        var availYears = SharedData.getAvailableYears();
        
        for( var k=0; k < availYears.length; k++ ) {
            if ( $scope.yearStolenChart === availYears[k] + ' Stolen' ) {
            
    		  $scope.configStolenPie.title = "Vehicles Stolen: " + availYears[k];
    		
    		  for( var i=0; i<SharedData.getYearStolen(availYears[k]).length; i++ ){
        		
                var yArray = [];
        		yArray.push(SharedData.getYearStolen(availYears[k])[i]);
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
                
        		dataVals.push(jsonObj);
              }
    	   }
        }
        
    	$scope.dataStolenPie["data"] = dataVals;
    }
    
}]);