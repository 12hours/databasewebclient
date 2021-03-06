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
                logger.info("diagnoses list success");
            },
            error: function (request, msg, error) {
                logger.error("diagnoses list fail");
            }
        }).done(function (data) {
            logger.info(data._embedded.diagnoses);
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
                logger.info("diagnosis save success");
            },
            error: function (request, msg, error) {
                logger.error("diagnosis save fail");
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
                logger.info("diagnosis update success");
            },
            error: function (request, msg, error) {
                logger.error("diagnosis update fail");
            }
        });
        $scope.getDiagnosesList();
        $scope.diagnoses.currentDiagnosis = {};
        $scope.nav.diagnosesTabSelected = 1;
    };

    $scope.deleteDiagnosis = function (url) {
        if (confirm('Вы действительно хотите удалить запись?')) {
            $.ajax({
                type: DELETE,
                url: url,
                async: false,
                success: function (result) {
                                logger.info("diagnosis delete success");
                            },
                error: function (request, msg, error) {
                    logger.error("diagnosis delete fail");
                }
            });
            $scope.getDiagnosesList();
            $scope.diagnoses.currentDiagnosis = {};
            $scope.nav.diagnosesTabSelected = 1;
        } else {
            return;
        }
    }


    $scope.editDiagnosis = function(diagnosis) {
        var popupWindow = document.getElementById("surveys-list-popup-window");
        popupWindow.style.display = 'block';
    }

}]);