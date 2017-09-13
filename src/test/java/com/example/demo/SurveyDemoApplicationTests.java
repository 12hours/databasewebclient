package com.example.demo;

import com.example.demo.dao.ChildRepository;
import com.example.demo.dao.SurveyRepository;
import com.example.demo.domain.Child;
import com.example.demo.domain.Survey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyDemoApplication.class)
@WebAppConfiguration
@Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/clean.sql")
public class SurveyDemoApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    ChildRepository childRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/data.sql")
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.surveys[*].diagnosis", hasItem("NA diagnosis")));
    }

    @Test
    public void ageTest() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(jsonPath("$._embedded.surveys", hasSize(0)));

        Child child = new Child();
        child.setName("name");
        child.setFamilyName("familyName");
        child.setPatrName("patrName");
        child.setBirthDate(new GregorianCalendar(2014,01,22).getTime());
//        child = childRepository.save(child);

        Survey survey = new Survey();
        survey.setSurveyDate(new GregorianCalendar(2015,01,22).getTime());
        survey.setProtocolNumber("1");
        survey.setChild(child);
        survey = surveyRepository.save(survey);

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=0&targetEndAge=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(0)));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=1&targetEndAge=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=2&targetEndAge=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(0)));
    }

}
