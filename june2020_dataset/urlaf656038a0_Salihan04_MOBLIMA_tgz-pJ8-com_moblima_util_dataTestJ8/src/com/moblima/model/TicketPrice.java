package com.moblima.model;

import java.io.Serializable;
import java.util.Date;

public class TicketPrice implements Serializable {
	
	private Float senior_citizen_price;
	private Float student_price;
	private Float platinum_suite_price;
	
	private Float _MON_WED_price; // Mon - Wed (all sessions)
	private Float _THU_price; // Thu (sessions before 5pm)
	private Float _FRI_EVE_PH_from_6_price; // Fri & Eve of PH (sessions before 6pm)
	private Float _FRI_EVE_PH_before_6_price; // Fri & Eve of PH (sessions from 6pm)
	private Float _SAT_SUN_PH_price; // Sat, Sun and P.H (all sessions)
	
	public TicketPrice() {
	}
	
	public void setPlatinumTicketPricing(float price) {
		platinum_suite_price = price;
	}
	
	public float getPlatinumTicketPricing() {
		return platinum_suite_price;
	}
	
	public void setSeniorCitizenPricing(float price) {
		senior_citizen_price = price;
	}
	
	public float getSeniorCitizenPricing() {
		return senior_citizen_price;
	}
	
	public void setStudentPricing(float price) {
		student_price = price;
	}
	
	public float getStudentPricing() {
		return student_price;
	}
	
	public void set_MON_WED_pricing(float price) {
		_MON_WED_price = price;
	}
	
	public float get_MON_WED_pricing() {
		return _MON_WED_price;
	}
	
	public void set_THU_pricing(float price) {
		_THU_price = price;
	}
	
	public float get_THU_pricing() {
		return _THU_price;
	}
	
	public void set_FRI_EVE_PH_from_6_pricing(float price) {
		_FRI_EVE_PH_from_6_price = price;
	}
	
	public float get_FRI_EVE_PH_from_6_price() {
		return _FRI_EVE_PH_from_6_price;
	}
	
	public void set_FRI_EVE_PH_before_6_pricing(float price) {
		_FRI_EVE_PH_before_6_price = price;
	}
	
	public float get_FRI_EVE_PH_before_6_pricing() {
		return _FRI_EVE_PH_before_6_price;
	}
	
	public void set_SAT_SUN_PH_pricing(float price) {
		_SAT_SUN_PH_price = price;
	}
	
	public float get_SAT_SUN_PH_pricing() {
		return _SAT_SUN_PH_price;
	}
}
