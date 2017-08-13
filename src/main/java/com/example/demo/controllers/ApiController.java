package com.example.demo.controllers;


import com.example.demo.dao.SurveyRepository;
import com.example.demo.domain.Survey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api2")
public class ApiController {

    SurveyRepository surveyRepository;

    public ApiController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/allsurveys")
    public Iterable<Survey> getSurveys(){
        return surveyRepository.findAll();
    }
}
