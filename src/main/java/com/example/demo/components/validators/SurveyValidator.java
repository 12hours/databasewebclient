package com.example.demo.components.validators;

import com.example.demo.domain.Survey;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SurveyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Survey.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Survey survey = (Survey) target;
        if (survey.getSurveyDate() == null)
            errors.rejectValue("surveyDate", "protocol.date.empty");
        if (survey.getProtocolNumber() == null)
            errors.rejectValue("surveyDate", "protocol.number.empty");
    }
}
