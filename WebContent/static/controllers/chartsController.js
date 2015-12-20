'use strict'

angular.module('mainApp.chartControllers', []).controller('chartCtrl', ['$scope', 'SharedData', 'ChartServices',
        function($scope, SharedData, ChartServices){
    
    $scope.dataReady = false;
    
    $scope.drawCharts = false;
    
    // The database query takes time, and therefore requires
    // Angular to hide the charts until they are ready
    $scope.$watch("dataReady", function(newValue, oldValue) {
        
    	if ( newValue === oldValue ) { return; }
    	
        // Get all data results from a shared data
        // service
        $scope.labels = SharedData.getLabels();
        $scope.data = [];
        $scope.dataStolenPie = [];

        $scope.data.push(SharedData.getYear2014());
        $scope.data.push(SharedData.getYear2015());
        
        $scope.dataStolenPie.push(SharedData.getStolen2014());
        $scope.dataStolenPie.push(SharedData.getStolen2015());

        $scope.series = ['2014', '2015'];

        $scope.onClick = function (points, evt) {
            console.log(points, evt);   
        };
    
    	$scope.dataTotalsPie = {
    		
    		series: ['Sales', 'Income', 'Expense', 'Laptops', 'Keyboards'],
    		
    		data: [{
    	          x: "2014 Total",
    	          y: SharedData.getTotal2014()
    	          //tooltip: "This is something"
    	      },{
    	          x: "2015 Total",
    	          y: SharedData.getTotal2015()
    	      }]
        };
    
        $scope.configTotalPaid = {
            title: "Average Vehicle Tow Charge",
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
   $scope.dataAverageCharge["series"] =  ['2014', '2015'];
   var dataVals = [];
    	
    	
   $scope.configTotalPaid.title = "Tow Charge Average";
    		
   for( var i=0; i<SharedData.getAveragePaid2014().length; i++ ){
        		
   		var yArray = [];
        yArray.push(SharedData.getAveragePaid2014()[i]);
        yArray.push(SharedData.getAveragePaid2015()[i]);
       
        //yArray.push(SharedData.getAveragePaid2014()[i]);
        var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
        dataVals.push(jsonObj);
        	
   }
    	
    $scope.dataAverageCharge["data"] = dataVals;

    $scope.charts = [ '2014 Tows', '2015 Tows' ];
    $scope.stolenCharts = ['2014 Stolen', '2015 Stolen'];
        
    // Set default value; Otherwise blank is shown
    $scope.yearChart = '2014 Tows';
    $scope.yearStolenChart = '2014 Stolen';
    
    // Force event listeners to fire
    $scope.changeChart();
    $scope.changeStolenPie();
    	
    	$scope.drawCharts = true;
    });
    
    $scope.doWork = function() {
        
    	ChartServices.getMonthlyTows().success(function(data) { 
	    		
				var series2014 = [];
	    		var series2015 = [];
	    		var labels = [];
	    		var averagePaid2014 = [];
	    		var averagePaid2015 = [];
                var stolen2014 = [];
                var stolen2015 = [];
	    		
	    		      
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
            
                angular.forEach(data['2014Stolen'], function(value, key) {
	    			this.push(value);
	    		}, stolen2014);
            
                angular.forEach(data['2015Stolen'], function(value, key) {
	    			this.push(value);
	    		}, stolen2015);
	    		
	    		
	    		SharedData.setYear2014(series2014);
	    		SharedData.setYear2015(series2015);
	    		
	    		SharedData.addTotal2014(data["2014Total"]);
	    		SharedData.addTotal2015(data["2015Total"]);
	    		
	    		SharedData.setLabels(labels);
	    		
	    		SharedData.setAveragePaid2014(averagePaid2014);
	    		SharedData.setAveragePaid2015(averagePaid2015);
            
                SharedData.setStolen2014(stolen2014);
                SharedData.setStolen2015(stolen2015);
            
	    		SharedData.setDataReady(true);
	    		
	    		$scope.dataReady = true;
	    		
	    	}).error(function(status, data) {
	    		
	    		return status;
	    	});
	    
		
    }
    
    $scope.vehicles = ['Lincoln', 'Ford'];
    

    $scope.config = {
            title: "Vehicles Towed: 2014",
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
        title: "Vehicles Towed Total: 2014 & 2015",
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
                                                                            
    $scope.configStolenPie = {
        title: "Vehicles Stolen by Month: 2014 & 2015",
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
    	
    	ChartServices.getVehicle($scope.make, $scope.model).success(function(data) {
            // TODO
            if ( $scope.make === undefined || ! $scope.make ) {
                return;
            }
			$scope.dataVehicleSearch = [];
			$scope.configVehicleSearch.title = $scope.make + " " + $scope.model + " Tows";
			
			$scope.dataVehicleSearch['series'] = ['2014', '2015'];
			
			var dataVals = [];
			
			for( var i=0; i<data["2014"].length; i++ ){
        		
        		var yArray = [];
        		yArray.push(data["2014"][i]);
        		yArray.push(data["2015"][i]);
        		
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
        		dataVals.push(jsonObj);       	
    		}
			
			$scope.dataVehicleSearch["data"] = dataVals;
			
    	}).error(function(error) {
    		$scope.vehicleMessage = "Please fill in the 'Make' field";
    	});
    };
    
    $scope.changeChart = function() {
    	
    	$scope.data = [];
    	$scope.data["series"] =  ['Vehicles Towed'];
    	var dataVals = [];
    	
    	if ( $scope.yearChart === "2014 Tows") {
            
    		$scope.config.title = "Vehicles Towed: 2014";
    		
    		for( var i=0; i<SharedData.getYear2014().length; i++ ){
        		
        		var yArray = [];
        		yArray.push(SharedData.getYear2014()[i]);
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
        		dataVals.push(jsonObj);
        	}
    	}
    	else if( $scope.yearChart === "2015 Tows"){
            
    		$scope.config.title = "Vehicles Towed: 2015";
    		
    		for( var i=0; i<SharedData.getYear2015().length; i++ ){
        		
        		var yArray = [];
        		yArray.push(SharedData.getYear2015()[i]);
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
        		dataVals.push(jsonObj);
        	}
    	}

    	$scope.data["data"] = dataVals;
    }
    
    $scope.changeStolenPie = function() {
    	
    	$scope.dataStolenPie = [];
    	$scope.dataStolenPie["series"] =  ['Vehicles Stolen'];
    	var dataVals = [];
    	
    	if ( $scope.yearStolenChart === '2014 Stolen' ) {
            
    		$scope.configStolenPie.title = "Vehicles Stolen: 2014";
    		
    		for( var i=0; i<SharedData.getStolen2014().length; i++ ){
        		
        		var yArray = [];
        		yArray.push(SharedData.getStolen2014()[i]);
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
                
        		dataVals.push(jsonObj);
        	}
    	}
    	else if( $scope.yearStolenChart === '2015 Stolen' ){
            
    		$scope.configStolenPie.title = "Vehicles Stolen: 2015";
    		
    		for( var i=0; i<SharedData.getStolen2015().length; i++ ){
        		
        		var yArray = [];
        		yArray.push(SharedData.getStolen2015()[i]);
        		var jsonObj = ({ x: SharedData.getLabels()[i], y: yArray });
      
        		dataVals.push(jsonObj);
        	}
    	}
        
    	$scope.dataStolenPie["data"] = dataVals;
    }
    
}]);