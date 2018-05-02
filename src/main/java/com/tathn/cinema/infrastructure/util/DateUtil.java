package com.tathn.cinema.infrastructure.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dateUtil")
@Scope("application")
public class DateUtil {

	 public static Date now(){
		 return new Date();
	 }
	 
	 public static Date getNextDay(Date date) {
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(date);
	     cal.add(Calendar.DAY_OF_MONTH, 1);
	     return cal.getTime();
	 }
	 
	 public static Date getPreviousDay(Date date) {
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(date);
	     cal.add(Calendar.DAY_OF_MONTH, -1);
	     return cal.getTime();
	 }
	 
	 public static Date setDateTime(Date date, Date time) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        int day = cal.get(Calendar.DAY_OF_MONTH);
	        int month = cal.get(Calendar.MONTH);
	        int year = cal.get(Calendar.YEAR);
	        cal.setTime(time);
	        cal.set(year,month,day);
	        return cal.getTime();
	 }
	 
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public boolean filterByDate(Object value, Object filter, Locale locale) {
        if( filter == null ) { return false; }
        if( value == null ) { return false; }
        return ((Date)value).compareTo((Date)filter) == 0;
    }

}