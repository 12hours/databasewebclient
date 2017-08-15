package com.example.demo.dao.tx;

import com.example.demo.controllers.Api2Controller;
import com.example.demo.domain.Child;
import com.example.demo.domain.Survey;
import com.example.demo.util.DTOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SurveyDao extends TransactionalDao{

    static Logger logger = LogManager.getLogger(Api2Controller.class);

    public void saveSurvey(String token, Survey survey) {
        EntityManager entityManager = getEntityManager(token);
        if (survey.getId() != null){
            Survey surveyFound = entityManager.find(Survey.class, survey.getId());
            DTOUtils.copyProperties(survey, surveyFound);
//            entityManager.merge(surveyFound);
//            entityManager.persist(childFound);
        } else {
            entityManager.persist(survey);
        }
    }

}
