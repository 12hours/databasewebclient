'use strict';

myApp.controller('ChildController', ['$scope', function ($scope) {

    $scope.suggestedChildren = [];

    $scope.findChild = function (familyName){
        $.ajax({
            type: GET,
            url: FIND_CHILD + 'familyName=' + familyName,
            async: false,
            success: function (result) {
                console.log("child search success");
            },
            error: function (request, msg, error) {
                console.log("child search fail");
            }
        }).done(function (data) {
            $scope.suggestedChildren = data._embedded.children;
            console.log($scope.suggestedChildren);
            if ($scope.suggestedChildren != null && $scope.suggestedChildren.length > 0) {
                var popupWindow = document.getElementById("children-list-popup-window");
                popupWindow.style.display = 'block';
            }
        })
    };

    $scope.initChild = function(rawChild){
        var child = {};
        child = rawChild;
        child.birthDate = new Date(rawChild.birthDate);
        return child;
    }

}]);