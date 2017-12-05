package by.bockroir.app.dao;

import by.bockroir.app.domain.Survey;
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
    Page findByProtocolNumberStartsWith(@Param("protocolNumber") String protocolNumber, Pageable p);


    @RestResource(path = "byChildName", rel = "byChildName")
    Page findByChildNameStartsWith(@Param("childName") String childName, Pageable p);

    @RestResource(path = "byDateBetween")
    Page findBySurveyDateBetween(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date
                                         start,
                                 @Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                 Pageable p);

    @Deprecated
//    @RestResource(path = "byNameAndDate")
    @Query("SELECT DISTINCT s FROM Survey s " +
            "LEFT JOIN s.diagnoses diagnoses LEFT JOIN s.disorders disorders " +
            "LEFT JOIN s.educationPrograms educationPrograms LEFT JOIN s.recommendations recommendations " +
            "WHERE " +
            "(:childName IS NULL OR (UPPER(s.childName) LIKE UPPER(:childName)||'%' )) AND " +
            "(:birthDateStart IS NULL OR s.child.birthDate >= :birthDateStart) AND " +
            "(:birthDateEnd IS NULL OR s.child.birthDate <= :birthDateEnd) AND " +
            "(:surveyDateStart IS NULL OR s.surveyDate >= :surveyDateStart) AND " +
            "(:surveyDateEnd IS NULL OR s.surveyDate <= :surveyDateEnd) AND " +
            "(:diagnosisId IS NULL OR diagnoses.id = :diagnosisId) AND " +
            "(:disorderId IS NULL OR disorders.id = :disorderId) AND " +
            "(:educationProgramId IS NULL OR educationPrograms.id = " +
            ":educationProgramId) AND " +
            "(:recommendationId IS NULL OR recommendations.id = :recommendationId) AND " +
            "(:targetStartAge IS NULL OR " +
            "(HOWOLD(s.child.birthDate, s.surveyDate) >= 0 AND HOWOLD(s.child.birthDate, s.surveyDate) >= :targetStartAge))" +
            " AND " +
            "(:targetEndAge IS NULL OR " +
            "HOWOLD(s.child.birthDate, s.surveyDate) < :targetEndAge) AND " +
            "(:regionId IS NULL OR s.child.region.id = :regionId)"
    )
    Page complexSearch(@Param("surveyDateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               Date surveyDateStart,
                       @Param("surveyDateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               Date surveyDateEnd,
                       @Param("birthDateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               Date birthDateStart,
                       @Param("birthDateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               Date birthDateEnd,
                       @Param("childName") String childName,
                       @Param("diagnosisId") Long diagnosisId,
                       @Param("disorderId") Long disorderId,
                       @Param("educationProgramId") Long educationProgramId,
                       @Param("recommendationId") Long recommendationId,
                       @Param("targetStartAge") Integer targetStartAge,
                       @Param("targetEndAge") Integer targetEndAge,
                       @Param("regionId") Long regionId,
                       Pageable p);

    @RestResource(path = "byNameAndDate")
    @Query(value = "SELECT s FROM Survey s " +
            "WHERE " +
            "(:diagnosisId IS NULL " +
            "   OR EXISTS ( SELECT diagnosis FROM Diagnosis diagnosis WHERE diagnosis MEMBER OF s.diagnoses " +
            "               AND diagnosis.id = :diagnosisId) " +
            ") " +
            "AND " +
            "(:disorderId IS NULL " +
            "   OR EXISTS ( SELECT disorder FROM Disorder disorder WHERE disorder MEMBER OF s.disorders " +
            "               AND disorder.id = :disorderId) " +
            ") " +
            "AND " +
            "(:educationProgramId IS NULL " +
            "   OR EXISTS ( SELECT educationProgram FROM EducationProgram educationProgram " +
            "               WHERE educationProgram MEMBER OF s.educationPrograms " +
            "               AND educationProgram.id = :educationProgramId) " +
            ") " +
            "AND " +
            "(:recommendationId IS NULL " +
            "   OR EXISTS ( SELECT recommendation FROM Recommendation recommendation " +
            "               WHERE recommendation MEMBER OF s.recommendations " +
            "               AND recommendation.id = :recommendationId) " +
            ") " +
            "AND " +
            "(:childName IS NULL OR (UPPER(s.childName) LIKE UPPER(:childName)||'%' )) AND " +
            "(:birthDateStart IS NULL OR s.child.birthDate >= :birthDateStart) AND " +
            "(:birthDateEnd IS NULL OR s.child.birthDate <= :birthDateEnd) AND " +
            "(:surveyDateStart IS NULL OR s.surveyDate >= :surveyDateStart) AND " +
            "(:surveyDateEnd IS NULL OR s.surveyDate <= :surveyDateEnd) AND " +
            "(:targetStartAge IS NULL OR " +
            "(HOWOLD(s.child.birthDate, s.surveyDate) >= 0 AND HOWOLD(s.child.birthDate, s.surveyDate) >= :targetStartAge))" +
            " AND " +
            "(:targetEndAge IS NULL OR " +
            "HOWOLD(s.child.birthDate, s.surveyDate) < :targetEndAge) AND " +
            "(:regionId IS NULL OR s.child.region.id = :regionId)" +
            "ORDER BY TONUM(s.protocolNumber)"
    , countQuery = "SELECT COUNT(s.id) FROM Survey s " +
            "WHERE " +
            "(:diagnosisId IS NULL " +
            "   OR EXISTS ( SELECT diagnosis FROM Diagnosis diagnosis WHERE diagnosis MEMBER OF s.diagnoses " +
            "               AND diagnosis.id = :diagnosisId) " +
            ") " +
            "AND " +
            "(:disorderId IS NULL " +
            "   OR EXISTS ( SELECT disorder FROM Disorder disorder WHERE disorder MEMBER OF s.disorders " +
            "               AND disorder.id = :disorderId) " +
            ") " +
            "AND " +
            "(:educationProgramId IS NULL " +
            "   OR EXISTS ( SELECT educationProgram FROM EducationProgram educationProgram " +
            "               WHERE educationProgram MEMBER OF s.educationPrograms " +
            "               AND educationProgram.id = :educationProgramId) " +
            ") " +
            "AND " +
            "(:recommendationId IS NULL " +
            "   OR EXISTS ( SELECT recommendation FROM Recommendation recommendation " +
            "               WHERE recommendation MEMBER OF s.recommendations " +
            "               AND recommendation.id = :recommendationId) " +
            ") " +
            "AND " +
            "(:childName IS NULL OR (UPPER(s.childName) LIKE UPPER(:childName)||'%' )) AND " +
            "(:birthDateStart IS NULL OR s.child.birthDate >= :birthDateStart) AND " +
            "(:birthDateEnd IS NULL OR s.child.birthDate <= :birthDateEnd) AND " +
            "(:surveyDateStart IS NULL OR s.surveyDate >= :surveyDateStart) AND " +
            "(:surveyDateEnd IS NULL OR s.surveyDate <= :surveyDateEnd) AND " +
            "(:targetStartAge IS NULL OR " +
            "(HOWOLD(s.child.birthDate, s.surveyDate) >= 0 AND HOWOLD(s.child.birthDate, s.surveyDate) >= :targetStartAge))" +
            " AND " +
            "(:targetEndAge IS NULL OR " +
            "HOWOLD(s.child.birthDate, s.surveyDate) < :targetEndAge) AND " +
            "(:regionId IS NULL OR s.child.region.id = :regionId)"
    )
    Page complexSearch2(@Param("surveyDateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                Date surveyDateStart,
                        @Param("surveyDateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                Date surveyDateEnd,
                        @Param("birthDateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                Date birthDateStart,
                        @Param("birthDateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                Date birthDateEnd,
                        @Param("childName") String childName,
                        @Param("diagnosisId") Long diagnosisId,
                        @Param("disorderId") Long disorderId,
                        @Param("educationProgramId") Long educationProgramId,
                        @Param("recommendationId") Long recommendationId,
                        @Param("targetStartAge") Integer targetStartAge,
                        @Param("targetEndAge") Integer targetEndAge,
                        @Param("regionId") Long regionId,
                        Pageable p);


    @RestResource(path = "complex")
    @Query(value = "SELECT * FROM surveys " +
            "WHERE " +
            "(:diagnosisId IS NULL OR EXISTS (  " +
            "    SELECT * FROM surveys_diagnoses" +
            "    JOIN diagnoses ON diagnoses.id = surveys_diagnoses.diagnosis_id" +
            "    WHERE surveys.survey_id = surveys_diagnoses.survey_id" +
            "    AND diagnoses.id = :diagnosisId)" +
            ") " +
            "AND " +
            "(:disorderId IS NULL OR EXISTS ( " +
            "    SELECT * FROM surveys_disorders" +
            "    JOIN disorders ON disorders.id = surveys_disorders.disorder_id" +
            "    WHERE surveys.survey_id = surveys_disorders.survey_id" +
            "    AND disorders.id = :disorderId)" +
            ") " +
            "AND " +
            "(:educationProgramId IS NULL OR EXISTS (" +
            "    SELECT * FROM surveys_edu_programs" +
            "    JOIN education_programs ON education_programs.id = surveys_edu_programs.edu_pr_id" +
            "    WHERE surveys.survey_id = surveys_edu_programs.survey_id" +
            "    AND education_programs.id = :educationProgramId)" +
            ") " +
            "AND " +
            "(:recommendationId IS NULL OR EXISTS (" +
            "    SELECT * FROM surveys_recommendations" +
            "    JOIN recommendations ON recommendations.id = surveys_recommendations.rec_id" +
            "    WHERE surveys.survey_id = surveys_recommendations.survey_id" +
            "    AND recommendations.id = :recommendationId)" +
            ") " +
            "ORDER BY survey_id \n-- #pageable\n" +
            " LIMIT ?;", nativeQuery = true,
            countQuery = "SELECT COUNT(1) FROM surveys " +
                    "WHERE " +
                    "(:diagnosisId IS NULL OR EXISTS (  " +
                    "    SELECT * FROM surveys_diagnoses" +
                    "    JOIN diagnoses ON diagnoses.id = surveys_diagnoses.diagnosis_id" +
                    "    WHERE surveys.survey_id = surveys_diagnoses.survey_id" +
                    "    AND diagnoses.id = :diagnosisId)" +
                    ") " +
                    "AND " +
                    "(:disorderId IS NULL OR EXISTS (" +
                    "    SELECT * FROM surveys_disorders" +
                    "    JOIN disorders ON disorders.id = surveys_disorders.disorder_id" +
                    "    WHERE surveys.survey_id = surveys_disorders.survey_id" +
                    "    AND disorders.id = ?2;disorderId)" +
                    ") " +
                    "AND " +
                    "(:educationProgramId IS NULL OR EXISTS (" +
                    "    SELECT * FROM surveys_edu_programs" +
                    "    JOIN education_programs ON education_programs.id = surveys_edu_programs.edu_pr_id" +
                    "    WHERE surveys.survey_id = surveys_edu_programs.survey_id" +
                    "    AND education_programs.id = :educationProgamId)" +
                    ") " +
                    "AND " +
                    "(:recommendationId IS NULL OR EXISTS (" +
                    "    SELECT * FROM surveys_recommendations" +
                    "    JOIN recommendations ON recommendations.id = surveys_recommendations.rec_id" +
                    "    WHERE surveys.survey_id = surveys_recommendations.survey_id" +
                    "    AND recommendations.id = :recommendationId)" +
                    ")" +
                    ";"
    )
    Page veryComplexSearch(//@Param("surveyDateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           //                           Date surveyDateStart,
//                       @Param("surveyDateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//                               Date surveyDateEnd,
//                       @Param("birthDateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//                               Date birthDateStart,
//                       @Param("birthDateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//                               Date birthDateEnd,
//                       @Param("childName") String childName,
                           @Param("diagnosisId") Long diagnosisId,
                           @Param("disorderId") Long disorderId,
                           @Param("educationProgramId") Long educationProgramId,
                           @Param("recommendationId") Long recommendationId,
//                       @Param("targetStartAge") Integer targetStartAge,
//                       @Param("targetEndAge") Integer targetEndAge,
//                       @Param("regionId") Long regionId,
                           Pageable p);


    @RestResource(path = "byBirthDate", rel = "byBirthDate")
    @Query("Select s from Survey s " +
            "WHERE " +
            "s.child.birthDate = :birthDate")
    Page findByChildBirthDate(@Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate,
                              Pageable p);


    @RestResource(path = "byDiagnosis", rel = "byDiagnosis")
    @Query("Select s from Survey s, IN (s.diagnoses) d WHERE " +
            "d.id = :id")
    Page findByDiagnosis(@Param("id") Long id, Pageable p);
    // sql:
//    select * from surveys
//    join surveys_diagnoses on
//    surveys_diagnoses.survey_id = surveys.survey_id
//    where exists (select * from diagnoses where 1 = surveys_diagnoses.diagnosis_id);

    @RestResource(path = "byDisorder", rel = "byDisorder")
    @Query("Select s from Survey s, IN (s.disorders) d WHERE " +
            "d.id = :id")
    Page findByDisorder(@Param("id") Long id, Pageable p);

    @RestResource(path = "byEducationProgram", rel = "byEducationProgram")
    @Query("Select s from Survey s, IN (s.educationPrograms) ep WHERE " +
            "ep.id = :id")
    Page findByEducationProgram(@Param("id") Long id, Pageable p);

    @RestResource(path = "byRecommendation", rel = "byRecommendation")
    @Query("Select s from Survey s, IN (s.recommendations) r WHERE " +
            "r.id = :id")
    Page findByRecommendation(@Param("id") Long id, Pageable p);


    @Query("SELECT s FROM Survey s WHERE " +
            "s.protocolNumber = :protocolNumber AND " +
            "s.surveyDate = :surveyDate")
    Survey findUnique(@Param("protocolNumber") String protocolNumber,
                      @Param("surveyDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date surveyDate);
}