'use strict';

myApp.controller('ProgramsController', ['$scope', function ($scope) {

    $scope.programs = {};
    $scope.programs.newProgram = {};
    $scope.programs.list = [];

    this.$onInit = function () {
        $scope.getProgramsList();
    };

    $scope.getProgramsList = function (){
        $.ajax({
            type: GET,
            url: EDU_PROGRAMS,
            async: false,
            success: function (result) {
                logger.info("programs list success");
            },
            error: function (request, msg, error) {
                logger.error("programs list fail");
            }
        }).done(function (data) {
            logger.info(data._embedded.programs);
            $scope.programs.list = data._embedded.educationPrograms;
        })
    };

    $scope.addProgram = function () {
        if ($scope.addProgramForm.program.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: POST,
            url: EDU_PROGRAMS,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.programs.newProgram),
            success: function (result) {
                logger.info("program save success");
            },
            error: function (request, msg, error) {
                logger.error("program save fail");
            }
        });
        $scope.programs.newProgram = {};
        $scope.nav.programsTabSelected = 1;
        $scope.getProgramsList();
    };

    $scope.updateProgram = function (url) {
        if ($scope.updateProgramForm.program.$invalid){
            raisePopup("Поле не может быть пустым");
            return;
        }
        $.ajax({
            type: PATCH,
            url: url,
            async: false,
            contentType: 'application/json',
            data: angular.toJson($scope.programs.currentProgram),
            success: function (result) {
                logger.info("diagnosis update success");
            },
            error: function (request, msg, error) {
                logger.error("diagnosis update fail");
            }
        });
        $scope.programs.currentProgram = {};
        $scope.nav.programsTabSelected = 1;
        $scope.getProgramsList();
    };

    $scope.deleteProgram = function (url) {
        if (confirm('Вы действительно хотите удалить запись?')) {
            $.ajax({
                type: DELETE,
                url: url,
                async: false,
                success: function (result) {
                    logger.info("program delete success");
                },
                error: function (request, msg, error) {
                    logger.error("program delete fail");
                }
            });
            $scope.programs.currentProgram = {};
            $scope.nav.programsTabSelected = 1;
            $scope.getProgramsList();
        } else {
            return;
        }
    };

}]);