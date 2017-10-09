package by.bockroir.app.components.validators;

import by.bockroir.app.domain.Region;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegionValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Region.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Region recommendation = (Region) target;
        if (recommendation.getRegion() == null)
            errors.rejectValue("region", "region.empty");
    }
}
