
'use strict'

// Helper factory class that stores all database data results.
// This is particularly useful in the event that additional sections
// are added to the site, allowing the data to be shared
angular.module('mainApp.sharedData', []).factory('SharedData', [ function() {
	
	var data = {
			year2014: {},
			year2015: {},
			yearTotal2014: [],
            yearTotal2015: [],
            stolen2014: [],
            stolen2015: [],
			labels: [],
			titles: [],
			avg2014Paid: [],
			avg2015Paid: [],
            // Tells the page the data is
            // ready to display
			dataReady: false
	};
	
    // Getters and setters
	return {
        
		getYear2014: function() {
			return data.year2014;
		},
		getYear2015: function() {
			return data.year2015;
		},
		getLabels: function() {
			return data.labels;
		},
		getTitles: function() {
			return data.titles;
		},
		getTotal2014: function() {
			return data.yearTotal2014;
		},
        getTotal2015: function() {
			return data.yearTotal2015;
		},
		getAveragePaid2014: function() {
			return data.avg2014Paid;
		},
		getAveragePaid2015: function() {
			return data.avg2015Paid;
		},
        getStolen2014: function() {
            return data.stolen2014;
        },
        getStolen2015: function() {
            return data.stolen2015;  
        },
		setTitles: function(title) {
			data.titles = title;
		},
		setYear2014: function(yearData) {			
			data.year2014 = (JSON.parse("[" + yearData + "]"));
		},
		setYear2015: function(yearData) {
			data.year2015 = (JSON.parse("[" + yearData + "]"));
		},
		setLabels: function(dataLabels) {
			data.labels = dataLabels;
		},
		setAveragePaid2014: function(averagePaid) {
			data.avg2014Paid = (JSON.parse("[" + averagePaid + "]"));
		},
		setAveragePaid2015: function(averagePaid) {
			data.avg2015Paid = (JSON.parse("[" + averagePaid + "]"));
		},
        setStolen2014: function(stolenData) {
            data.stolen2014 = JSON.parse("[" + stolenData + "]");
        },
        setStolen2015: function(stolenData) {
            data.stolen2015 = JSON.parse("[" + stolenData + "]");
        },
		addTotal2014: function(yearData) {
			//data.yearTotal.push(yearData);
            data.yearTotal2014 = JSON.parse("[" + yearData + "]")
		},
        addTotal2015: function(yearData) {
			//data.yearTotal.push(yearData);
            data.yearTotal2015 = JSON.parse("[" + yearData + "]")
		},
		setDataReady: function(val) {
			data.dataReady = val;
		},
		getDataReady: function() {
			return data.dataReady;
		}
		
	};
	
}]);