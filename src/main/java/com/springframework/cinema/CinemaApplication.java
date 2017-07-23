package com.springframework.cinema;


import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.sun.faces.config.ConfigureListener;

@SpringBootApplication
public class CinemaApplication {

	public static void main(String[] args) {
		/* Test */
		SpringApplication.run(CinemaApplication.class, args);
	}
	
	@Bean
    public ServletContextInitializer servletContextCustomizer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
                servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
//                servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
//                servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
//                servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
                servletContext.setInitParameter("primefaces.THEME", "midnight");
                servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/springsecurity.taglib.xml");
            }
        };
    }
	
	@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
        servletRegistrationBean.setName("facesServlet");
        return servletRegistrationBean;
    }
}
