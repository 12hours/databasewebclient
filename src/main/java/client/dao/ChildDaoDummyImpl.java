package client.dao;

import client.domain.Child;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Repository(value = "dummyChildDao")
public class ChildDaoDummyImpl implements ChildDao {
    @Override
    public Child getChildById(long id) {
        Child child = new Child();
        child.setAddress("Some address...");
        child.setBirthDate(new Date(System.currentTimeMillis()));
        child.setDeterminedDisorder("Some disorder");
        child.setDisability(false);
        child.setDiagnosis("Some diagnosis");
        child.setFamilyName("Beliakov");
        child.setId(0);
        child.setName("Victor");
        child.setPatrName("Vladimirovich");
        child.setPlaceOfEducation("School 20");
        child.setProtocolNumber(42);
        child.setRecommendedFormOfEducation("Higher education");
        child.setRecommendedProgrammOfEducation("Regular programm of education");
        child.setRemarks("No remarks provided");
        child.setSpecialFacilitiesNeeding(false);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 6, 18);
        child.setSurveyDate(new Date(calendar.getTimeInMillis()));

        return child;
    }

    @Override
    public void saveChild(Child child) {

    }

    @Override
    public List<Child> getAllChildren() {
        return null;
    }
}
