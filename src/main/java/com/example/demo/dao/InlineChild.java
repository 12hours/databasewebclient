package com.example.demo.dao;

import com.example.demo.domain.Child;
import com.example.demo.domain.Survey;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;

@Projection(name = "inlineChild", types = {Survey.class})
public interface InlineChild {

    Long getId();

    Date getSurveyDate();

    String getProtocolNumber();

    String getChildName();

    String getDiagnosis();

    String getDeterminedDisorder();

    String getRecommendedProgramOfEducation();

    String getRecommendedFormOfEducation();

    String getRemarks();

    Child getChild();
}
