package client.service;

import client.domain.Survey;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Service
public class SurveyService {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    public void saveSurvey(Survey survey){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(survey);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
