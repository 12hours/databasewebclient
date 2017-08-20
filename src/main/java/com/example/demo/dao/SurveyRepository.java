package com.example.demo.dao;

import com.example.demo.domain.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//@RepositoryRestResource(excerptProjection = InlineChild.class)
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
        "(:childName IS NULL OR (1 = LOCATE(:childName, s.childName))) AND "+
        "(:start IS NULL OR s.surveyDate >= :start) AND" +
        "(:end IS NULL OR s.surveyDate <= :end)")
    public Page findByChildNameAndDateBetween(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date
                                                          start,
                                              @Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                              @Param("childName") String childName,
                                              Pageable p);

}
