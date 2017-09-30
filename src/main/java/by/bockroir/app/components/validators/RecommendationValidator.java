package by.bockroir.app.components.validators;

import by.bockroir.app.domain.Recommendation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RecommendationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Recommendation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Recommendation recommendation = (Recommendation) target;
        if (recommendation.getRecommendation() == null)
            errors.rejectValue("recommendation", "recommendation.empty");
    }
}
