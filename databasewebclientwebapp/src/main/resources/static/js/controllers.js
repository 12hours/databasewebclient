var app = angular.module("clientApp", []);

app.controller('surveyListCtrl', function($scope, $http, $rootScope, $window) {
    $http.get("/api/getall").then(function(response) {$scope.names = response.data;});
});

app.controller('childWindowCtrl', ['$rootScope', '$window', '$timeout', function($rootScope, $window, $timeout){
    var scroll;
    $rootScope.ifChildWindowIsOpen = false;
    $rootScope.openChildWindow = function () {
        scroll = $window.pageYOffset;
        $rootScope.ifChildWindowIsOpen = true;
    }
    $rootScope.closeChildWindow = function () {
        $rootScope.ifChildWindowIsOpen = false;
        setTimeout(function () {
            $window.scrollTo(0, scroll);
        }, 100);
    }
}]);