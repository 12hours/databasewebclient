package client.controllers;

import client.dao.ChildRepository;
import client.dao.SurveyRepository;
import client.domain.Child;
import client.domain.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
public class ChildController {

    @Autowired
    ChildRepository childrenService;

    @Autowired
    SurveyRepository surveyService;

    @RequestMapping(value = {"/savechild"}, method = RequestMethod.POST)
    public String saveChild(@RequestBody String jsonString){
        String result = null;
//        try {
//            Child child = childrenService.createChildFromJson(jsonString);
//            childrenService.saveChild(child);
//            result =  "{ result: success }";
//        } catch (Exception e){
//            e.printStackTrace();
//            result =  "{ result: failure }";
//        }
        return result;
    }

    @RequestMapping("/getchild")
    public String getChild(@RequestParam("id") long id) {
//        Child child = childrenService.getChildById(id);
        Child child = childrenService.findOne(id);
        if (child == null)  return "not found";

        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(child);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "no json";
//        return String.valueOf(child.getSurveys().get(0).getProtocolNumber());
    }

    @RequestMapping("/getsurvey")
    public String getSurvey(@RequestParam("id") int id){
        Survey survey = surveyService.findOne(id);
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(survey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "no json";
    }

    @RequestMapping(value = "/test")
    public void test(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 6, 18);

        Child child = new Child();
        child.setAddress("addr");
        child.setDisability(false);
        child.setBirthDate(new java.sql.Date(calendar.getTimeInMillis()));
        child.setFamilyName("fam");
        child.setPatrName("patr");
        child.setName("name");
        child.setPlaceOfEducation("none");
        child.setSpecialFacilitiesNeeding(false);

        Survey survey = new Survey();
        survey.setDeterminedDisorder("disorder");
        survey.setDiagnosis("diagn");
        survey.setProtocolNumber(42);
        survey.setRecommendedFormOfEducation("edu");
        calendar.set(2017, 8, 24);
        survey.setSurveyDate(new java.sql.Date(calendar.getTimeInMillis()));
        survey.setChild(child);

//        child.getSurveys().add(survey);
//        childrenService.saveChild(child);
        surveyService.save(survey);
    }
}
