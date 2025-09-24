package com.moblima.util;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ConsoleReader {

	static Calendar calendar = Calendar.getInstance();
	static Scanner scan = new Scanner(System.in);
	private static int intInput, hour, minute, day, month, year;
	private static float floatInput;
	private static String timeInput;
	private static String dateInput;
	private static String str;

	public static int readIntInput() {
		boolean tryAgain;

		do {
			tryAgain = false;
			try {
				intInput = Integer.parseInt(scan.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.print("Input must be an integer! Try again: ");
				tryAgain = true;
			}
		}while(tryAgain);
		
//		scan.nextLine(); //to flush the '\n' character

		return intInput;
	}
	
	public static float readFloatInput() {
		boolean tryAgain;

		do {
			tryAgain = false;
			try {
				floatInput = Float.parseFloat(scan.nextLine());
			}
			catch(NumberFormatException e) {
				System.out.print("Input must be an integer! Try again: ");
				tryAgain = true;
			}
		}while(tryAgain);
		
//		scan.nextLine(); //to flush the '\n' character

		return floatInput;
	}
	
	public static String readPassword() {
		Console console = System.console();
		return new String(console.readPassword());
	}

	public static String readTimeInput() {
		boolean tryAgain;

		do {
			tryAgain = false;
			timeInput = scan.nextLine();
			
			for(String time: timeInput.split(" ")) {
				if(!time.contains(":")){
					System.out.print("Input is in wrong format! Try again: ");
					tryAgain = true;
					break;
				}
				
				String[] time_hh_mm = time.split(":");
				
				
				if(time_hh_mm.length > 2) {
					System.out.print("Input is in wrong format! Try again: ");
					tryAgain = true;
				}
	
				hour = Integer.parseInt(time_hh_mm[0]);
				minute = Integer.parseInt(time_hh_mm[1]);
	
				if(hour < 0 || hour > 23 || minute < 0 || minute > 59) {
					System.out.print("The time input is invalid! Try again: ");
					tryAgain = true;
					break;
				}
			}
		}while(tryAgain);

		return timeInput;
	}
	
	public static Date readDateInput() {
		boolean tryAgain;
		do {
			tryAgain = false;
			dateInput = scan.nextLine();
			
			SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
			sdf.setLenient(false);
			try {
				return sdf.parse(dateInput);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.print("Date input is invalid! Try again: ");
				tryAgain = true;
			}
			
		}while(tryAgain);

		return null; // this is not gonna happen
	}
	
	public static String readDateString() {
		boolean tryAgain;
		do {
			tryAgain = false;
			dateInput = scan.nextLine();
			
			SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
			sdf.setLenient(false);
			try {
				sdf.parse(dateInput);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.print("Date input is invalid! Try again: ");
				tryAgain = true;
			}
			
		}while(tryAgain);

		return dateInput; // this is not gonna happen
	}
	
	// read seat no in XYY. X - Letter, Y - No
	public static String readSeatNumbers() {
		boolean tryAgain;
		
		do {
			tryAgain = false;
			str = scan.nextLine();
			
			for(String s: str.split(" ")) {
			
				if(s.length() < 2) {
					tryAgain = true;
				}
				else if(s.charAt(0) < 'A' && s.charAt(0) > 'Z') {
					tryAgain = true;
				}
				else {
					try {
						Integer.parseInt(s.substring(1, s.length()));
					} catch(NumberFormatException exception) {
						tryAgain = true;
					}
				}	
				
				if(tryAgain) {
					System.out.print("Seats No input is invalid! Try again: ");
					break;
				}
			}
			
		}while(tryAgain);
		
		return str;
	}
	
	public static String readString() {
		
		str = scan.nextLine();
		
		return str;
	}
	
	public static char readChar() {
		
		str = scan.nextLine();
		
		if(str.length() > 1)
			System.out.print("Input should be a character! Try again: ");
		
		return str.charAt(0);
	}
}
