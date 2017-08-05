'use strict';

var app = angular.module("clientApp", []);

app.controller('surveyListCtrl', function($http) {
    $http.get("/api/surveys").then(function(response) {$scope.names = response.data._embedded.surveys;});
});

app.controller('SurveyController', ['$scope', '$http', function($scope, $http){
    $scope.child = {};
    $scope.child.diagnoses = [];

    $scope.selectedDiagnoses = [];
    $scope.selectedDisorders = [];
    $scope.selectedPrograms = [];
    $scope.selectedRecommendations = [];

    $scope.name = $scope.child.name;
    
    var initEmpty = function(){
        $scope.selectedDiagnoses = [];
        $scope.selectedDisorders = [];
        $scope.selectedPrograms = [];
        $scope.selectedRecommendations = [];
    };

    var init = function(){

    };

    $scope.getDisordersList = function () {
        $http.get("/api/disorders").then(function(response) {
            var disordersList = response.data._embedded.disorders;
            var disorders = [];
            for (var key in disordersList){ var dis = {};
                dis['disorder'] = disordersList[key].disorder;
                dis['href'] = disordersList[key]["_links"]["disorder"]['href'];
                disorders.push(dis);
            };
            $scope.disorders = disorders;
            
        });
    }

    $scope.getDiagnosesList = function () {
        $http.get("/api/diagnoses").then(function(response) {
            var diagnosesList = response.data._embedded.diagnoses;
            var diagnoses = [];
            for (var key in diagnosesList){
                var dis = {};
                dis['diagnosis'] = diagnosesList[key].diagnosis;
                dis['href'] = diagnosesList[key]["_links"]["diagnosis"]['href'];
                diagnoses.push(dis);
            };
            $scope.diagnoses = diagnoses;
            $scope.selectedDiagnoses[0] = diagnoses[1];
        });
    }

    $scope.getEducationProgramsList = function () {
        $http.get("/api/educationPrograms").then(function(response) {
            var educationProgramsList = response.data._embedded.educationPrograms;
            var programs = [];
            for (var key in educationProgramsList){
                var dis = {};
                dis['program'] = educationProgramsList[key].program;
                dis['href'] = educationProgramsList[key]["_links"]["educationProgram"]['href'];
                programs.push(dis);
            };
            $scope.programs = programs;
        });
    }

    $scope.getRrecommendationsList = function () {
        $http.get("/api/recommendations").then(function(response) {
            var recommendationsList = response.data._embedded.recommendations;
            var recommendations = [];
            for (var key in recommendationsList){
                var dis = {};
                dis['recommendation'] = recommendationsList[key].recommendation;
                dis['href'] = recommendationsList[key]["_links"]["recommendation"]['href'];
                recommendations.push(dis);
            };
            $scope.recommendations = recommendations;
        });
    }
}]);

