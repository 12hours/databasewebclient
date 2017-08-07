'use strict';

var myApp = angular.module('myApp', []);

myApp.controller('MyCtrl', ['$http', '$scope', '$rootScope', function ($http, $scope, $rootScope) {

    var registerScope = null;
    $rootScope.surveysTabSelected = 1;
    $rootScope.targetSurveyId = -1;

    var isNewSurvey;

    this.$onInit = function () {
        var myVar;
        $rootScope.myVar = myVar;
        $.ajax({url: ('/api'), type: 'GET', async: false}).done(function (data) {
            $rootScope.myVar = 42;
        })
        console.log("myVar", $rootScope.myVar);

        $scope.getSurveysList();

    };

    console.log($scope.surveys);

    $scope.getSurveysList = function () {
        console.log("getting surveys ");
        $http.get("/api/surveys")
            .then(function (response) {
                $scope.surveys = response.data._embedded.surveys;
            })
            .catch(function (err) {
                console.log(err)
            });

        //register rootScope event
        registerScope = $rootScope.$on('someEvent', function (event) {
            console.log("fired");
        });
    }


    this.$onDestroy = function () {
        //unregister rootScope event by calling the return function
        registerScope();
    };

    $scope.initEmptySurvey = function () {
        isNewSurvey = true;

        console.log("clear");
        $scope.survey = {};
        $scope.child = {};

        $scope.selectedDiagnoses = [];
        $scope.selectedDisorders = [];
        $scope.selectedPrograms = [];
        $scope.selectedRecommendations = [];
    };

    $scope.initSurvey = function (surveyUrl) {
        console.log("TARGET=" + surveyUrl);
        if (surveyUrl === -1) {
            $scope.initEmptySurvey();
            return;
        }

        isNewSurvey = false;
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

    $scope.getDiagnosesList = function () {
        $http.get("/api/diagnoses").then(function (response) {
            var diagnosesList = response.data._embedded.diagnoses;
            $scope.diagnoses = diagnosesList;
        });
    }

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

    $scope.submit = function () {
        var childUrl;
        var surveyUrl;
        var methodType;
        console.log("is new = " + isNewSurvey);
        if (isNewSurvey === true) {
            // create
            methodType = 'POST';
            childUrl = "api/children";
        } else {
            // update
            methodType = 'PATCH';
            childUrl = $scope.child._links.child.href;

        }

        // child

        $.ajax({
                url: (childUrl),
                type: methodType,
                async: false,
                contentType: 'application/json',
                data: angular.toJson($scope.child),
                success: function (result) {
                    console.log("child update success");
                },
                error: function (request, msg, error) {
                    console.log("child update fail");
                }
            }
        ).done(function (data) {
            if (isNewSurvey) {
                    $scope.child = data;
            }
        });


        if (isNewSurvey === true) {
            console.log('actual child: ', $scope.child);
            // surveyUrl = $scope.child._links.surveys.href;
            surveyUrl = "/api/surveys";
        } else {
            surveyUrl = $scope.survey._links.self.href;
        }
        // survey
        // TODO: here we update childName field. Perhaps there are better way
        $scope.survey.childName = ($scope.child.familyName + " " + $scope.child.name + " " + $scope.child.patrName);
        $scope.survey.child = $scope.child._links.self.href;
        $.ajax({
            url: (surveyUrl),
            type: methodType,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.survey),
            // handle success
            success: function (result) {
                console.log("survey success");
            },
            // handle failure
            error: function (request, msg, error) {
                console.log("survey fail");
            }
        }).done(function (data) {
            if (isNewSurvey){
                surveyUrl = data._links.self.href;
            }
        });


//diagnoses
        var diagnosesArray = [];
        for (var key in $scope.selectedDiagnoses) {
            diagnosesArray.push($scope.selectedDiagnoses[key]._links.self.href);
        }
        ;
        var diagnosesString = diagnosesArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/diagnoses"),
            type: 'PUT',
            contentType: 'text/uri-list',
            data: diagnosesString,
            success: function (result) {
                // handle success
            },
            error: function (request, msg, error) {
                // handle failure
            }
        });

// disorders
        var disordersArray = [];
        for (var key in $scope.selectedDisorders) {
            disordersArray.push($scope.selectedDisorders[key]._links.self.href);
        }
        ;
        var disordersString = disordersArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/disorders"),
            type: 'PUT',
            contentType: 'text/uri-list',
            data: disordersString,
            success: function (result) {
                // handle success
            },
            error: function (request, msg, error) {
                // handle failure
            }
        });

// programs
        var programsArray = [];
        for (var key in $scope.selectedPrograms) {
            programsArray.push($scope.selectedPrograms[key]._links.self.href);
        }
        ;
        var programsString = programsArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/eduPrograms"),
            type: 'PUT',
            contentType: 'text/uri-list',
            data: programsString,
            success: function (result) {
                // handle success
            },
            error: function (request, msg, error) {
                // handle failure
            }
        });

// recommendations
        var recommendsArray = [];
        for (var key in $scope.selectedRecommendations) {
            recommendsArray.push($scope.selectedRecommendations[key]._links.self.href);
        }
        ;
        var recommendsString = recommendsArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/recommends"),
            type: 'PUT',
            contentType: 'text/uri-list',
            data: recommendsString,
            success: function (result) {
                // handle success
            },
            error: function (request, msg, error) {
                // handle failure
            }
        });

// TODO: optimize
        document.getElementById("saved-window-message").textContent = "Сохранено";
        var savedWindow = document.getElementById("saved-window");
        savedWindow.style.display = 'block';
// When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == savedWindow) {
                savedWindow.style.display = "none";
            }
        }

//     console.log(diagnosesString);
//     $http.post(
//         (surveyUrl+"/diagnoses"),
//         (diagnosesString + "\r\n"),
//         {
//             headers: {"Content-Type": "text/uri-list"}
//         }
//     ).then(
//         function(response){
//             // success callback
//             console.log("ok "+surveyUrl+"/diagnoses");
//             console.log(response.status);
//             console.log(response.data);
//         },
//         function(response){
//             // failure callback
//             console.log("error");
//         }
//     );
    }
}])
;