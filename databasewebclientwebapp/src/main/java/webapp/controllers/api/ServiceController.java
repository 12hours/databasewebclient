package webapp.controllers.api;

import core.domain.Child;
import core.domain.Diagnosis;
import core.domain.Survey;
import core.service.ChildService;
import core.service.DomainService;
import core.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.data.DTUtils;

@RequestMapping("/api")
@RestController
public class ServiceController {

    @Autowired
    ChildService childService;

    @Autowired
    SurveyService surveyService;

    @Autowired
    DomainService domainService;

    @RequestMapping(value = "/savechild", method = RequestMethod.POST)
    public String saveChild(){

        return null;
    }

    @RequestMapping(value = "/getchild", method = RequestMethod.POST)
    public String getChild(@RequestParam("id") long id){
        Child child = childService.getChildById(id);
        String jsonChild = DTUtils.childToJson(child);
        return jsonChild;
    }

    @RequestMapping(value = "/getsurvey", method = RequestMethod.POST)
    public String getSurvey(@RequestParam("id") long id){
        Survey survey = surveyService.findSurveyById(id);
        String jsonSurvey = DTUtils.surveyToJson(survey);
        return jsonSurvey;
    }

    @RequestMapping(value = "/getdiagnosis", method = RequestMethod.POST)
    public String getDiagnosis(@RequestParam("id") long id){
        Diagnosis diagnosis = domainService.findDiagnosisById(id);
        String jsonDiagn = DTUtils.objectToJson(diagnosis);
        return jsonDiagn;
    }

}
