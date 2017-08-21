'use strict';

myApp.controller('DisordersController', ['$scope', function ($scope) {

    $scope.disorders = {};
    $scope.disorders.newDisorder = {};
    $scope.disorders.list = [];

    this.$onInit = function () {
        $scope.getDisordersList();
    };

    $scope.getDisordersList = function (){
        $.ajax({
            type: GET,
            url: DISORDERS,
            async: false,
            success: function (result) {
                console.log("disorders list success");
            },
            error: function (request, msg, error) {
                console.log("disorders list fail");
            }
        }).done(function (data) {
            console.log(data._embedded.disorders);
            $scope.disorders.list = data._embedded.disorders;
        })
    };

    $scope.addDisorder = function () {
        if ($scope.addDisorderForm.disorder.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: POST,
            url: DISORDERS,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.disorders.newDisorder),
            success: function (result) {
                console.log("diagnosis save success");
            },
            error: function (request, msg, error) {
                console.log("diagnosis save fail");
            }
        });
        $scope.disorders.newDisorder = {};
        $scope.nav.disordersTabSelected = 1;
        $scope.getDisordersList();
    };

    $scope.updateDisorder = function (url) {
        if ($scope.updateDisorderForm.disorder.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: PATCH,
            url: url,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.disorders.currentDisorder),
            success: function (result) {
                console.log("diagnosis update success");
            },
            error: function (request, msg, error) {
                console.log("diagnosis update fail");
            }
        });
        $scope.disorders.currentDisorder = {};
        $scope.nav.disordersTabSelected = 1;
        $scope.getDisordersList();
    };


    $scope.editDisorder = function(diagnosis) {
        var popupWindow = document.getElementById("surveys-list-popup-window");
        popupWindow.style.display = 'block';
    }

}]);