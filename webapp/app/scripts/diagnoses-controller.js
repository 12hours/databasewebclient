'use strict';

myApp.controller('DiagnosesController', ['$scope', function ($scope) {

    $scope.diagnoses = {};
    $scope.diagnoses.totalPages = 0;
    $scope.diagnoses.currentPage = 0;
    $scope.diagnoses.newDiagnosis = {};

    this.$onInit = function () {
        $scope.getDiagnosesList();
    };

    $scope.getDiagnosesList = function (){
        $.ajax({
            type: GET,
            url: DIAGNOSES,
            async: false,
            success: function (result) {
                console.log("diagnoses list success");
            },
            error: function (request, msg, error) {
                console.log("diagnoses list fail");
            }
        }).done(function (data) {
            $scope.diagnoses.list = data._embedded.diagnoses;
        })
    };

    $scope.addDiagnosis = function () {
        $.ajax({
            type: POST,
            url: DIAGNOSES,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.diagnoses.newDiagnosis),
            success: function (result) {
                console.log("diagnosis save success");
            },
            error: function (request, msg, error) {
                console.log("diagnosis save fail");
            }
        });
    };

    $scope.showList = function(url) {
        $scope.suggestedSurveys = [];
        $.ajax({
            type: GET,
            url: url,
            async: false,
            success: function (result) {
                console.log("surveys list get success");
            },
            error: function (request, msg, error) {
                console.log("surveys list get fail");
            }
        }).done(function (data) {
            $scope.suggestedSurveys = data._embedded.surveys;
            var popupWindow = document.getElementById("surveys-list-popup-window");
            popupWindow.style.display = 'block';
        });


    }

}]);