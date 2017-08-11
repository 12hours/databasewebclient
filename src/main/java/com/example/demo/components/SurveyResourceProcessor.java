package com.example.demo.components;

import com.example.demo.domain.Survey;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SurveyResourceProcessor implements ResourceProcessor<Resource<Survey>>{

    @Override
    public Resource<Survey> process(Resource<Survey> surveyResource) {
//        Survey survey = surveyResource.getContent();
//        surveyResource.add(linkTo(methodOn(Survey.class).getChildName()).withRel("getName"));
        return surveyResource;
    }
}
