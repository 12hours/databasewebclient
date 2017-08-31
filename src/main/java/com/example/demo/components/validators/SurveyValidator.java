package com.example.demo.components.validators;

import com.example.demo.dao.SurveyRepository;
import com.example.demo.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SurveyValidator implements Validator {

    private SurveyRepository surveyRepository;

    @Autowired
    public SurveyValidator(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Survey.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Survey survey = (Survey) target;
        if (survey.getSurveyDate() == null)
            errors.rejectValue("surveyDate", "survey.date.empty");
        if (survey.getProtocolNumber() == null)
            errors.rejectValue("surveyDate", "survey.number.empty");

        Survey surveyExisting = surveyRepository.findUnique(survey.getProtocolNumber(), survey.getSurveyDate());
        if (surveyExisting != null) {
            if (surveyExisting.getId() != survey.getId()) {
                errors.rejectValue("id", "survey.alreadyExists");
            }
        }
    }
}
