'use strict';

myApp.controller('NavigationController', ['$rootScope','$scope', function ($rootScope, $scope) {
    console.log("init nav");
    $scope.nav = {};
    $scope.nav.surveysTabSelected = 1;
    $scope.nav.mainTabChosen = 1;
    $scope.nav.targetSurveyId = -1;
    $scope.nav.childrenTabSelected = 1;



}]);