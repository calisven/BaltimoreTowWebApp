'use strict'

// Helper factory class that stores all database data results.
// This is particularly useful in the event that additional sections
// are added to the site, allowing the data to be shared
angular.module('mainApp.sharedData', []).factory('SharedData', [ function() {
	
	var allYears = ['2014', '2015'];

	var data = {
        
            availableYears: [],
			yearResults:  {
				yearTowResults: {},
				yearTotalResults: {},
				stolenResults: {},
				avgPaidResults: {}
			},
			labels: [],
			titles: [],
            // Tells the page the data is
            // ready to display
			dataReady: false
	};
	
	for( var i=0; i<allYears.length; i++ ) {
		data.yearResults.yearTowResults[allYears[i]] = {};
		data.yearResults.yearTotalResults[allYears[i]] = [];
		data.yearResults.stolenResults[allYears[i]] = [];
		data.yearResults.avgPaidResults[allYears[i]] = [];
	}
	
    // Getters and setters
	return {

		getYearTow: function(year) {
			return data.yearResults.yearTowResults[year];
		},
		getLabels: function() {
			return data.labels;
		},
		getTitles: function() {
			return data.titles;
		},
		getYearTotal: function(year) {
			return data.yearResults.yearTotalResults[year];
		},
		getYearAveragePaid: function(year) {
			return data.yearResults.avgPaidResults[year];
		},
        getYearStolen: function(year) {
        	return data.yearResults.stolenResults[year];
        },
		setTitles: function(title) {
			data.titles = title;
		},
		setYearTow: function(year, yearData) {
			data.yearResults.yearTowResults[year] = (JSON.parse("[" + yearData + "]"));
		},
		setLabels: function(dataLabels) {
			data.labels = dataLabels;
		},
		setYearAveragePaid: function(year, averagePaid) {
			data.yearResults.avgPaidResults[year] = (JSON.parse("[" + averagePaid + "]"));
		},
        setYearStolen: function(year, stolenData) {
        	data.yearResults.stolenResults[year] = JSON.parse("[" + stolenData + "]");
        },
		setYearTotal: function(year, yearData) {
			data.yearResults.yearTotalResults[year] = JSON.parse("[" + yearData + "]");
		},
		setDataReady: function(val) {
			data.dataReady = val;
		},
		getDataReady: function() {
			return data.dataReady;
		},
        setAvailableYears: function(years) {
            years = years + "";
            data.availableYears = years.split(",");
        },
        getAvailableYears: function() {
            return data.availableYears;
        },
        getDisplayString: function() {
            // There are a number of chart titles that need
            // an array of all years displayed; this will 
            // place all of them in a string
            var displayStr = '';
            
            for( var i=0; i < data.availableYears.length; i++ ) {
                displayStr = displayStr + data.availableYears[i];
        
                if( i + 1 !== data.availableYears.length ) {
                    displayStr += ", "
                }
            }
            return displayStr;
        }
		
	};
	
}]);