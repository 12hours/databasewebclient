'use strict';

myApp.controller('ChildrenController', ['$scope', function ($scope) {

    $scope.children = {};
    $scope.children.currentChild = {};
    $scope.children.totalPages = 0;
    $scope.children.currentPage = 0;

    this.$onInit = function () {
        $scope.getChildrenList(0);
    };

    $scope.getChildrenList = function(page){
        $scope.getChildrenListByUrl(CHILDREN + "?page=" + page);
    };

    $scope.getChildrenListByUrl = function (url){
        $.ajax({
            type: GET,
            url: url,
            async: false,
            success: function (result) {
                console.log("children list success");
            },
            error: function (request, msg, error) {
                console.log("children list fail");
            }
        }).done(function (data) {
            $scope.children.list = data._embedded.children;
            $scope.children.totalPages = data.page.totalPages;
            $scope.children.currentPage = data.page.number;
            if ($scope.children.totalPages > 1) {
                $scope.children.nextPage = (data._links.next == null) ?
                    data._links.last.href : data._links.next.href;
                $scope.children.prevPage = (data._links.prev == null) ?
                    data._links.first.href : data._links.prev.href;
            } else {
                $scope.children.nextPage = url;
                $scope.children.prevPage = url;
            }
        })
    };

    $scope.initChild = function(url){
        $.ajax({
            type: GET,
            url: url,
            async: false,
            success: function (result) {
                logger.info("children list success");
            },
            error: function (request, msg, error) {
                logger.error("children list fail");
            }
        }).done(function (data) {
            $scope.children.currentChild = data;
            $scope.children.currentChild.birthDate = new Date(data.birthDate);
        });

    };


    $scope.updateChild = function (url) {
        $.ajax({
            type: PATCH,
            url: url,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.children.currentChild),
            success: function (result) {
                logger.info("child update success");
            },
            error: function (request, msg, error) {
                logger.error("child update fail");
            }
        });
        $scope.children.currentChild = {};
        $scope.nav.childrenTabSelected = 1;
        $scope.getChildrenList($scope.children.currentPage);
    };


    $scope.getChildSurveys = function(childUrl){

    };


}]);