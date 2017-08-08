var app = angular.module("clientApp", []);


app.controller('surveyListCtrl', function ($http) {
    console.log("list");
    $http.get("/api/surveys").then(function (response) {
        $scope.names = response.data._embedded.surveys;
    });
});

app.controller("NavigationController", ['$rootScope', function ($rootScope) {
    $rootScope.surveysTabSelected = 1;
    $rootScope.targetSurveyId = -1;
}]);


app.controller('SurveyController', function ($scope, $http) {
    (function init() {
        console.log("init");
    })();

    var clearSurvey = function () {
        console.log("clear");
        $scope.survey = {};
        $scope.child = {};

        $scope.selectedDiagnoses = [];
        $scope.selectedDisorders = [];
        $scope.selectedPrograms = [];
        $scope.selectedRecommendations = [];
    };

    $scope.initEmptySurvey = function () {
        clearSurvey();
    };

    $scope.initSurvey = function (surveyUrl) {
        console.log("TARGET=" + surveyUrl);
        if (surveyUrl === -1) {
            $scope.initEmptySurvey();
            return;
        }
        $http.get(surveyUrl).then(function (response) {
            console.log("getting survey " + surveyUrl);
            $scope.survey = response.data;
            $scope.survey.surveyDate = new Date(response.data.surveyDate);
            var childUrl = response.data._links.child.href;
            var diagnosesUrl = response.data._links.diagnoses.href;
            var disordersUrl = response.data._links.disorders.href;
            var programsUrl = response.data._links.eduPrograms.href;
            var recommendsUrl = response.data._links.recommends.href;

            $http.get(childUrl).then(function (response) {
                $scope.child = response.data;
                $scope.child.birthDate = new Date(response.data.birthDate);
            });

            $http.get(diagnosesUrl).then(function (response) {
                $scope.selectedDiagnoses = response.data._embedded.diagnoses;
            });

            $http.get(disordersUrl).then(function (response) {
                $scope.selectedDisorders = response.data._embedded.disorders;
            });

            $http.get(programsUrl).then(function (response) {
                $scope.selectedPrograms = response.data._embedded.educationPrograms;
            });

            $http.get(recommendsUrl).then(function (response) {
                $scope.selectedRecommendations = response.data._embedded.recommendations;
            });
        });
    };

    $scope.getDisordersList = function () {
        $http.get("/api/disorders").then(function (response) {
            var disordersList = response.data._embedded.disorders;
            var disorders = [];
            for (var key in disordersList) {
                var dis = {};
                dis['disorder'] = disordersList[key].disorder;
                dis['href'] = disordersList[key]["_links"]["disorder"]['href'];
                disorders.push(dis);
            }
            ;
            $scope.disorders = disordersList;

        });
    };

    $scope.getDiagnosesList = function () {
        $http.get("/api/diagnoses").then(function (response) {
            var diagnosesList = response.data._embedded.diagnoses;
            var diagnoses = [];
            for (var key in diagnosesList) {
                var dis = {};
                dis['diagnosis'] = diagnosesList[key].diagnosis;
                dis['href'] = diagnosesList[key]["_links"]["diagnosis"]['href'];
                diagnoses.push(dis);
            }
            ;
            $scope.diagnoses = diagnosesList;
        });
    }

    $scope.getEducationProgramsList = function () {
        $http.get("/api/educationPrograms").then(function (response) {
            var educationProgramsList = response.data._embedded.educationPrograms;
            var programs = [];
            for (var key in educationProgramsList) {
                var dis = {};
                dis['program'] = educationProgramsList[key].program;
                dis['href'] = educationProgramsList[key]["_links"]["educationProgram"]['href'];
                programs.push(dis);
            }
            ;
            $scope.programs = educationProgramsList;
        });
    };

    $scope.getRrecommendationsList = function () {
        $http.get("/api/recommendations").then(function (response) {
            var recommendationsList = response.data._embedded.recommendations;
            var recommendations = [];
            for (var key in recommendationsList) {
                var dis = {};
                dis['recommendation'] = recommendationsList[key].recommendation;
                dis['href'] = recommendationsList[key]["_links"]["recommendation"]['href'];
                recommendations.push(dis);
            }
            ;
            $scope.recommendations = recommendationsList;
        });
    };

    this.$onInit = function () {
        console.log("getting surveys ");
        $http.get("/api/surveys")
            .then(function (response) {
                $scope.surveys = response.data._embedded.surveys;
            })
            .catch(function (err) {
                console.log(err)
            });
    };


});


