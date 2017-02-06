
(function() {
	var app = angular.module('MyApp', ['ngAnimate', 'ui.bootstrap']);

	app.controller('beliefCtrl', function($scope, $location, $http) {
		$scope.getBelief = function(restUrl) {
			$http.get(restUrl).success(function(data) {
				$scope.belief = data;
			});
		};
		
		$scope.clear = function() {
			/*
			 * preview.statusFilter = ""; 
			 */
		}
		
		$scope.create = function(restUrl) {
			var restUrl = 'belief/create';
			var data = {
					id: "2"
	            };
			
			$http.get(restUrl, data).success(function(data) {
				$scope.belief = data;
				
				alert(data);
			});
		};
		
		$scope.clear = function() {
			/*
			 * preview.statusFilter = ""; 
			 */
		}
		
		$scope.update = function(restUrl) {
			var restUrl = 'belief/update';
			var data = {
					id: "2"
	            };
			
			$http.get(restUrl, data).success(function(data) {
				$scope.belief = data;
				
				alert(data);
			});
		};
		
		$scope.clear = function() {
			/*
			 * preview.statusFilter = ""; 
			 */
		}
	});
})();
