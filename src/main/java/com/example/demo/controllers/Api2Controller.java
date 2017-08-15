package com.example.demo.controllers;


import com.example.demo.dao.tx.ChildDao;
import com.example.demo.dao.tx.SurveyDao;
import com.example.demo.domain.Child;
import com.example.demo.domain.Survey;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Repository
@RestController
@RequestMapping("/api2")
public class Api2Controller {

    static Logger logger = LogManager.getLogger(Api2Controller.class);

    private static final String COOKIE_TX_ID = "tx_id";

    private ChildDao childDao;
    private SurveyDao surveyDao;

    @Autowired
    public Api2Controller(ChildDao childDao, SurveyDao surveyDao) {
        this.childDao = childDao;
        this.surveyDao = surveyDao;
    }

    @PostMapping("/begin")
    public String beginTransaction(@CookieValue(value = COOKIE_TX_ID, required = false) String txToken,
                                   HttpServletResponse response) {
        if (txToken == null) {
            txToken = createNewToken();
            response.addCookie(new Cookie(COOKIE_TX_ID, txToken));
        }

        childDao.beginTransaction(txToken);

        return "{ transaction: 'active' }";
    }

    private String createNewToken() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(Math.abs(random.nextLong())) + String.valueOf(Math.abs(random.nextLong()));
    }

    @PostMapping("/commit")
    public String commitTransaction(@CookieValue(value = COOKIE_TX_ID, required = false) String txToken) {
        logger.debug("trying to commit " + txToken);
        childDao.commitTransaction(txToken);
        return null;
    }

    @PostMapping("/save/child")
    public String saveChild(@CookieValue(value = COOKIE_TX_ID, required = false) String txToken,
                             @RequestBody Child child) {
        logger.debug("saving child " + child.toString());
        childDao.saveChild(txToken, child);
        return null;
    }

    @PostMapping("/save/survey")
    public String saveSurvey(@CookieValue(value = COOKIE_TX_ID, required = true) String txToken,
                            @RequestBody Survey survey){
        logger.debug("saving child " + survey.toString());
        surveyDao.saveSurvey(txToken, survey);
        return null;
    }

}
