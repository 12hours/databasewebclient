package com.example.demo.components.validators;

import com.example.demo.domain.EducationProgram;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EducationProgramValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return EducationProgram.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EducationProgram educationProgram = (EducationProgram) target;
        if (educationProgram.getProgram() == null)
            errors.rejectValue("program", "educationProgram.empty");
    }
}
