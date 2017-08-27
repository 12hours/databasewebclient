package com.example.demo.dao;

import com.example.demo.domain.Survey;
import org.h2.index.PageBtree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long> {

    @RestResource(path = "byProtocolNumber", rel = "byProtocolNumber")
    public Page findByProtocolNumberStartsWith(@Param("protocolNumber") String protocolNumber, Pageable p);


    @RestResource(path = "byChildName", rel = "byChildName")
    public Page findByChildNameStartsWith(@Param("childName") String childName, Pageable p);

    @RestResource(path = "byDateBetween")
    public Page findBySurveyDateBetween(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date
                                                start,
                                        @Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                        Pageable p);

    @RestResource(path = "byNameAndDate")
    @Query("Select s from Survey s " +
            "WHERE " +
            "(:childName IS NULL OR (UPPER(s.childName) LIKE UPPER(:childName)||'%' )) AND " +
            "(:start IS NULL OR s.surveyDate >= :start) AND " +
            "(:end IS NULL OR s.surveyDate <= :end) ")
    public Page findByChildNameAndDateBetween(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date
                                                      start,
                                              @Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                              @Param("childName") String childName,
                                              Pageable p);


    @RestResource(path = "byBirthDate", rel = "byBirthDate")
    @Query("Select s from Survey s WHERE " +
            "s.child.birthDate = :birthDate")
    public Page findByChildBirthDate(@Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate,
                                     Pageable p);


    @RestResource(path = "byDiagnosis", rel = "byDiagnosis")
    @Query("Select s from Survey s, IN (s.diagnoses) d WHERE " +
            "d.id = :id")
    public Page findByDiagnosis(@Param("id") Long id, Pageable p);
    // sql:
//    select * from surveys
//    join surveys_diagnoses on
//    surveys_diagnoses.survey_id = surveys.survey_id
//    where exists (select * from diagnoses where 1 = surveys_diagnoses.diagnosis_id);

    @RestResource(path = "byDisorder", rel = "byDisorder")
    @Query("Select s from Survey s, IN (s.disorders) d WHERE " +
            "d.id = :id")
    public Page findByDisorder(@Param("id") Long id, Pageable p);

    @RestResource(path = "byEducationProgram", rel = "byEducationProgram")
    @Query("Select s from Survey s, IN (s.educationPrograms) ep WHERE " +
            "ep.id = :id")
    public Page findByEducationProgram(@Param("id") Long id, Pageable p);

    @RestResource(path = "byRecommendation", rel = "byRecommendation")
    @Query("Select s from Survey s, IN (s.recommendations) r WHERE " +
            "r.id = :id")
    public Page findByRecommendation(@Param("id") Long id, Pageable p);

}