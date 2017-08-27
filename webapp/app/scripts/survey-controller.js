'use strict';

myApp.controller('SurveyController', ['$http', '$scope', '$rootScope', function ($http, $scope, $rootScope) {

    var nameQuery = '';
    var startDateQuery = '';
    var endDateQuery = '';

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
        logger.info("getting page with url ", url);

        $.ajax({
            type: "GET",
            url: url,
            async: false,
            success: function (result) {
                logger.info("child update success");
            },
            error: function (request, msg, error) {
                logger.error("child update fail");
                logger.error(error);
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
    };

    this.$onDestroy = function () {
    };

    $scope.initEmptySurvey = function () {
        $scope.getData();
        logger.info("clear");
        $scope.surveys.currentSurvey.survey = {};
        $scope.surveys.currentSurvey.child = {};

        $scope.surveys.currentSurvey.selectedDiagnoses = [];
        $scope.surveys.currentSurvey.selectedDisorders = [];
        $scope.surveys.currentSurvey.selectedPrograms = [];
        $scope.surveys.currentSurvey.selectedRecommendations = [];
    };

    $scope.initSurvey = function (surveyUrl) {
        $scope.getData();
        logger.info("TARGET=" + surveyUrl);
        if (surveyUrl === -1) {
            $scope.initEmptySurvey();
            return;
        }

        $.ajax({
            type: "GET",
            url: surveyUrl,
            async: false,
            success: function (result) {
                logger.info("get survey success");
            },
            error: function (request, msg, error) {
                logger.error("get survey fail");
                logger.error(error);
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
                    logger.info("get child for survey success");
                },
                error: function (request, msg, error) {
                    logger.error("get child for survey fail");
                    logger.error(error);
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
                    logger.info("get diagnoses for survey success");
                },
                error: function (request, msg, error) {
                    logger.error("get diagnoses for survey fail");
                    logger.error(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedDiagnoses = data._embedded.diagnoses;
            });

            $.ajax({
                type: "GET",
                url: disordersUrl,
                async: false,
                success: function (result) {
                    logger.info("get disorders for survey success");
                },
                error: function (request, msg, error) {
                    logger.error("get disorders for survey fail");
                    logger.error(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedDisorders = data._embedded.disorders;
            });

            $.ajax({
                type: "GET",
                url: programsUrl,
                async: false,
                success: function (result) {
                    logger.info("get programs for survey success");
                },
                error: function (request, msg, error) {
                    logger.error("get programs for survey fail");
                    logger.error(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedPrograms = data._embedded.educationPrograms;
            });

            $.ajax({
                type: "GET",
                url: recommendsUrl,
                async: false,
                success: function (result) {
                    logger.info("get recommendations for survey success");
                },
                error: function (request, msg, error) {
                    logger.error("get recommendations for survey fail");
                    logger.error(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.selectedRecommendations = data._embedded.recommendations;
            });

        });
    };

    $scope.getData = function () {
        $scope.getDiagnosesList();
        $scope.getDisordersList();
        $scope.getEducationProgramsList();
        $scope.getRecommendationsList();
    }

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
                    logger.info("child update success");
                },
                error: function (request, msg, error) {
                    logger.error("child update fail");
                }
            }
        ).done(function (data) {
            $scope.surveys.currentSurvey.survey.child = data._links.self.href;
        });

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
                logger.info("survey update success");
            },
            error: function (request, msg, error) {
                logger.error("survey update fail: ", surveyMethodType, 'on', surveyUrl);
            }
        }).done(function (data) {
            // surveyUrl = data._links.self.href;
            $scope.surveys.currentSurvey.survey = data;
            logger.info("URL ", surveyUrl);
        });
        surveyUrl = $scope.surveys.currentSurvey.survey._links.self.href;


        //diagnoses
        var diagnosesArray = [];
        for (var key in $scope.surveys.currentSurvey.selectedDiagnoses) {
            if ($scope.surveys.currentSurvey.selectedDiagnoses[key].hasOwnProperty('_links'))  // check if not empty
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
            if ($scope.surveys.currentSurvey.selectedDisorders[key].hasOwnProperty('_links'))
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
            if ($scope.surveys.currentSurvey.selectedPrograms[key].hasOwnProperty('_links'))
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
            if ($scope.surveys.currentSurvey.selectedRecommendations[key].hasOwnProperty('_links'))
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

        raisePopup("Сохранено");
    };
}])
;