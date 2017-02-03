
(function() {
	var app = angular.module('MyApp', ['ngAnimate', 'ui.bootstrap']);

	app.controller('tmDeltaCtrl', function($scope, $location, $http) {
		$scope.getDeltas = function(restUrl) {
			$http.get(restUrl).success(function(data) {
				$scope.deltaServiceResponse = data;
			});
		};
		
		$scope.clear = function() {
			preview.statusFilter = "";
			preview.tmNumber = "";
		}
		
		$scope.retry = function(changeSetId) {
			var restUrl = 'deltas/retry';
			var data = {
				changeDesc: changeSetId
            };
				
			$http.post(restUrl, data).success(function(data, status, headers) {
				alert(data);
			});
		}
		
		$scope.retryNew = function(changeSetId) {
			var restUrl = 'deltas/retryNew';
			var data = {
				changeDesc: changeSetId
            };
				
			$http.post(restUrl, data).success(function(data, status, headers) {
				alert(data);
			});
		}
	});
	
	app.controller('tmRangesCtrl', function($scope, $location, $http) {
		$scope.getRanges = function(restUrl) {
			$http.get(restUrl).success(function(data) {
				$scope.rangeServiceResponse = data;
			});
		};
		
		$scope.clear = function() {
			preview.statusFilter = "";
			preview.changeSetDesc = "";
		}
		
		$scope.retry = function() {
			var restUrl = 'biblio/range/retry';
			var data = {
				changeDesc: this.preview.changeSetDesc
            };
				
			$http.post(restUrl, data).success(function(data, status, headers) {
				location = headers('Location');
			});
		}
	});
	
	app.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, pextract)
	{
		$scope.extract = pextract;
		$scope.modifiedString =  JSON.stringify(JSON.parse(pextract.ipaJson.split('\r\n').join("")), undefined, 2);
	});
	
	app.controller('changeSetCtrl', function($scope, $http, $uibModal) {
		$scope.getChanges = function(restUrl) {
			$http.get(restUrl).success(function(data) {
				$scope.deltaServiceResponse = data;
			});
		};
		
		// MODAL WINDOW
	    $scope.open = function (_extract) {

	        var modalInstance = $uibModal.open({
	          controller: "ModalInstanceCtrl",
	          templateUrl: 'myModalContent.html',
	          size: 'lg',
	          resolve: {
	            	pextract: function() {
	                    return _extract;
	                }
	          }
	        });

	    };
	});
	
	app.controller('verifyInternalCtrl', function($scope, $http, $uibModal) {
			
		$scope.getCounts= function() {
			var restUrl = 'ccheckInternal/counts';
			$http.get(restUrl).success(function(data) {
				$scope.counts = data;
			});
		};
		
		$scope.getDetails= function() {
			var restUrl = 'ccheckInternal/detailCounts';
			$http.get(restUrl).success(function(data) {
				$scope.detailCounts = data;
			});
		};
		
		$scope.getTimes= function() {
			var restUrl = 'ccheckInternal/times';
			$http.get(restUrl).success(function(data) {
				$scope.times = data;
			});
		};
		
		$scope.getDiff= function(restUrl) {
			$http.get(restUrl).success(function(data) {
				$scope.detail = data;
			});
		};
		
		// MODAL WINDOW
	    $scope.open = function (_extract) {

	        var modalInstance = $uibModal.open({
	          controller: "ModalInstanceCtrl",
	          templateUrl: 'myModalContent.html',
	          size: 'lg',
	          resolve: {
	            	pextract: function() {
	                    return _extract;
	                }
	          }
	        });
	    };
	    
	    $scope.init=function() {
	    	$scope.getCounts();
	    	$scope.getDetails();
	    	$scope.getDiff('ccheckInternal/detailDiff?sort=tmNumber,desc');
	    	$scope.getTimes();
	    };
	    
	    $scope.fixOPI = function() {
			$http.post('ccheckInternal', null);
		};
	    
	    $scope.fixNotOPI = function() {
			$http.post('ccheckInternal/notOPI', {});
		};
		
		$scope.removeOutOfSync = function() {
			$http.post('ccheckInternal/removeOutOfSync', {});
		};
		
		
	});
	
	app.controller('verifyPublicCtrl', function($scope, $http, $uibModal) {
		
		$scope.getCounts= function() {
			var restUrl = 'ccheckPublic/counts';
			$http.get(restUrl).success(function(data) {
				$scope.counts = data;
			});
		};
		
		$scope.getDetails= function() {
			var restUrl = 'ccheckPublic/detailCounts';
			$http.get(restUrl).success(function(data) {
				$scope.detailCounts = data;
			});
		};
		
		$scope.getTimes= function() {
			var restUrl = 'ccheckPublic/times';
			$http.get(restUrl).success(function(data) {
				$scope.times = data;
			});
		};
		
		$scope.getDiff= function(restUrl) {
			$http.get(restUrl).success(function(data) {
				$scope.detail = data;
			});
		};
		
		// MODAL WINDOW
	    $scope.open = function (_extract) {

	        var modalInstance = $uibModal.open({
	          controller: "ModalInstanceCtrl",
	          templateUrl: 'myModalContent.html',
	          size: 'lg',
	          resolve: {
	            	pextract: function() {
	                    return _extract;
	                }
	          }
	        });
	    };
	    
	    $scope.init=function() {
	    	$scope.getCounts();
	    	$scope.getDetails();
	    	$scope.getDiff('ccheckPublic/detailDiff?sort=tmNumber,desc');
	    	$scope.getTimes();
	    };
	    
	    $scope.fixOPI = function() {
			$http.post('ccheckPublic', null);
		};
	    
	    $scope.fixNotOPI = function() {
			$http.post('ccheckPublic/notOPI', {});
		};
		
		$scope.removeOutOfSync = function() {
			$http.post('ccheckPublic/removeOutOfSync', {});
		};
		
		
	});
	
	app.controller('runCtrl', function($scope, $location, $http) {
		$scope.runCCheckUpdate = function() {
			$http.post('run/cCheckUpdate', {});
		};
		$scope.runHashRebuild = function() {
			$http.post('run/hashRebuild', {});
		};
		$scope.runHashRebuildNotOPI = function() {
			$http.post('run/hashRebuildNotOPI', {});
		};
		$scope.runDbCleanup = function() {
			$http.post('run/dbCleanup', {});
		};
		$scope.retryAgedIncompleteChangeSets = function() {
			$http.post('run/retryAgedIncompleteChangeSets', {});
		};
		$scope.publishLastDetails = function() {
			$http.post('run/publishLastDetails', {});
		};
	});
	
	app.directive('selectOnClick', function () {
	    // Linker function
	    return function (scope, element, attrs) {
	      element.bind('click', function () {
	        this.select();
	      });
	    };
	  });
	
	app.directive('tmrangeForm', function() {
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
