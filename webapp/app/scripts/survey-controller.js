'use strict';

myApp.controller('SurveyController', ['$http', '$scope', '$rootScope', function ($http, $scope, $rootScope) {

    var nameQuery = '';
    var startDateQuery = '';
    var endDateQuery = '';
    var startDate = '';
    var endDate = '';

    $('.date-start').datepicker({
        minViewMode: 0,
        format: 'yyyy-mm-dd',
        clearBtn: true,
        language: 'ru'
    });
    $('.date-start').on('changeDate', function() {
        $scope.$apply(function () {
            $scope.dateStart = $('.date-start').datepicker('getFormattedDate');
            if ($scope.dateStart === ''){
                startDateQuery = '';
                $scope.dateStart = null;
                $scope.getSurveysList(0);
            } else {
                startDateQuery = "&start=" + new Date($scope.dateStart).toISOString().split('T')[0];
                $scope.getSurveysList(0);
            }
        });
    });

    $('.date-end').datepicker({
        minViewMode: 0,
        format: 'yyyy-mm-dd',
        clearBtn: true,
        language: 'ru'
    });
    $('.date-end').on('changeDate', function() {
        $scope.$apply(function () {
            $scope.dateEnd = $('.date-end').datepicker('getFormattedDate');
            if ($scope.dateEnd === ''){
                endDateQuery = '';
                $scope.dateEnd = null;
                $scope.getSurveysList(0);
            } else {
                endDateQuery = "&end=" + new Date($scope.dateEnd).toISOString().split('T')[0];
                $scope.getSurveysList(0);
            }
        });
    });

    $scope.surveys = {};
    $scope.surveys.list = [];
    $scope.surveys.size = 0;
    $scope.surveys.currentPage = 0;
    $scope.surveys.currentSurvey = {};

    this.$onInit = function () {
        $scope.getSurveysList(0);
    };

    $scope.getSurveysListByName = function (name) {
        nameQuery = "&childName="+name;
        $scope.getSurveysList(0);
    };

    $scope.getSurveysList = function (page) {
        $scope.getSurveysListByUrl(SURVEYS_SEARCH + startDateQuery + endDateQuery + nameQuery + "&page=" + page);
    };

    $scope.getSurveysListByUrl = function (url) {
        console.log("getting page with url ", url);

        $.ajax({
            type: "GET",
            url: url,
            async: false,
            success: function (result) {
                console.log("child update success");
            },
            error: function (request, msg, error) {
                console.log("child update fail");
                console.log(error);
            }
        }).done(function (data) {
            $scope.surveys.list = data._embedded.surveys;
            $scope.surveys.totalPages = data.page.totalPages;
            $scope.surveys.currentPage = data.page.number;
            if ($scope.surveys.totalPages > 1) {
                $scope.surveys.nextPage = (data._links.next == null) ?
                    data._links.last.href : data._links.next.href;
                $scope.surveys.prevPage = (data._links.prev == null) ?
                    data._links.first.href : data._links.prev.href;
            } else {
                $scope.surveys.nextPage = url;
                $scope.surveys.prevPage = url;
            }
        });

        // $http.get(url)
        //     .then(function (response) {
        //         $scope.surveys.list = response.data._embedded.surveys;
        //         $scope.surveys.totalPages = response.data.page.totalPages;
        //         $scope.surveys.currentPage = response.data.page.number;
        //         if ($scope.surveys.totalPages > 1) {
        //             $scope.surveys.nextPage = (response.data._links.next == null) ?
        //                 response.data._links.last.href : response.data._links.next.href;
        //             $scope.surveys.prevPage = (response.data._links.prev == null) ?
        //                 response.data._links.first.href : response.data._links.prev.href;
        //         } else {
        //             $scope.surveys.nextPage = url;
        //             $scope.surveys.prevPage = url;
        //         }
        //     })
        //     .catch(function (err) {
        //         console.log(err)
        //     });
    };

    this.$onDestroy = function () {
    };

    $scope.initEmptySurvey = function () {
        console.log("clear");
        $scope.surveys.currentSurvey.survey = {};
        $scope.surveys.currentSurvey.child = {};

        $scope.surveys.currentSurvey.selectedDiagnoses = [];
        $scope.surveys.currentSurvey.selectedDisorders = [];
        $scope.surveys.currentSurvey.selectedPrograms = [];
        $scope.surveys.currentSurvey.selectedRecommendations = [];
    };

    $scope.initSurvey = function (surveyUrl) {
        console.log("TARGET=" + surveyUrl);
        if (surveyUrl === -1) {
            $scope.initEmptySurvey();
            return;
        }

        $.ajax({
            type: "GET",
            url: surveyUrl,
            async: false,
            success: function (result) {
                console.log("get survey success");
            },
            error: function (request, msg, error) {
                console.log("get survey fail");
                console.log(error);
            }
        }).done(function (data) {
            $scope.surveys.currentSurvey.survey = angular.fromJson(data);
            $scope.surveys.currentSurvey.survey.surveyDate = new Date(data.surveyDate);
            // $scope.survey.child.birthDate = new Date(response.data.child.birthDate);
            var childUrl = data._links.child.href;
            var diagnosesUrl = data._links.diagnoses.href;
            var disordersUrl = data._links.disorders.href;
            var programsUrl = data._links.educationPrograms.href;
            var recommendsUrl = data._links.recommendations.href;


            $.ajax({
                type: "GET",
                url: childUrl,
                async: false,
                success: function (result) {
                    console.log("get child for survey success");
                },
                error: function (request, msg, error) {
                    console.log("get child for survey fail");
                    console.log(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.child = data;
                $scope.surveys.currentSurvey.child.birthDate = new Date(data.birthDate);
            });

            $.ajax({
                type: "GET",
                url: diagnosesUrl,
                async: false,
                success: function (result) {
                    console.log("get diagnoses for survey success");
                },
                error: function (request, msg, error) {
                    console.log("get diagnoses for survey fail");
                    console.log(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedDiagnoses = data._embedded.diagnoses;
            });

            $.ajax({
                type: "GET",
                url: disordersUrl,
                async: false,
                success: function (result) {
                    console.log("get disorders for survey success");
                },
                error: function (request, msg, error) {
                    console.log("get disorders for survey fail");
                    console.log(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedDisorders = data._embedded.disorders;
            });

            $.ajax({
                type: "GET",
                url: programsUrl,
                async: false,
                success: function (result) {
                    console.log("get programs for survey success");
                },
                error: function (request, msg, error) {
                    console.log("get programs for survey fail");
                    console.log(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedPrograms = data._embedded.educationPrograms;
            });

            $.ajax({
                type: "GET",
                url: recommendsUrl,
                async: false,
                success: function (result) {
                    console.log("get recommendations for survey success");
                },
                error: function (request, msg, error) {
                    console.log("get recommendations for survey fail");
                    console.log(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedRecommendations = data._embedded.recommendations;
            });

        });

        // $http.get(surveyUrl).then(function (response) {
        //     console.log("getting survey " + surveyUrl);
        //     $scope.surveys.currentSurvey.survey = angular.fromJson(response.data);
        //     $scope.surveys.currentSurvey.survey.surveyDate = new Date(response.data.surveyDate);
        //     // $scope.survey.child.birthDate = new Date(response.data.child.birthDate);
        //     var childUrl = response.data._links.child.href;
        //     var diagnosesUrl = response.data._links.diagnoses.href;
        //     var disordersUrl = response.data._links.disorders.href;
        //     var programsUrl = response.data._links.eduPrograms.href;
        //     var recommendsUrl = response.data._links.recommends.href;
        //
        //
        //     $http.get(childUrl).then(function (response) {
        //         $scope.surveys.currentSurvey.child = response.data;
        //         $scope.surveys.currentSurvey.child.birthDate = new Date(response.data.birthDate);
        //     });
        //
        //     $http.get(diagnosesUrl).then(function (response) {
        //         $scope.selectedDiagnoses = response.data._embedded.diagnoses;
        //     });
        //
        //     $http.get(disordersUrl).then(function (response) {
        //         $scope.selectedDisorders = response.data._embedded.disorders;
        //     });
        //
        //     $http.get(programsUrl).then(function (response) {
        //         $scope.selectedPrograms = response.data._embedded.educationPrograms;
        //     });
        //
        //     $http.get(recommendsUrl).then(function (response) {
        //         $scope.selectedRecommendations = response.data._embedded.recommendations;
        //     });
        //
        // });
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
        // TODO: here we update childName field. Perhaps there is better way
        // $scope.surveys.currentSurvey.survey.childName =
        //         ($scope.surveys.currentSurvey.child.familyName + " "
        //         + $scope.surveys.currentSurvey.child.name + " "
        //         + $scope.surveys.currentSurvey.child.patrName);

        var childUrl;
        var surveyUrl;
        var childMethodType;
        var surveyMethodType;

        try {
            childUrl = $scope.surveys.currentSurvey.child._links.self.href;
            childMethodType = PATCH;
        } catch (err) {
            childUrl = CHILDREN;
            childMethodType = POST;
        }

        // child
        $.ajax({
                url: (childUrl),
                type: childMethodType,
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


        // if (isNewSurvey === true) {
        //     console.log('actual child: ', $scope.surveys.currentSurvey.child);
        //     // surveyUrl = $scope.survey.child._links.surveys.href;
        //     surveyUrl = SURVEYS;
        //     methodType = POST;
        // } else {
        //     surveyUrl = $scope.surveys.currentSurvey.survey._links.self.href;
        // }


        try {
            surveyUrl = $scope.surveys.currentSurvey.survey._links.self.href;
            surveyMethodType = PATCH;
        } catch (err) {
            surveyUrl = SURVEYS;
            surveyMethodType = POST;
        }

        // survey
        $.ajax({
            url: (surveyUrl),
            type: surveyMethodType,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.surveys.currentSurvey.survey),
            success: function (result) {
                console.log("survey success");
            },
            error: function (request, msg, error) {
                console.log("survey fail");
            }
        }).done(function (data) {
            // surveyUrl = data._links.self.href;
            $scope.surveys.currentSurvey.survey = data;
            console.log("URL ", surveyUrl);
        });
        surveyUrl = $scope.surveys.currentSurvey.survey._links.self.href;


        //diagnoses
        var diagnosesArray = [];
        for (var key in $scope.surveys.currentSurvey.selectedDiagnoses) {
            diagnosesArray.push($scope.surveys.currentSurvey.selectedDiagnoses[key]._links.self.href);
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
        for (var key in $scope.surveys.currentSurvey.selectedDisorders) {
            disordersArray.push($scope.surveys.currentSurvey.selectedDisorders[key]._links.self.href);
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
        for (var key in $scope.surveys.currentSurvey.selectedPrograms) {
            programsArray.push($scope.surveys.currentSurvey.selectedPrograms[key]._links.self.href);
        }
        var programsString = programsArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/educationPrograms"),
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
        for (var key in $scope.surveys.currentSurvey.selectedRecommendations) {
            recommendsArray.push($scope.surveys.currentSurvey.selectedRecommendations[key]._links.self.href);
        }
        var recommendsString = recommendsArray.join("\r\n");
        $.ajax({
            url: (surveyUrl + "/recommendations"),
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


        document.getElementById("popup-window-message").textContent = "Сохранено";
        var savedWindow = document.getElementById("popup-window");
        savedWindow.style.display = 'block';
    }
}])
;