package by.bockroir.app.config;

import by.bockroir.app.components.validators.*;
import by.bockroir.app.domain.*;
import by.bockroir.app.dao.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

    @Autowired
    ChildValidator childValidator;

    @Autowired
    SurveyValidator surveyValidator;

    private ChildRepository childRepository;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Child.class);
        config.exposeIdsFor(Survey.class);
        config.exposeIdsFor(Diagnosis.class);
        config.exposeIdsFor(Disorder.class);
        config.exposeIdsFor(EducationProgram.class);
        config.exposeIdsFor(Recommendation.class);
    }

    @Override
    public void configureValidatingRepositoryEventListener(
            ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", surveyValidator);
        v.addValidator("beforeSave", surveyValidator);
        v.addValidator("beforeCreate", childValidator);
        v.addValidator("beforeSave", childValidator);
        v.addValidator("beforeCreate", new DiagnosisValidator());
        v.addValidator("beforeSave", new DiagnosisValidator());
        v.addValidator("beforeCreate", new DisorderValidator());
        v.addValidator("beforeSave", new DisorderValidator());
        v.addValidator("beforeCreate", new EducationProgramValidator());
        v.addValidator("beforeSave", new EducationProgramValidator());
        v.addValidator("beforeCreate", new RecommendationValidator());
        v.addValidator("beforeSave", new RecommendationValidator());
    }

}