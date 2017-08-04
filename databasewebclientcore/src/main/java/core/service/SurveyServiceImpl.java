package core.service;

import core.dao.SurveyRepository;
import core.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Override
    public Survey findSurveyById(long id) {
        Survey survey = surveyRepository.findOne(id);
        return survey;
    }

    @Override
    public void saveSurvey(Survey survey) {
        surveyRepository.save(survey);
    }

    @Override
    public List<Survey> getAllSurveys(){
        return surveyRepository.findAll();
    }
}
