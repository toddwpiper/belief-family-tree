
(function() {
	var app = angular.module('MyApp', ['ngAnimate', 'ui.bootstrap']);

	app.controller('beliefCtrl', function($scope, $location, $http) {
		$scope.get = function(restUrl) {
			var restUrl = 'belief';
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
	
	
	app.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, pextract)
	{
		$scope.extract = pextract;
		$scope.modifiedString =  JSON.stringify(JSON.parse(pextract.ipaJson.split('\r\n').join("")), undefined, 2);
	});
	
	
	
	app.directive('beliefForm', function() {
		return {
			restrict: 'E',
			templateUrl: 'resources/public/tmrangeForm.html',
			controller: ['$http', '$log', '$timeout', function($http, $log, $timeout) {
				this.preview = {};
				this.stacked = [];
				var tmRangePreviewCtrl = this;
				
				this.clear = function() {
					this.serviceResponse = {};
					this.attemptedUpdate = false;
					
				};
				
				this.clear();
				
				this.extractHeadstarts = function() {
					this.clear();
					this.attemptedUpdate = true;
					var restUrl = 'biblio/range/notOPI';
					var data = {
						changeDesc: this.preview.changeDesc,
		                startIpId: this.preview.startTmNumber,
		                endIpId: this.preview.endTmNumber,
		                binaryTransferType: this.preview.binaryTransferType
		            };
					var complete = false;
					var location = null;
					
					var poll = function(outer) {
						$timeout(function() {
				            if (location) {
				            	$http.get(location).success(function(data) {
				            		
				            		outer.stacked=[];
				            		
				            		var x = data;
				            		outer.serviceResponse = x;
				            		outer.complete = x.finished;
				            		var completed = waiting = failed = running = extracted = 0;
				            		
				            		completed = x.totalCompleted;
				            		waiting = x.totalWaiting;
				            		failed = x.totalFailed;
				            		running = x.totalRunning;
				            		extracted = x.totalExtracted;
				            		
				            		completed = completed == 0? 0 : (completed/x.total)*100;
				            		waiting = waiting == 0? 0 : (waiting/x.total)*100;
				            		failed = failed == 0? 0 : (failed/x.total)*100;
				            		running = running == 0? 0 : (running/x.total)*100;
				            		extracted = extracted == 0? 0 : (extracted/x.total)*100;
				            		
				            		outer.stacked.push({value: completed,type:'success'});
				            		outer.stacked.push({value: waiting,type:'warning'});
				            		outer.stacked.push({value: failed,type:'danger'});
				            		outer.stacked.push({value: running,type:'info'});
				            		outer.stacked.push({value: extracted,type:'info'});
								});
				            }    
				            
				            if (!outer.complete) {
				            	poll(outer);
				            }
				        }, 120000);
				    };     
					
					$http.post(restUrl, data).success(function(data, status, headers) {
						location = headers('Location');
						poll(tmRangePreviewCtrl);
					});
				};
				
				this.extractBiblio = function() {
					this.clear();
					this.attemptedUpdate = true;
					var restUrl = 'biblio/range/';
					var data = {
						changeDesc: this.preview.changeDesc,
		                startIpId: this.preview.startTmNumber,
		                endIpId: this.preview.endTmNumber,
		                binaryTransferType: this.preview.binaryTransferType
		            };
					var complete = false;
					var location = null;
					
					var poll = function(outer) {
						$timeout(function() {
				            if (location) {
				            	$http.get(location).success(function(data) {
				            		
				            		outer.stacked=[];
				            		
				            		var x = data;
				            		outer.serviceResponse = x;
				            		outer.complete = x.finished;
				            		var completed = waiting = failed = running = extracted = 0;
				            		
				            		completed = x.totalCompleted;
				            		waiting = x.totalWaiting;
				            		failed = x.totalFailed;
				            		running = x.totalRunning;
				            		extracted = x.totalExtracted;
				            		
				            		completed = completed == 0? 0 : (completed/x.total)*100;
				            		waiting = waiting == 0? 0 : (waiting/x.total)*100;
				            		failed = failed == 0? 0 : (failed/x.total)*100;
				            		running = running == 0? 0 : (running/x.total)*100;
				            		extracted = extracted == 0? 0 : (extracted/x.total)*100;
				            		
				            		outer.stacked.push({value: completed,type:'success'});
				            		outer.stacked.push({value: waiting,type:'warning'});
				            		outer.stacked.push({value: failed,type:'danger'});
				            		outer.stacked.push({value: running,type:'info'});
				            		outer.stacked.push({value: extracted,type:'info'});
								});
				            }    
				            
				            if (!outer.complete) {
				            	poll(outer);
				            }
				        }, 120000);
				    };     
					
					$http.post(restUrl, data).success(function(data, status, headers) {
						location = headers('Location');
						poll(tmRangePreviewCtrl);
					});
				};
				
				this.extractBinaries = function() {
					this.clear();
					this.attemptedUpdate = true;
					var restUrl = 'biblio/range/binaries';
					var data = {
						changeDesc: this.preview.changeDesc,
		                startIpId: this.preview.startTmNumber,
		                endIpId: this.preview.endTmNumber,
		                binaryTransferType: this.preview.binaryTransferType
		            };
					var complete = false;
					var location = null;
					
					var poll = function(outer) {
						$timeout(function() {
				            if (location) {
				            	$http.get(location).success(function(data) {
				            		
				            		outer.stacked=[];
				            		
				            		var x = data;
				            		outer.serviceResponse = x;
				            		outer.complete = x.finished;
				            		var completed = waiting = failed = running = extracted = 0;
				            		
				            		completed = x.totalCompleted;
				            		waiting = x.totalWaiting;
				            		failed = x.totalFailed;
				            		running = x.totalRunning;
				            		extracted = x.totalExtracted;
				            		
				            		completed = completed == 0? 0 : (completed/x.total)*100;
				            		waiting = waiting == 0? 0 : (waiting/x.total)*100;
				            		failed = failed == 0? 0 : (failed/x.total)*100;
				            		running = running == 0? 0 : (running/x.total)*100;
				            		extracted = extracted == 0? 0 : (extracted/x.total)*100;
				            		
				            		outer.stacked.push({value: completed,type:'success'});
				            		outer.stacked.push({value: waiting,type:'warning'});
				            		outer.stacked.push({value: failed,type:'danger'});
				            		outer.stacked.push({value: running,type:'info'});
				            		outer.stacked.push({value: extracted,type:'info'});
								});
				            }    
				            
				            if (!outer.complete) {
				            	poll(outer);
				            }
				        }, 120000);
				    };     
					
					$http.post(restUrl, data).success(function(data, status, headers) {
						location = headers('Location');
						poll(tmRangePreviewCtrl);
					});
				};
				
				this.showResponse = function() {
					return this.attemptedUpdate && this.serviceResponse != null && this.serviceResponse != {};
				};
				
				this.hideResponse = function() {
					return !this.attemptedUpdate;
				};
			}],
			controllerAs: 'tmRangeCtrl'
		};
	});
})();
