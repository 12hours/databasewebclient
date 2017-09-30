package by.bockroir.app.dao;

import by.bockroir.app.domain.Child;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface ChildRepository extends PagingAndSortingRepository<Child, Long> {

    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    Page findByNameStartsWith(@Param("name") String name, Pageable p);

    @Query("select c from Child c "
            + "where (:familyName='' or c.familyName=:familyName)"
            + "and (:name='' or c.name=:name)"
            + "and (:patrName='' or c.patrName=:patrName)"
            + "and (:birthDate IS NULL or c.birthDate=:birthDate) ")
    Page identify(@Param("familyName") String familyName,
                  @Param("name") String name,
                  @Param("patrName") String patrName,
                  @Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate,
                  Pageable page);

    @Query("SELECT c FROM Child c "
            + "WHERE (c.familyName=:familyName) "
            + "AND (c.name=:name) "
            + "AND (c.patrName=:patrName) "
            + "AND (c.birthDate=:birthDate) ")
    public Child findUnique(
            @Param("familyName") String familyName,
            @Param("name") String name,
            @Param("patrName") String patrName,
            @Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate
            );

    @RestResource(path = "byFamilyName", rel = "byFamilyName")
    public Page findByFamilyNameIgnoreCase(@Param("familyName") String familyName, Pageable p);

    @RestResource(path = "findByBirthDate")
    Page findByBirthDate(@Param("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthDate,
                         Pageable page);
}

