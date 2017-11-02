package by.bockroir.app;

import by.bockroir.app.dao.ChildRepository;
import by.bockroir.app.dao.SurveyRepository;
import by.bockroir.app.domain.Child;
import by.bockroir.app.domain.Survey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.context.ActiveProfiles;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/clean.sql")
@ActiveProfiles("test")
public class ApiAgeTests {

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
    @Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/clean.sql")
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.surveys[*].diagnosis", hasItem("NA diagnosis")));
    }

    @Test
    public void ageTestMinus0() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(jsonPath("$._embedded.surveys", hasSize(0)));

        Child child = new Child();
        child.setName("name");
        child.setFamilyName("familyName");
        child.setPatrName("patrName");
        child.setBirthDate(new GregorianCalendar(2015,01,22).getTime());
//        child = childRepository.save(child);

        Survey survey = new Survey();
        survey.setSurveyDate(new GregorianCalendar(2015,01,21).getTime());
        survey.setProtocolNumber("1");
        survey.setChild(child);
        survey = surveyRepository.save(survey);

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=0&targetEndAge=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(0)));
    }

    @Test
    public void ageTest0() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(jsonPath("$._embedded.surveys", hasSize(0)));

        Child child = new Child();
        child.setName("name");
        child.setFamilyName("familyName");
        child.setPatrName("patrName");
        child.setBirthDate(new GregorianCalendar(2014,01,22).getTime());
//        child = childRepository.save(child);

        Survey survey = new Survey();
        survey.setSurveyDate(new GregorianCalendar(2015,01,21).getTime());
        survey.setProtocolNumber("1");
        survey.setChild(child);
        survey = surveyRepository.save(survey);

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=0&targetEndAge=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=1&targetEndAge=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(0)));
    }

    @Test
    public void ageTest1() throws Exception {
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
                .andExpect(jsonPath("$.page.totalElements", is(0)));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=1&targetEndAge=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=2&targetEndAge=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(0)));
    }

    @Test
    public void ageTestMixed() throws Exception {
        mockMvc.perform(get("/api/surveys"))
                .andExpect(jsonPath("$._embedded.surveys", hasSize(0)));

        // age 0
        Child child = new Child();
        child.setName("Child0");
        child.setFamilyName("familyName");
        child.setPatrName("patrName");
        child.setBirthDate(new GregorianCalendar(2014,01,22).getTime());
        Survey survey = new Survey();
        survey.setSurveyDate(new GregorianCalendar(2015,01,21).getTime());
        survey.setProtocolNumber("0");
        survey.setChild(child);
        survey = surveyRepository.save(survey);
        
        // age 1
        Child child1 = new Child();
        child1.setName("Child1");
        child1.setFamilyName("familyName");
        child1.setPatrName("patrName");
        child1.setBirthDate(new GregorianCalendar(2014,01,22).getTime());
        Survey survey1 = new Survey();
        survey1.setSurveyDate(new GregorianCalendar(2015,01,22).getTime());
        survey1.setProtocolNumber("1");
        survey1.setChild(child1);
        survey1 = surveyRepository.save(survey1);
        
        // age 3
        Child child3 = new Child();
        child3.setName("Child3");
        child3.setFamilyName("familyName");
        child3.setPatrName("patrName");
        child3.setBirthDate(new GregorianCalendar(2014,01,22).getTime());
        Survey survey3 = new Survey();
        survey3.setSurveyDate(new GregorianCalendar(2017,01,22).getTime());
        survey3.setProtocolNumber("3");
        survey3.setChild(child3);
        survey3 = surveyRepository.save(survey3);

        // age 6
        Child child5 = new Child();
        child5.setName("Child6");
        child5.setFamilyName("familyName");
        child5.setPatrName("patrName");
        child5.setBirthDate(new GregorianCalendar(2014,01,22).getTime());
        Survey survey5 = new Survey();
        survey5.setSurveyDate(new GregorianCalendar(2020,01,22).getTime());
        survey5.setProtocolNumber("6");
        survey5.setChild(child5);
        survey5 = surveyRepository.save(survey5);

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=0&targetEndAge=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)))
                .andExpect(jsonPath("$._embedded.surveys[0].protocolNumber", is("0")));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=1&targetEndAge=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)))
                .andExpect(jsonPath("$._embedded.surveys[0].protocolNumber", is("1")));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=3&targetEndAge=6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)))
                .andExpect(jsonPath("$._embedded.surveys[0].protocolNumber", is("3")));

        mockMvc.perform(get("/api/surveys/search/byNameAndDate?targetStartAge=6&targetEndAge=18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.surveys", hasSize(1)))
                .andExpect(jsonPath("$._embedded.surveys[0].protocolNumber", is("6")));
    }

}
