package com.springframework.cinema.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Patryk on 2017-05-23.
 */

@Controller
public class PagesController {
	
	 @GetMapping("/")
	 public String getMainPage(){
		 return "index";
	 }
}
