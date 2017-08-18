package com.example.demo.dao.tx;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.HashMap;
import java.util.Map;

public abstract class TransactionalDao {

    @Resource(name = "entityHashMap")
    private Map<String, EntityManager> map = new HashMap<>();

    @PersistenceUnit
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager(String token) {
        return map.get(token);
    }

    public void beginTransaction(String token) {

        EntityManager entityManager = map.get(token);
        if (entityManager == null) {
            entityManager = emf.createEntityManager();
            map.put(token, entityManager);
        }

        EntityTransaction tx = entityManager.getTransaction();
        if (!tx.isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    public void commitTransaction(String token) {
        EntityManager entityManager = map.get(token);
        entityManager.getTransaction().commit();
    }


}
