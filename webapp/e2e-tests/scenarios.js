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


    describe('$scope.grade', function() {
        it('controller init', function() {

            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            $scope.initEmptySurvey();
            expect(typeof $scope.surveys.currentSurvey.survey).toEqual('object');
            expect(typeof $scope.surveys.currentSurvey.child).toEqual('object');
        });

        it('controller get list of surveys on init', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            expect($scope.surveys.list.length).toEqual(0);
            controller.$onInit();
            expect($scope.surveys.list.length).toBeGreaterThan(0);
        });

        it('repeat', function () {
            var $scope = {};
            var controller = $controller('SurveyController', { $scope: $scope });
            expect($scope.surveys.list.length).toEqual(0);

            expect($scope.surveys.currentSurvey.survey).toBeUndefined();
            $scope.initSurvey("http://localhost:8080/api/surveys/1");
            expect($scope.surveys.currentSurvey.survey).not.toBeUndefined();
            expect($scope.surveys.currentSurvey.child).not.toBeUndefined();
        })

    });


});