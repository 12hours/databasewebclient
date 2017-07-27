package client.service;

import core.domain.Child;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChildrenServiceTest {

    protected Child getChild() {
        Child child = new Child();
        child.setAddress("Some address...");
        child.setBirthDate(new Date(System.currentTimeMillis()));
        child.setDisability(false);
        child.setFamilyName("Beliakov");
        child.setId(0);
        child.setName("Victor");
        child.setPatrName("Vladimirovich");
        child.setPlaceOfEducation("School 20");
        child.setSpecialFacilitiesNeeding(false);
        Calendar calendar = Calendar.getInstance();

        calendar.set(2017, 6, 18);
        return child;
    }

    @Test
    public void createChildFromJson() throws Exception {
        ChildrenService cs = new ChildrenService() {
            @Override
            public Child getChildById(long id) {
                return null;
            }

            @Override
            public void saveChild(Child child) {

            }
        };

        //{"surveyDate":"2017-07-18","protocolNumber":42,"familyName":"Beliakov","name":"Victor","patrName":"Vladimirovich","birthDate":"2017-07-17","placeOfEducation":"School 20","address":"Some address...","disability":false,"specialFacilitiesNeeding":false,"diagnosis":"Some diagnosis","determinedDisorder":"Some disorder","recommendedProgrammOfEducation":"Regular programm of education","recommendedFormOfEducation":"Higher education","remarks":"No remarks provided"}
        String json = "{\"surveyDate\":\"2017-07-18\",\"protocolNumber\":42,\"familyName\":\"Beliakov\",\"name\":\"Victor\",\"patrName\":\"Vladimirovich\",\"birthDate\":\"2017-07-17\",\"placeOfEducation\":\"School 20\",\"address\":\"Some address...\",\"disability\":false,\"specialFacilitiesNeeding\":false,\"diagnosis\":\"Some diagnosis\",\"determinedDisorder\":\"Some disorder\",\"recommendedProgrammOfEducation\":\"Regular programm of education\",\"recommendedFormOfEducation\":\"Higher education\",\"remarks\":\"No remarks provided\"}";
        Child child = cs.createChildFromJson(json);
        assertThat(child.getName(), is("Victor"));
        assertThat(child.getFamilyName(), is("Beliakov"));
    }

}