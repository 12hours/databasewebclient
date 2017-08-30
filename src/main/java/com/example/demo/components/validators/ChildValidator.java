package com.example.demo.components.validators;

import com.example.demo.domain.Child;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChildValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Child.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Child child = (Child) target;
        if (child.getName() == null)
            errors.rejectValue("name", "child.name.empty");
        if (child.getFamilyName() == null)
            errors.rejectValue("familyName", "child.familyName.empty");
        if (child.getPatrName() == null)
            errors.rejectValue("patrName", "child.patrName.empty");
        if (child.getBirthDate() == null)
            errors.rejectValue("birthDate", "child.birthDate.empty");
    }
}
