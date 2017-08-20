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
                console.log("children list success");
            },
            error: function (request, msg, error) {
                console.log("children list fail");
            }
        }).done(function (data) {
            $scope.children.currentChild.child = data;
            $scope.children.currentChild.child.birthDate = new Date(data.birthDate);
        });

    };

    $scope.getChildSurveys = function(childUrl){

    };


}]);