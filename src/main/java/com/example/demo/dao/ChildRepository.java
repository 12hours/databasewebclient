package com.example.demo.dao;

import com.example.demo.domain.Child;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.text.SimpleDateFormat;


public interface ChildRepository extends CrudRepository<Child, Long> {

    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    public Page findByNameStartsWith(@Param("name") String name, Pageable p);

    @Query("select c from Child c "
            + "where (:familyName='' or c.familyName=:familyName)"
            + "and (:name='' or c.name=:name)"
            + "and (:patrName='' or c.patrName=:patrName)"
            + "and (:birthDate='' or c.birthDate=:birthDate) ")
            Page findBy(@Param("familyName") String familyName,
                        @Param("name") String name,
                        @Param("patrName") String patrName,
                        @DateTimeFormat(pattern = "yyyy-mm-dd")@Param("birthDate") Date birthDate,
            Pageable page);
}

