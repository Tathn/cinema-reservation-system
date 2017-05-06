package com.cinema.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Patryk on 2017-05-01.
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/login")
    public String initLoginForm(){
        return "login";
    }

}
