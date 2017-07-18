package client.service;

import client.domain.Child;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ChildrenServiceTest {
    @org.junit.Test
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

        String json = "{\"surveyDate\":\"2017-07-18\",\"protocolNumber\":42,\"familyName\":\"Beliakov\",\"name\":\"Victor\",\"patrName\":\"Vladimirovich\",\"birthDate\":\"2017-07-17\",\"placeOfEducation\":\"School 20\",\"address\":\"Some address...\",\"disability\":false,\"specialFacilitiesNeeding\":false,\"diagnosis\":\"Some diagnosis\",\"determinedDisorder\":\"Some disorder\",\"recommendedProgrammOfEducation\":\"Regular programm of education\",\"recommendedFormOfEducation\":\"Higher education\",\"remarks\":\"No remarks provided\"}";
        Child child = cs.createChildFromJson(json);
        assertThat(child.getName(), is("Victor"));
        assertThat(child.getFamilyName(), is("Beliakov"));
    }

}