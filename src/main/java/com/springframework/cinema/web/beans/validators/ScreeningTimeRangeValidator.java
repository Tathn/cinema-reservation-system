package com.springframework.cinema.web.beans.validators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.primefaces.util.DateUtils;
import org.primefaces.validate.ClientValidator;

import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningService;
import com.springframework.cinema.domain.user.UserService;
import com.springframework.cinema.infrastructure.util.DateUtil;

@Named("screeningTimeRangeValidator")
public class ScreeningTimeRangeValidator implements Validator, ClientValidator { 
	
	@EJB
	private ScreeningService screeningService;

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		if(value == null) return;
		
		Screening screening = (Screening) uiComponent.getAttributes().get("screening");
		Collection<Screening> screenings = screeningService.findByDateAndRoom(screening.getDate(),screening.getRoom());
		screenings.addAll(screeningService.findByDateAndRoom(screening.getFinishesAt(),screening.getRoom()));
		screenings.addAll(screeningService.findByDateAndRoom(DateUtil.getPreviousDay(screening.getDate()),screening.getRoom()));
		for(Screening scr : screenings){
			if(scr.getStartsAt().compareTo(screening.getFinishesAt()) <= 0 &&
	    			screening.getStartsAt().compareTo(scr.getFinishesAt()) <= 0){
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors",
		    				"Screening you want to create is colliding with another screening. Please verify your input."));
			}
		}
//		Screening screening = (Screening) uiComponent.getAttributes().get("screening");
//		Collection<Screening> screenings = screeningService.findByDateAndRoom(screening.getDate(), screening.getRoom());
//		screenings.addAll(screeningService.findByDateAndRoom(DateUtil.getNextDay(screening.getDate()), screening.getRoom()));
//		screenings.addAll(screeningService.findByDateAndRoom(DateUtil.getPreviousDay(screening.getDate()), screening.getRoom()));
//		
//		for(Screening scr : screenings){
//			if(scr.getFinishesAt().before(scr.getStartsAt()))
//		}
//	    if(screening.getFinishesAt().before(screening.getStartsAt())) {
//	    	if(isColliding(screening, 1))
//	    		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors",
//	    				"Screening you want to create is colliding with another tommorow's screening. Please verify your input."));       
//	    } else {
//	    	if(isColliding(screening, -1))
//    			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors",
//	    				"Screening you want to create is colliding with another yesterday's screening. Please verify your input."));      
//	    }
//	    
//	    if(isColliding(screening, 0))
//			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form input has errors",
//    				"Screening you want to create is colliding with another today's screening. Please verify your input."));
	}
	
	/**
     * Check if screening is colliding with screenings from a day which is <tt>amountOfDays</tt> away from <tt>screening.getDate()</tt>
     * @return true if screenings start and finish time overlap and false if they do not
     */
//	private boolean isColliding(Screening screening, Integer amountOfDays ){
//		Calendar calendar = Calendar.getInstance(); 
//    	calendar.setTime(screening.getDate()); 
//    	calendar.add(Calendar.DATE, amountOfDays);
//    	Date dateToCheck = calendar.getTime();
//    	Collection<Screening> screenings = screeningService.findByDateAndRoom(dateToCheck, screening.getRoom());
//    	
//    	if(amountOfDays > 0) {
//			System.out.println("Im in amountOfDays > 0");
//    		for(Screening scr : screenings) {
//    			if(scr.getStartsAt().compareTo(screening.getFinishesAt()) <= 0){
//        			System.out.println("Collision detected!\nScreening: \n" + screening + "\n scr\n" + scr);
//        			return true;
//        		}
//    		}
//			
//		} else if (amountOfDays < 0) {
//			System.out.println("Im in amountOfDays < 0");
//			for(Screening scr : screenings) {
//				if(scr.getFinishesAt().before(scr.getStartsAt()))
//				if(scr.getFinishesAt().compareTo(screening.getStartsAt()) >= 0){
//	    			System.out.println("Collision detected!\nScreening: \n" + screening + "\n scr\n" + scr);
//	    			return true;
//	    		}
//				// if screening.start <= scr.start
//    		}
//		} else if(screening.getFinishesAt().before(screening.getStartsAt())) {
//			screenings = screeningService.findByDateAndRoom(new Date(), screening.getRoom());
//			for(Screening scr : screenings) {
//				if(scr.getStartsAt().compareTo(screening.getStartsAt()) >= 0)
//					return true;
//			}
//			System.out.println("Im in else");
//			screenings = screeningService.findByDateAndRoom(new Date(), screening.getRoom());
//			for(Screening scr : screenings) {
////				System.out.println("scr: " + scr + "\n\nscreening: " + screening);
////				System.out.println("scr.getStartsAt().compareTo(screening.getFinishesAt()) <= 0 = " + (scr.getStartsAt().compareTo(screening.getFinishesAt()) <= 0));
////				System.out.println("screening.getStartsAt().compareTo(scr.getFinishesAt()) <= 0 = " + (screening.getStartsAt().compareTo(scr.getFinishesAt()) <= 0) + "\n---------------------------------");
////				Date scrStartsAt = DateUtil.removeDate(scr.getStartsAt());
////				Date scrFinishesAt = DateUtil.removeDate(scr.getFinishesAt());
//				if(scr.getStartsAt().compareTo(screening.getFinishesAt()) <= 0 &&
//	    			screening.getStartsAt().compareTo(scr.getFinishesAt()) <= 0){
//					System.out.println("Collision detected!\nScreening: \n" + screening + "\n scr\n" + scr);
//	    			return true;
//	    		}
//			}
//		}
//    	System.out.println("Collision not detected!\nScreening: \n" + screening);
//    	return false;
//	}
//	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "custom.EmailValidator";
	}
	
	
}
