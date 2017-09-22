'use strict';

myApp.controller('SurveyController', ['$http', '$scope', '$rootScope', function ($http, $scope, $rootScope) {

    $scope.query = {
        name: '',
        surveyDateStart: '',
        surveyDateEnd: '',        
        birthDateStart: '',
        birthDateEnd: '',
        diagnosis: '',
        disorder: '',
        educationProgram: '',
        recommendation: '',
        targetStartAge: '',
        targetEndAge: ''
    }

    $('.date-start').datepicker({
        minViewMode: 0,
        format: 'yyyy-mm-dd',
        clearBtn: true,
        language: 'ru'
    }).on('changeDate', function () {
        $scope.$apply(function () {
            $scope.dateStart = $('.date-start').datepicker('getFormattedDate');
            if ($scope.dateStart === '') {
                $scope.query.surveyDateStart = '';
                $scope.dateStart = null;
                $scope.getSurveysList(0);
            } else {
                $scope.query.surveyDateStart = new Date($scope.dateStart).toISOString().split('T')[0];
                $scope.getSurveysList(0);
            }
        });
    });

    $('.date-end').datepicker({
        minViewMode: 0,
        format: 'yyyy-mm-dd',
        clearBtn: true,
        language: 'ru'
    }).on('changeDate', function () {
        $scope.$apply(function () {
            $scope.dateEnd = $('.date-end').datepicker('getFormattedDate');
            if ($scope.dateEnd === '') {
                $scope.query.surveyDateEnd = '';
                $scope.dateEnd = null;
                $scope.getSurveysList(0);
            } else {
                $scope.query.surveyDateEnd = new Date($scope.dateEnd).toISOString().split('T')[0];
                $scope.getSurveysList(0);
            }
        });
    });

    $('.birth-date-start').datepicker({
        minViewMode: 0,
        format: 'yyyy-mm-dd',
        clearBtn: true,
        language: 'ru'
    }).on('changeDate', function () {
        $scope.$apply(function () {
            $scope.birthDateStart = $('.birth-date-start').datepicker('getFormattedDate');
            if ($scope.birthDateStart === '') {
                $scope.query.birthDateStart = '';
                $scope.birthDateStart  = null;
                $scope.getSurveysList(0);
            } else {
                $scope.query.birthDateStart  = new Date($scope.birthDateStart).toISOString().split('T')[0];
                if ($scope.birthDateEnd == null){
                    $scope.birthDateEnd = $scope.birthDateStart;
                    $scope.query.birthDateEnd = $scope.query.birthDateStart;
                }
                $scope.getSurveysList(0);
            }
        });
    });

    $('.birth-date-end').datepicker({
        minViewMode: 0,
        format: 'yyyy-mm-dd',
        clearBtn: true,
        language: 'ru'
    }).on('changeDate', function () {
        $scope.$apply(function () {
            $scope.birthDateEnd = $('.birth-date-end').datepicker('getFormattedDate');
            if ($scope.birthDateEnd === '') {
                $scope.query.birthDateEnd = '';
                $scope.birthDateEnd = null;
                $scope.getSurveysList(0);
            } else {
                $scope.query.birthDateEnd = new Date($scope.birthDateEnd).toISOString().split('T')[0];
                $scope.getSurveysList(0);
            }
        });
    });

    $scope.surveys = {};
    $scope.surveys.list = [];
    $scope.surveys.size = 0;
    $scope.surveys.currentPage = 0;
    $scope.surveys.currentSurvey = {};
    $scope.surveys.currentSurvey.region = {};

    this.$onInit = function () {
        $scope.getSurveysList(0);
        $scope.getData();
    };

    $scope.getSurveysListByName = function (name) {
        $scope.query.name = name;
        $scope.getSurveysList(0);
    };

    $scope.getSurveysList = function (page) {
        $scope.getSurveysListByUrl(SURVEYS_SEARCH +
                                            '&surveyDateStart=' + $scope.query.surveyDateStart +
                                            '&surveyDateEnd=' + $scope.query.surveyDateEnd +
                                            '&birthDateStart=' + $scope.query.birthDateStart +
                                            '&birthDateEnd=' + $scope.query.birthDateEnd +
                                            '&childName=' + $scope.query.name +
                                            '&diagnosisId=' + $scope.query.diagnosis +
                                            '&disorderId=' + $scope.query.disorder +
                                            '&educationProgramId=' + $scope.query.educationProgram +
                                            '&recommendationId=' + $scope.query.recommendation +
                                            '&targetStartAge=' + $scope.query.targetStartAge +
                                            '&targetEndAge=' + $scope.query.targetEndAge +
                                            '&page=' + page +
                                            '&sort=protocolNumber,surveyDate');
    };

    $scope.getSurveysListByUrl = function (url) {
        logger.notice("getting page with url ", url);

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
            $scope.surveys.currentSurvey.survey.nextSurveyDate = new Date(data.nextSurveyDate);
            // $scope.survey.child.birthDate = new Date(response.data.child.birthDate);
            var childUrl = data._links.child.href;
            var diagnosesUrl = data._links.diagnoses.href;
            var disordersUrl = data._links.disorders.href;
            var programsUrl = data._links.educationPrograms.href;
            var recommendsUrl = data._links.recommendations.href;
            // var regionsUrl = data._links.regions.href;

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

            var regionsUrl = $scope.surveys.currentSurvey.child._links.region.href;
            $.ajax({
                type: "GET",
                url: regionsUrl,
                async: false,
                success: function (result) {
                    logger.info("get regions for survey success");
                },
                error: function (request, msg, error) {
                    logger.error("get regions for survey fail");
                    logger.error(error);
                }
            }).done(function (data) {
                $scope.surveys.currentSurvey.region = data;
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

            $scope.isSchoolchild = checkIsHeSchoolchild($scope.surveys.currentSurvey.survey.surveyDate,
                                                        $scope.surveys.currentSurvey.child.birthDate);

            function checkIsHeSchoolchild (surveyDate, birthDate) {

                var difference;

                if (birthDate.getFullYear() === surveyDate.getFullYear()) difference = 0;
                else {
                    difference = surveyDate.getFullYear() - birthDate.getFullYear();
                    if ((surveyDate.getMonth() === birthDate.getMonth() && surveyDate.getDay() < birthDate.getDay()) ||
                    (surveyDate.getMonth() < birthDate.getMonth()))
                        difference = difference - 1;
                }

                var type;

                if (0 <= difference && difference < 3) type = "Ранний возраст";
                else if (3 <= difference && difference < 6) type = "Дошкольник";
                else if (6 <= difference && difference < 18) type = "Школьник";
                else type = "Взрослый";

                console.log("WARNING! " + difference + " " + type);

                return type;
            }
        });
    };

    $scope.getData = function () {
        $scope.getDiagnosesList();
        $scope.getDisordersList();
        $scope.getEducationProgramsList();
        $scope.getRecommendationsList();
        $scope.getRegionsList();
    }

    //TODO: Refactor next code

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

    $scope.getRegionsList = function () {
        $http.get(REGIONS).then(function (response) {
            var regionsList = response.data._embedded.regions;
            $scope.regions = regionsList;
        });
    };

    $scope.validate = function () {
        var errorNames = [];
        var validated = true;

        if ($scope.surveyEditChild.surname.$invalid){
            errorNames.push("Фамилия");
            validated = false;
        }

        if ($scope.surveyEditChild.name.$invalid){
            errorNames.push("Имя");
            validated = false;
        }

        if ($scope.surveyEditChild.patrName.$invalid){
            errorNames.push("Отчество");
            validated = false;
        }

        if ($scope.surveyEditChild.birthDate.$invalid){
            errorNames.push("Дата рождения");
            validated = false;
        }

        if ($scope.surveyEditSurvey.protocolNumber.$invalid){
            errorNames.push("Номер протокола");
            validated = false;
        }

        if ($scope.surveyEditSurvey.surveyDate.$invalid){
            errorNames.push("Дата обследования");
            validated = false;
        }

        if (!validated) {
            raisePopup(`Поле ${errorNames.join(", ")} не заполнено или заполнено неверно`);
        }

        return validated;
    }

    $scope.submit = function () {

        if (!$scope.validate()) {
            throw "Validation fail";
            return;
        }
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
        // $scope.surveys.currentSurvey.child.region = $scope.surveys.currentSurvey.region._links.href;
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

        // // regions
        // var regionUrl = $scope.surveys.currentSurvey.region;
        // console.log("IMPORTANT " + regionUrl);
        // $.ajax({
        //     url: (childUrl + "/regions"),
        //     type: PUT,
        //     contentType: 'text/uri-list',
        //     data: regionUrl,
        //     success: function (result) {
        //         logger.info("region update success");
        //     },
        //     error: function (request, msg, error) {
        //         logger.info("region update fail");
        //     }
        // });

        $scope.getSurveysList($scope.surveys.currentPage);
        raisePopup("Сохранено");
    };

}])
;