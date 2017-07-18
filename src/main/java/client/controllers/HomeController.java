package client.controllers;

import client.domain.Child;
import client.service.ChildrenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

@Controller
public class HomeController {

    @Autowired
    ChildrenService childrenService;

    @RequestMapping("/")
    public String homePage() {
        return "/index.html";
    }




}
