package com.example.demo.dao;

import com.example.demo.domain.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(excerptProjection = InlineChild.class)
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long>{

    @RestResource(path = "byProtocolNumber", rel = "byProtocolNumber")
    public Page findByProtocolNumberStartsWith(@Param("protocolNumber") String protocolNumber, Pageable p);

    @RestResource(path = "byChildName", rel = "byChildName")
    public Page findByChildNameStartsWith(@Param("childName") String childName, Pageable p);


}
