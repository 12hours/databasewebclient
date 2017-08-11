'use strict';

myApp.controller('SurveyController', ['$http', '$scope', '$rootScope', function ($http, $scope, $rootScope) {

    var isNewSurvey;

    this.$onInit = function () {
        $scope.surveys = {};
        $scope.surveys.list = [];
        $scope.surveys.size = 0;
        $scope.surveys.currentPage = 0;
        $scope.surveys.currentSurvey = {};
        $scope.getSurveysList(0);
    };

    $scope.getSurveysList = function (page) {
        var url = SURVEYS + "?page=" + page;
        $scope.getSurveysListByUrl(url);
    };

    $scope.getSurveysListByName = function (name) {
        var url = SURVEYS + "/search/byChildName?childName=" + name;
        $scope.getSurveysListByUrl(url);
    };

    $scope.getSurveysListByUrl = function (url) {
        console.log("getting page with url ", url);
        $http.get(url)
            .then(function (response) {
                $scope.surveys.list = response.data._embedded.surveys;
                $scope.surveys.totalPages = response.data.page.totalPages;
                if ($scope.surveys.totalPages > 1) {
                    $scope.surveys.currentPage = response.data.page.number;
                    $scope.surveys.nextPage = (response.data._links.next == null) ?
                        response.data._links.last.href : response.data._links.next.href;
                    $scope.surveys.prevPage = (response.data._links.prev == null) ?
                        response.data._links.first.href : response.data._links.prev.href;
                }
            })
            .catch(function (err) {
                console.log(err)
            });
    };

    this.$onDestroy = function () {
    };

    $scope.initEmptySurvey = function () {
        isNewSurvey = true;
        console.log("clear");
        $scope.surveys.currentSurvey.survey = {};
        $scope.surveys.currentSurvey.child = {};

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
            $scope.surveys.currentSurvey.survey = angular.fromJson(response.data);
            $scope.surveys.currentSurvey.survey.surveyDate = new Date(response.data.surveyDate);
            // $scope.survey.child.birthDate = new Date(response.data.child.birthDate);
            var childUrl = response.data._links.child.href;
            var diagnosesUrl = response.data._links.diagnoses.href;
            var disordersUrl = response.data._links.disorders.href;
            var programsUrl = response.data._links.eduPrograms.href;
            var recommendsUrl = response.data._links.recommends.href;


            $http.get(childUrl).then(function (response) {
                $scope.surveys.currentSurvey.child = response.data;
                $scope.surveys.currentSurvey.child.birthDate = new Date(response.data.birthDate);
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
        $http.get(DIAGNOSES).then(function (response) {
            var diagnosesList = response.data._embedded.diagnoses;
            $scope.diagnoses = diagnosesList;
        });
    }

    $scope.getDisordersList = function () {
        $http.get(DISORDERS).then(function (response) {
            var disordersList = response.data._embedded.disorders;
            $scope.disorders = disordersList;

        });
    };

    $scope.getEducationProgramsList = function () {
        $http.get(EDU_PROGRAMS).then(function (response) {
            var educationProgramsList = response.data._embedded.educationPrograms;
            $scope.programs = educationProgramsList;
        });
    };

    $scope.getRecommendationsList = function () {
        $http.get(RECOMMENDS).then(function (response) {
            var recommendationsList = response.data._embedded.recommendations;
            $scope.recommendations = recommendationsList;
        });
    };

    $scope.submit = function () {
        $scope.surveys.currentSurvey.survey.childName = ($scope.surveys.currentSurvey.child.familyName + " " + $scope.surveys.currentSurvey.child.name + " " + $scope.surveys.currentSurvey.child.patrName);
        var childUrl;
        var surveyUrl;
        var methodType;
        console.log("is new = " + isNewSurvey);
        if (isNewSurvey === true) {
            // create

        } else {
        }
        // update

        try {
            childUrl = $scope.surveys.currentSurvey.child._links.self.href;
            methodType = PATCH;
        } catch (err) {
            childUrl = CHILDREN;
            methodType = POST;
        }

        // child

        $.ajax({
                url: (childUrl),
                type: methodType,
                async: false,
                contentType: 'application/json',
                data: angular.toJson($scope.surveys.currentSurvey.child),
                success: function (result) {
                    console.log("child update success");
                },
                error: function (request, msg, error) {
                    console.log("child update fail");
                }
            }
        ).done(function (data) {
            $scope.surveys.currentSurvey.survey.child = data._links.self.href;
        });


        if (isNewSurvey === true) {
            console.log('actual child: ', $scope.surveys.currentSurvey.child);
            // surveyUrl = $scope.survey.child._links.surveys.href;
            surveyUrl = SURVEYS;
            methodType = POST;
        } else {
            surveyUrl = $scope.surveys.currentSurvey.survey._links.self.href;
        }

        // survey
        // TODO: here we update childName field. Perhaps there are better way

        $.ajax({
            url: (surveyUrl),
            type: methodType,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.surveys.currentSurvey.survey),
            // handle success
            success: function (result) {
                console.log("survey success");
            },
            // handle failure
            error: function (request, msg, error) {
                console.log("survey fail");
            }
        }).done(function (data) {
            surveyUrl = data._links.self.href;
            console.log("URL ", surveyUrl);
            if (isNewSurvey) {
            }
        });


//diagnoses
        var diagnosesArray = [];
        for (var key in $scope.selectedDiagnoses) {
            diagnosesArray.push($scope.selectedDiagnoses[key]._links.self.href);
        }
        var diagnosesString = diagnosesArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/diagnoses"),
            type: PUT,
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
        var disordersString = disordersArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/disorders"),
            type: PUT,
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
        var programsString = programsArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/eduPrograms"),
            type: PUT,
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
        var recommendsString = recommendsArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/recommends"),
            type: PUT,
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
        document.getElementById("popup-window-message").textContent = "Сохранено";
        var savedWindow = document.getElementById("popup-window");
        savedWindow.style.display = 'block';

    }
}])
;