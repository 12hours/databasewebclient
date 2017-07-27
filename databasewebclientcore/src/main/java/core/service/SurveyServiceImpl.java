package core.service;

import core.dao.SurveyRepository;
import core.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Override
    public Survey findSurveyById(long id) {
        return surveyRepository.findOne(id);
    }

    @Override
    public void saveSurvey(Survey survey) {
        surveyRepository.save(survey);
    }
}
