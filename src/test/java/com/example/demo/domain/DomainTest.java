package com.example.demo.domain;

import static org.junit.Assert.*;

import com.example.demo.config.DataSourceConfig;
import com.example.demo.config.JpaConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.*;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, JpaConfig.class})
public class DomainTest {

    EntityManager em = null;

    @PersistenceUnit
    EntityManagerFactory emf;


    @Before
    public void before(){
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @After
    public void after(){
        em.getTransaction().rollback();
        em.close();
    }

    @Ignore
    @Test
    public void test() throws InterruptedException {
        Survey survey = new Survey();
        survey.setProtocolNumber("42");

        Child child = new Child();
        child.setName("victor");

        Disorder disorder = new Disorder();
        disorder.setDisorder("dyslexia");

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosis("F81");

        Recommendation rec = new Recommendation();
        rec.setRecommendation("some recommendations");

        EducationProgram eduProg = new EducationProgram();
        eduProg.setProgram("some education program");

        em.persist(disorder);
        em.persist(diagnosis);
        em.persist(rec);
        em.persist(eduProg);
        survey.setChild(child);
        em.persist(survey);

        disorder.getSurveys().add(survey);
        diagnosis.getSurveys().add(survey);
        rec.getSurveys().add(survey);
        eduProg.getSurveys().add(survey);

        survey.getEducationPrograms().add(eduProg);
        survey.getDiagnoses().add(diagnosis);
        survey.getDisorders().add(disorder);
        survey.getRecommendations().add(rec);

        em.flush();

        assertThat(true, is(true));
    }

    @Test
    public void createDisorder(){
        final String disorderName = "dyslexia";
        Disorder disorder = new Disorder();
        disorder.setDisorder(disorderName);
        em.persist(disorder);
        em.flush();
        long disorderId = disorder.getId();
        disorder = null;

        assertTrue(disorderId  > 0);

        Query namedQuery = em.createQuery("SELECT d FROM Disorder d WHERE (d.disorder = ?1)", Disorder.class);
        namedQuery.setParameter(1, disorderName);
        Disorder disorderFound = (Disorder) namedQuery.getSingleResult();

        assertEquals(disorderFound.getDisorder(), disorderName);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void createDisorderCheckConstraint(){
        final String disorderName = "dyslexia";
        Disorder disorder = new Disorder();
        disorder.setDisorder(disorderName);
        em.persist(disorder);
        em.flush();
        long disorderId = disorder.getId();
        em.clear();
        disorder = null;

        disorder = new Disorder();
        disorder.setDisorder(disorderName);
        em.persist(disorder);
        em.flush();
        disorder = null;
    }


    @Test
    public void createDiagnosis(){
        final String diagnosisName = "F81";
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosis(diagnosisName);
        em.persist(diagnosis);
        em.flush();
        long disorderId = diagnosis.getId();
        diagnosis = null;

        assertTrue(disorderId  > 0);

        Query namedQuery = em.createQuery("SELECT c FROM Diagnosis c WHERE (c.diagnosis = ?1)", Diagnosis.class);
        namedQuery.setParameter(1, diagnosisName);
        Diagnosis disorderFound = (Diagnosis) namedQuery.getSingleResult();

        assertEquals(disorderFound.getDiagnosis(), diagnosisName);
    }

    @Test
    public void createRecommendation() {
        final String recommendation = "Some recommendation";
        Recommendation rec = new Recommendation();
        rec.setRecommendation(recommendation);
        em.persist(rec);
        em.flush();
        long id = rec.getId();
        assertTrue(id > 0);

        Recommendation recommendationFound = em.find(Recommendation.class, id);
        assertEquals(recommendationFound.getRecommendation(), recommendation);
    }

    @Test
    public void createEduProgram(){
        final String programString = "Some program";
        EducationProgram eduProg = new EducationProgram();
        eduProg.setProgram(programString);
        em.persist(eduProg);
        em.flush();
        long id = eduProg.getId();
        assertTrue(id > 0);

        EducationProgram programFound = em.find(EducationProgram.class, id);
        assertEquals(programFound.getProgram(), programString);
    }

    @Test
    public void createChild(){
        String childName = "victor";
        Child child = new Child();
        child.setName(childName);
        em.persist(child);
        em.flush();
        long id = child.getId();

        Child childFound = em.find(Child.class, id);
        assertEquals(childFound.getName(), childName);
    }

    @Test
    public void createSurveyAndCheckDate(){
        Survey survey = new Survey();
        survey.setChild(new Child());

        long time = System.currentTimeMillis();
        survey.setSurveyDate(new java.sql.Date(time));
        survey.setProtocolNumber("100");
        em.persist(survey);
        em.flush();
        long id = survey.getId();

        Survey surveyFound = em.find(Survey.class, id);
        assertEquals(surveyFound.getSurveyDate(), new java.sql.Date(time));
    }

    @Test
    public void testDomain(){

        // CREATE DOMAIN OBJECTS
        Disorder disorder = new Disorder();
        disorder.setDisorder("dyslexia");
        em.persist(disorder);
        long disorderId = disorder.getId();
        assertTrue(disorderId > 0);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosis("F81");
        em.persist(diagnosis);
        long diagnosisId = diagnosis.getId();
        assertTrue(diagnosisId > 0);

        Recommendation rec = new Recommendation();
        rec.setRecommendation("some recommendation");
        em.persist(rec);
        long recId = rec.getId();
        assertTrue(recId > 0);

        EducationProgram eduProg = new EducationProgram();
        eduProg.setProgram("some programm");
        em.persist(eduProg);
        long eduId = eduProg.getId();
        assertTrue(eduId > 0);
        // ----------- //

        Child child = new Child();
        child.setName("childName");
        em.persist(child);
        long childId = child.getId();
        assertTrue(childId > 0);

        Survey survey = new Survey();
        child.getSurveys().add(survey);
        disorder.getSurveys().add(survey);
        diagnosis.getSurveys().add(survey);
        rec.getSurveys().add(survey);
        eduProg.getSurveys().add(survey);
        survey.setChild(child);
        survey.getRecommendations().add(rec);
        survey.getDisorders().add(disorder);
        survey.getDiagnoses().add(diagnosis);
        survey.getEducationPrograms().add(eduProg);
        survey.setProtocolNumber("999");
        survey.setSurveyDate(new Date());
        em.persist(survey);
        em.flush();
        long surveyId = survey.getId();
        assertTrue(surveyId > 0);

        survey = null;
        disorder = null;
        diagnosis = null;
        rec = null;
        eduProg = null;
        child = null;

        em.getTransaction().commit();
        em.close();

        // TEST SURVEY
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Survey foundSurvey = em.find(Survey.class, surveyId);
        assertTrue(foundSurvey.getChild().getId() == childId);
        assertTrue(foundSurvey.getDiagnoses().iterator().next().getId() == diagnosisId);
        assertTrue(foundSurvey.getDisorders().iterator().next().getId() == disorderId);
        assertTrue(foundSurvey.getEducationPrograms().iterator().next().getId() == eduId);
        assertTrue(foundSurvey.getRecommendations().iterator().next().getId() == recId);

        // TEST CHILD
        Child foundChild = em.find(Child.class, childId);
        assertTrue(foundChild.getSurveys().iterator().next().getId() == surveyId);

        // TEST DISORDER
        Disorder foundDisorder = em.find(Disorder.class, disorderId);
        assertTrue(foundDisorder.getSurveys().iterator().next().getId() == surveyId);

        // TEST DIAGNOSIS
        Diagnosis foundDiagnosis = em.find(Diagnosis.class, diagnosisId);
        assertTrue(foundDiagnosis.getSurveys().iterator().next().getId() == surveyId);

        // TEST RECOMMENDATION
        Recommendation foundRec = em.find(Recommendation.class, recId);
        assertTrue(foundRec.getSurveys().iterator().next().getId() == surveyId);

        // TEST EDU_PROGRAMM
        EducationProgram foundEduProg = em.find(EducationProgram.class, eduId);
        assertTrue(foundEduProg.getSurveys().iterator().next().getId() == surveyId);

    }

}