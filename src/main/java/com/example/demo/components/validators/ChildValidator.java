package com.example.demo.components.validators;

import com.example.demo.dao.ChildRepository;
import com.example.demo.dao.SurveyRepository;
import com.example.demo.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ChildValidator implements Validator {

    private ChildRepository childRepository;

    @Autowired
    public ChildValidator(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Child.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "child.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "familyName", "child.familyName.empty");
        ValidationUtils.rejectIfEmpty(errors, "patrName", "child.patrName.empty");
        ValidationUtils.rejectIfEmpty(errors, "birthDate", "child.birthDate.empty");

        Child child = (Child) target;
        Child childExisting = childRepository.findUnique(child.getFamilyName(), child.getName(), child.getPatrName(),
                child.getBirthDate());
        if (childExisting != null){
            if (childExisting.getId() != child.getId()){
                errors.rejectValue("id", "child.alreadyExists");
            }
        }

    }
}
