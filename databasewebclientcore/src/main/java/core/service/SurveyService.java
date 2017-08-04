package core.service;

import core.domain.Survey;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SurveyService {

    Survey findSurveyById(long id);

    void saveSurvey(Survey survey);

    List<Survey> getAllSurveys();
}