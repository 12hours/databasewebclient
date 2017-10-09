package by.bockroir.app.components.validators;

import by.bockroir.app.domain.Diagnosis;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DiagnosisValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Diagnosis.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Diagnosis diagnosis = (Diagnosis) target;
        if (diagnosis.getDiagnosis() == null)
            errors.rejectValue("diagnosis", "diagnosis.empty");
    }
}
