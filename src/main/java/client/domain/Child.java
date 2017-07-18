package client.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "children")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "survey_date")
    private Date surveyDate;

    @Column(name = "protocol_number")
    private int protocolNumber;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "name")
    private String name;

    @Column(name = "patr_name")
    private String patrName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "place_of_education")
    private String placeOfEducation;

    @Column(name = "address")
    private String address;

    @Column(name = "disability")
    private boolean disability;

    @Column(name = "special_needs")
    private boolean specialFacilitiesNeeding;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "determined_disorder")
    private String determinedDisorder;

    @Column(name = "recommended_programm")
    private String recommendedProgrammOfEducation;

    @Column(name = "recommended_form")
    private String recommendedFormOfEducation;

    @Column(name = "remarks")
    private String remarks;

    public void setId(int id) {
        this.id = id;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatrName(String patrName) {
        this.patrName = patrName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPlaceOfEducation(String placeOfEducation) {
        this.placeOfEducation = placeOfEducation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDisability(boolean disability) {
        this.disability = disability;
    }

    public void setSpecialFacilitiesNeeding(boolean specialFacilitiesNeeding) {
        this.specialFacilitiesNeeding = specialFacilitiesNeeding;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setDeterminedDisorder(String determinedDisorder) {
        this.determinedDisorder = determinedDisorder;
    }

    public void setRecommendedProgrammOfEducation(String recommendedProgrammOfEducation) {
        this.recommendedProgrammOfEducation = recommendedProgrammOfEducation;
    }

    public void setRecommendedFormOfEducation(String recommendedFormOfEducation) {
        this.recommendedFormOfEducation = recommendedFormOfEducation;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getName() {
        return name;
    }

    public String getPatrName() {
        return patrName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPlaceOfEducation() {
        return placeOfEducation;
    }

    public String getAddress() {
        return address;
    }

    public boolean getDisability() {
        return disability;
    }

    public boolean getSpecialFacilitiesNeeding() {
        return specialFacilitiesNeeding;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getDeterminedDisorder() {
        return determinedDisorder;
    }

    public String getRecommendedProgrammOfEducation() {
        return recommendedProgrammOfEducation;
    }

    public String getRecommendedFormOfEducation() {
        return recommendedFormOfEducation;
    }

    public String getRemarks() {
        return remarks;
    }
}

