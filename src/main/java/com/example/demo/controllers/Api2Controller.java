package com.example.demo.controllers;


import com.example.demo.domain.Child;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Repository // JPA exceptions will be translated into DataAccessException
@RestController
@RequestMapping("/api2")
public class Api2Controller {

    static Logger logger = LogManager.getLogger(Api2Controller.class);

    private static final String COOKIE_TX_ID = "tx_id";

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Map<String, EntityManager> map = new HashMap<>();

    @PostMapping("/begin")
    public String beginTransaction(@CookieValue(value = COOKIE_TX_ID, required = false) String txCookie,
                                   HttpServletResponse response) {
        if (txCookie == null || map.containsKey(txCookie) == false) {
            EntityManager entityManager = emf.createEntityManager();
            txCookie = createNewToken();
            map.put(txCookie, entityManager);
            response.addCookie(new Cookie(COOKIE_TX_ID, txCookie));
        }
        logger.info("beginning transaction with " + txCookie);

        EntityManager entityManager = map.get(txCookie);
        EntityTransaction tx = entityManager.getTransaction();
        if (!tx.isActive()) {
            entityManager.getTransaction().begin();
        }

        return "{ transaction: 'active' }";
    }

    private String createNewToken() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(Math.abs(random.nextLong())) + String.valueOf(Math.abs(random.nextLong()));
    }

    @PostMapping("/save/child")
    public String saveSurvey(@CookieValue(value = COOKIE_TX_ID, required = false) String txCookie,
                             Child child) {
        logger.debug("saving child " + child.toString());
        return null;
    }


}
