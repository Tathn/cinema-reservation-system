package com.springframework.cinema.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.webflow.scope.ViewScope;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/");
		resolver.setSuffix(".xhtml");
		return resolver;
	}
	
	@Bean
	public static CustomScopeConfigurer viewScope() {
	    CustomScopeConfigurer configurer = new CustomScopeConfigurer();
	    HashMap<String, Object> map =  new HashMap<String, Object>();
	    map.put("view", new ViewScope());
	    configurer.setScopes(map);
	    System.out.println("SCOPEEEE " + map.containsKey("view"));
	    return configurer;
	}
}
