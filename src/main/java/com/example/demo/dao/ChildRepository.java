package com.example.demo.dao;

import com.example.demo.domain.Child;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public interface ChildRepository extends PagingAndSortingRepository<Child, Long> {

    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    Page findByNameStartsWith(@Param("name") String name, Pageable p);

    @Query("select c from Child c "
            + "where (:familyName='' or c.familyName=:familyName)"
            + "and (:name='' or c.name=:name)"
            + "and (:patrName='' or c.patrName=:patrName)"
            + "and (:birthDate=Null or c.birthDate=:birthDate) ")
    Page identify(@Param("familyName") String familyName,
                        @Param("name") String name,
                        @Param("patrName") String patrName,
                        @Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate,
            Pageable page);

    @RestResource(path = "byFamilyName", rel = "byFamilyName")
    public Page findByFamilyName(@Param("familyName") String familyName, Pageable p);

    @RestResource(path = "findByBd", rel = "findByBd")
    Page findByBirthDate(@Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate,
                Pageable page);
}

