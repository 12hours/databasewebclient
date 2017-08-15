package com.example.demo.dao.tx;

import com.example.demo.controllers.Api2Controller;
import com.example.demo.domain.Child;
import com.example.demo.util.DTOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Repository// JPA exceptions will be translated into DataAccessException
public class ChildDao extends TransactionalDao {

    static Logger logger = LogManager.getLogger(Api2Controller.class);

    public void saveChild(String token, Child child) {
        EntityManager entityManager = getEntityManager(token);
        if (child.getId() != null){
            Child childFound = entityManager.find(Child.class, child.getId());
            DTOUtils.copyProperties(child, childFound);
//            entityManager.merge(childFound);
//            entityManager.persist(childFound);
        } else {
            entityManager.persist(child);
        }
    }

}
