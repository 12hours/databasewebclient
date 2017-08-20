'use strict';

myApp.controller('DiagnosesController', ['$scope', function ($scope) {

    $scope.diagnoses = {};
    $scope.diagnoses.newDiagnosis = {};
    $scope.diagnoses.list = [];

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
            console.log(data._embedded.diagnoses);
            $scope.diagnoses.list = data._embedded.diagnoses;
        })
    };

    $scope.addDiagnosis = function () {
        if ($scope.addDiagnosisForm.diagnosis.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
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
        $scope.getDiagnosesList();
        $scope.diagnoses.newDiagnosis = {};
        $scope.nav.diagnosesTabSelected = 1;
    };

    $scope.updateDiagnosis = function (url) {
        if ($scope.updateDiagnosisForm.diagnosis.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: PATCH,
            url: url,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.diagnoses.currentDiagnosis),
            success: function (result) {
                console.log("diagnosis update success");
            },
            error: function (request, msg, error) {
                console.log("diagnosis update fail");
            }
        });
        $scope.getDiagnosesList();
        $scope.diagnoses.currentDiagnosis = {};
        $scope.nav.diagnosesTabSelected = 1;
    };


    $scope.editDiagnosis = function(diagnosis) {
        var popupWindow = document.getElementById("surveys-list-popup-window");
        popupWindow.style.display = 'block';
    }

}]);