'use strict';


describe('app', function () {
    //
    // it('dummy test', function () {
    //     expect(2+2).toEqual(4);
    // });

    beforeEach(angular.mock.module('myApp'));

    var $controller, $httpBackend;
    beforeEach(inject(function(_$controller_, _$httpBackend_){
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $controller = _$controller_;
        $httpBackend = _$httpBackend_;
        // $httpBackend.whenGET(/.*/).passThrough();
        SURVEYS = "http://localhost:8080/api/suveys";
        SURVEYS = 'http://localhost:8080/api/surveys';
        CHILDREN = 'http://localhost:8080/api/children';
        DIAGNOSES = 'http://localhost:8080/api/diagnoses';
        EDU_PROGRAMS = 'http://localhost:8080/api/educationPrograms';
        DISORDERS = 'http://localhost:8080/api/disorders';
        RECOMMENDS = 'http://localhost:8080/api/recommendations';
        FIND_CHILD_BY_FAMILY_NAME = 'http://localhost:8080/api/children/search/byFamilyName?'

    }));


    describe('init empty survey', function() {
        it('controller init', function() {

            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            expect($scope.surveys.currentSurvey.survey).toBeUndefined();
            expect($scope.surveys.list.length).toEqual(0);
            $scope.initEmptySurvey();
            expect(typeof $scope.surveys.currentSurvey.survey).toEqual('object');
            expect(typeof $scope.surveys.currentSurvey.child).toEqual('object');
        });

        it('get list of surveys on init', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            expect($scope.surveys.list.length).toEqual(0);
            controller.$onInit();
            expect($scope.surveys.list.length).toBeGreaterThan(0);
        });

        it('get survey', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });

            $scope.initEmptySurvey();
            expect($scope.surveys.currentSurvey.survey).toBeEmptyObject();
            expect($scope.surveys.currentSurvey.child).toBeEmptyObject();
            expect( $scope.surveys.currentSurvey.selectedDiagnoses).toBeEmptyArray();
            expect($scope.surveys.currentSurvey.selectedDisorders).toBeEmptyArray();
            expect($scope.surveys.currentSurvey.selectedPrograms).toBeEmptyArray();
            expect( $scope.surveys.currentSurvey.selectedRecommendations).toBeEmptyArray();

            $scope.initSurvey("http://localhost:8080/api/surveys/1");

            expect($scope.surveys.currentSurvey.survey).toBeNonEmptyObject();
            expect($scope.surveys.currentSurvey.child).toBeNonEmptyObject();
            expect( $scope.surveys.currentSurvey.selectedDiagnoses).toBeNonEmptyArray();
            expect($scope.surveys.currentSurvey.selectedDisorders).toBeNonEmptyArray();
            expect($scope.surveys.currentSurvey.selectedPrograms).toBeNonEmptyArray();
            expect( $scope.surveys.currentSurvey.selectedRecommendations).toBeNonEmptyArray();
        });

        it('get, save and clear survey', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            $scope.initSurvey("http://localhost:8080/api/surveys/1");
            $scope.submit();
            $scope.initEmptySurvey();
            expect($scope.surveys.currentSurvey.survey).toBeEmptyObject();
            expect($scope.surveys.currentSurvey.child).toBeEmptyObject();
            expect( $scope.surveys.currentSurvey.selectedDiagnoses).toBeEmptyArray();
            expect($scope.surveys.currentSurvey.selectedDisorders).toBeEmptyArray();
            expect($scope.surveys.currentSurvey.selectedPrograms).toBeEmptyArray();
            expect( $scope.surveys.currentSurvey.selectedRecommendations).toBeEmptyArray();
        });



        it('get, modify and save survey', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            $scope.initSurvey("http://localhost:8080/api/surveys/1");
            $scope.surveys.currentSurvey.survey.protocolNumber = "148test";
            $scope.surveys.currentSurvey.child.name = "testName";
            $scope.submit();
            $scope.initEmptySurvey();

            $scope.initSurvey("http://localhost:8080/api/surveys/1");
            expect($scope.surveys.currentSurvey.survey.protocolNumber).toEqual("148test");
            expect($scope.surveys.currentSurvey.child.name).toEqual("testName");
        });

        it('create survey, then get it', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            $scope.initEmptySurvey();
            $scope.surveys.currentSurvey.survey.protocolNumber = "999";
            $scope.surveys.currentSurvey.survey.surveyDate = new Date();
            $scope.surveys.currentSurvey.child.name = "testName";
            $scope.surveys.currentSurvey.child.familyName = "testFamilyName";
            $scope.surveys.currentSurvey.child.birthDate = new Date();
            $scope.submit();

            var surveyUrl =  $scope.surveys.currentSurvey.survey._links.self.href;
            $scope.initEmptySurvey();
            $scope.initSurvey(surveyUrl);
            expect($scope.surveys.currentSurvey.survey.protocolNumber).toEqual("999");
            expect($scope.surveys.currentSurvey.child.familyName).toEqual("testFamilyName");
            expect($scope.surveys.currentSurvey.child.name).toEqual("testName");
        });


    });


});