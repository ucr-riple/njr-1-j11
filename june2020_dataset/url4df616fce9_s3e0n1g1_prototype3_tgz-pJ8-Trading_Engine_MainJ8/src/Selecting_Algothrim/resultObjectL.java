package Selecting_Algothrim;

import java.sql.Time;


public class resultObjectL {

	public resultObjectL( Time averageTime, double aPercentage){
		time = averageTime;
		percentage = aPercentage;
	}
	
	public Time getTime(){
		return time;
	}
	public double getPercentage(){
		return percentage;
	}
	
	private Time time;
	private double percentage;
	
}
