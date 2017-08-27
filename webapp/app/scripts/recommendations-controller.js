'use strict';

myApp.controller('RecommendationsController', ['$scope', function ($scope) {

    $scope.recommendations = {};
    $scope.recommendations.newRecommendation = {};
    $scope.recommendations.list = [];

    this.$onInit = function () {
        $scope.getRecommendationsList();
    };

    $scope.getRecommendationsList = function (){
        $.ajax({
            type: GET,
            url: RECOMMENDS,
            async: false,
            success: function (result) {
                logger.info("recommendations list success");
            },
            error: function (request, msg, error) {
                logger.error("recommendations list fail");
            }
        }).done(function (data) {
            logger.info(data._embedded.recommendations);
            $scope.recommendations.list = data._embedded.recommendations;
        })
    };

    $scope.addRecommendation = function () {
        if ($scope.addRecommendationForm.recommendation.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: POST,
            url: RECOMMENDS,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.recommendations.newRecommendation),
            success: function (result) {
                logger.info("recommendation save success");
            },
            error: function (request, msg, error) {
                logger.error("recommendation save fail");
            }
        });
        $scope.recommendations.newRecommendation = {};
        $scope.nav.recommendationsTabSelected = 1;
        $scope.getRecommendationsList();
    };

    $scope.updateRecommendation = function (url) {
        if ($scope.updateRecommendationForm.recommendation.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: PATCH,
            url: url,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.recommendations.currentRecommendation),
            success: function (result) {
                logger.info("diagnosis update success");
            },
            error: function (request, msg, error) {
                logger.error("diagnosis update fail");
            }
        });
        $scope.recommendations.currentRecommendation = {};
        $scope.nav.recommendationsTabSelected = 1;
        $scope.getRecommendationsList();
    };

}]);