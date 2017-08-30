package com.example.demo.components.validators;

import com.example.demo.domain.Disorder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DisorderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Disorder.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Disorder disorder = (Disorder) target;
        if (disorder.getDisorder() == null)
            errors.rejectValue("disorder", "disorder.empty");
    }
}
