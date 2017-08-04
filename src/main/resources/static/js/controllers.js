var app = angular.module("clientApp", []);

app.controller('surveyListCtrl', function($scope, $http, $rootScope, $window) {
    $http.get("/surveys").then(function(response) {$scope.names = response.data._embedded.surveys;});
});

app.controller('tabCtrl', function ($scope) {
    $scope.tabChosen = 1;
});

app.controller('addSurveyController', ['$scope', '$http', function($scope, $http){
    $scope.child = {};
    $scope.selectedDiagnoses = [];
    $scope.selectedDisorders = [];
    $scope.selectedPrograms = [];
    $scope.selectedRecommendations = [];

    $scope.name = $scope.child.name;

    $scope.lg = function () {
        console.log($scope.selectedDisorders);
        console.log($scope.selectedDiagnoses);
        console.log($scope.selectedPrograms);
        console.log($scope.selectedRecommendations);
    }

    $scope.getDisordersList = function () {
        $http.get("/disorders").then(function(response) {
            disordersList = response.data._embedded.disorders;
            var disorders = [];
            for (var key in disordersList){
                var dis = {};
                dis['disorder'] = disordersList[key].disorder;
                dis['href'] = disordersList[key]["_links"]["disorder"]['href'];
                disorders.push(dis);
            };
            $scope.disorders = disorders;
        });
    }

    $scope.getDiagnosesList = function () {
        $http.get("/diagnoses").then(function(response) {
            diagnosesList = response.data._embedded.diagnoses;
            var diagnoses = [];
            for (var key in diagnosesList){
                var dis = {};
                dis['diagnosis'] = diagnosesList[key].diagnosis;
                dis['href'] = diagnosesList[key]["_links"]["diagnosis"]['href'];
                diagnoses.push(dis);
            };
            $scope.diagnoses = diagnoses;
            console.log(diagnoses);
        });
    }

    $scope.getEducationProgramsList = function () {
        $http.get("/educationPrograms").then(function(response) {
            educationProgramsList = response.data._embedded.educationPrograms;
            var programs = [];
            for (var key in educationProgramsList){
                var dis = {};
                dis['program'] = educationProgramsList[key].program;
                dis['href'] = educationProgramsList[key]["_links"]["educationProgram"]['href'];
                programs.push(dis);
            };
            $scope.programs = programs;
            console.log(programs);
        });
    }

    $scope.getRrecommendationsList = function () {
        $http.get("/recommendations").then(function(response) {
            recommendationsList = response.data._embedded.recommendations;
            var recommendations = [];
            for (var key in recommendationsList){
                var dis = {};
                dis['recommendation'] = recommendationsList[key].recommendation;
                dis['href'] = recommendationsList[key]["_links"]["recommendation"]['href'];
                recommendations.push(dis);
            };
            $scope.recommendations = recommendations;
            console.log(recommendations);
        });
    }
}]);

