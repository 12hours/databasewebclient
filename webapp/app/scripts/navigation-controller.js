'use strict';

myApp.controller('NavigationController', ['$rootScope','$scope', function ($rootScope, $scope) {
    $scope.nav = {};
    $scope.nav.surveysTabSelected = 1;
    $scope.nav.mainTabChosen = 1;
    $scope.nav.childrenTabSelected = 1;
    $scope.nav.diagnosesTabSelected = 1;
    $scope.nav.disordersTabSelected = 1;
    $scope.nav.programsTabSelected = 1;
    $scope.nav.recommendationsTabSelected = 1;

}]);