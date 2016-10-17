//(function(){
//	'use strict';
//
//
//// Register `phoneList` component, along with its associated controller and template
//angular.module('phoneList').component('phoneList', {
//    controller: ['$http', function($http) {
//      var self = this;
//      self.orderProp = 'age';
//      templateUrl: '../jsp/customer/announces.html',
//      $http.get('../../announce/getAnnounce').then(function(response) {
//        self.announces = response.object;
//      });
//    }]
//  });
//})();
angular.module('announce', []).controller('AnnounceListController', ['$http', '$scope', function($http, $scope) {
		$http.get('../../test/announce/getAnnounce').
				then(function(response) {
					$scope.announces = response.data.object;
				});
}
]).filter('getStringToDate', function() {  
	   return function(string) {
		   if (angular.isString(string)) {  
				return new Date(string.replace(/-/g, "/"));  
			}
	   };
});

//factory('utilConvertDateToString', ['$filter', function ($filter) {  
//	return {  
//		getDateToString: function (date, format) {  
//			if (angular.isDate(date) && angular.isString(format)) {  
//				return $filter('date')(date, format);  
//			}  
//		},  
//		getStringToDate: function (string) {  
//			if (angular.isString(string)) {  
//				return new Date(string.replace(/-/g, "/"));  
//			}  
//		}  
//	};  
//}]);