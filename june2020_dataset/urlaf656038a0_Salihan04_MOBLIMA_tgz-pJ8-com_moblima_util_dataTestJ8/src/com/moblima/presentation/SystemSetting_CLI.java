package com.moblima.presentation;

import com.moblima.dataaccess.*;
import com.moblima.model.*;
import com.moblima.util.ConsoleReader;
import com.moblima.businesslogic.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SystemSetting_CLI {
	private TicketPriceBL ticketPriceBL;
	private PublicHolidayBL publicHolidayBL;
	
	public SystemSetting_CLI(){
		ticketPriceBL = new TicketPriceBL();
		publicHolidayBL = new PublicHolidayBL();
	}
	
	public static void main(String [] args){
		SystemSetting_CLI cli = new SystemSetting_CLI();
		cli.show();
	}
	
	public void show(){
		int choice;
		boolean cont=true;
		do{
		System.out.println("");
		System.out.println("=========================");
		System.out.println("    SYSTEM SETTINGS");
		System.out.println("=========================");
		System.out.println("");
		System.out.println("Please choose the system settings that you want to change: ");
		System.out.println("1:\tTicket Price Rates");
		System.out.println("2:\tPublic Holidays");
		System.out.println("3:\tBack");
		System.out.print("Please enter your choice : ");
		choice = ConsoleReader.readIntInput();
		switch(choice){
			case 1:
				showTicketPriceSettings();
				break;
			case 2:
				showPublicHolidaySettings();
				break;
			case 3:
				cont=false;
				break;
			default:
				System.out.println("You entered an invalid choice.");	
			}
		
		}while(cont);
		
		
	}
	
	public void showTicketPriceSettings(){
		int ticketPriceChoice, priceRateChoice;
		float newPrice;
		System.out.println("");
		System.out.println("=================================");
		System.out.println("      Ticket Price Settings");
		System.out.println("=================================");
		System.out.println("");
		System.out.println("Current price settings are:");
		TicketPrice ticketPrice = new TicketPrice();
		TicketPrice ticketPrice1 = ticketPriceBL.getTicketPrice(0);
		System.out.println("");
		System.out.println("1) General Prices: ");
		System.out.println("------------------");
		System.out.println("");
		displayPrices(ticketPrice1);
		
		TicketPrice ticketPrice2 = ticketPriceBL.getTicketPrice(1);
		System.out.println("");
		System.out.println("2) 3d Movies Prices: ");
		System.out.println("--------------------");
		System.out.println("");
		displayPrices(ticketPrice2);
		System.out.println("");
		System.out.println("============================================================");
		System.out.println("");
		System.out.print("Please input the index for the type of ticket prices you would like to change (Enter -1 to  exit) : ");
		ticketPriceChoice = ConsoleReader.readIntInput();
		System.out.println();
		
		switch(ticketPriceChoice){
			case 1:
				System.out.println("You have chosen general ticket prices:");
				System.out.println("");
				displayPrices(ticketPrice1);
				ticketPrice = ticketPrice1;
				System.out.println("============================================================");
				System.out.println("Please enter your which attribute you would like to change:");
				choicesForTicketPrices();
				System.out.print("Please enter your choice : ");
				priceRateChoice = ConsoleReader.readIntInput();
				break;
			case 2:
				System.out.println("You have chosen 3d movie ticket prices:");
				System.out.println("");
				displayPrices(ticketPrice2);
				ticketPrice = ticketPrice2;
				System.out.println("============================================================");
				System.out.println("Please enter your which attribute you would like to change:");
				choicesForTicketPrices();
				System.out.print("Please enter your choice (Enter -1 to exit : ");
				priceRateChoice = ConsoleReader.readIntInput();
				break;
			case -1:
				priceRateChoice = 0;
				return;
			default:
				priceRateChoice = 0;
				System.out.println("Invalid choice");
				return;
		}
		
		//entering new price
		System.out.println("Please enter the new price : ");
		newPrice = ConsoleReader.readFloatInput();
		switch(priceRateChoice){
		case 1:
			ticketPrice.setStudentPricing(newPrice);
			break;
		case 2:
			ticketPrice.setSeniorCitizenPricing(newPrice);
			break;
		case 3:
			ticketPrice.setPlatinumTicketPricing(newPrice);
			break;
		case 4:
			ticketPrice.set_THU_pricing(newPrice);
			break;
		case 5:
			ticketPrice.set_SAT_SUN_PH_pricing(newPrice);
			break;
		case 6:
			ticketPrice.set_MON_WED_pricing(newPrice);
			break;
		case 7:
			ticketPrice.set_FRI_EVE_PH_from_6_pricing(newPrice);
			break;
		case 8:
			ticketPrice.set_FRI_EVE_PH_before_6_pricing(newPrice);
			break;
		default:
			System.out.println("InvalidChoice");
			break;
		}
		
		ticketPriceBL.setTicketPrice(ticketPrice, ticketPriceChoice-1);

	}
	
	public void showPublicHolidaySettings(){
		int settingsToChangeChoice;
		Date date;
		String name;
		int yr,mth,day,hr,min;
		int id;
		Calendar calendar = Calendar.getInstance();
		
		System.out.println("");
		System.out.println("=================================");
		System.out.println("Public Holiday Settings");
		System.out.println("=================================");
		System.out.println("");
		System.out.println("Current list of public holidays: ");
		System.out.println("");
		displayListOfPublicHolidays();
		System.out.println("");
		System.out.println("=============================="); 
		System.out.println("Please State what you would like to: ");
		System.out.println("1: Create New Public Holiday");
		System.out.println("2: Remove a Public Holiday");
		System.out.println("3: Back");
		System.out.println("=====================================");
		System.out.print("Please enter your choice : ");
		settingsToChangeChoice = ConsoleReader.readIntInput();
		switch(settingsToChangeChoice){
			case 1:
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				System.out.print("Please enter the date (DD/MM/YYYY) : ");
				date = ConsoleReader.readDateInput();
				
				System.out.print("Please enter The Name Of the Public Holiday : ");
				name = ConsoleReader.readString();
				publicHolidayBL.setPublicHoliday(date, name);
				break;
			case 2:
				System.out.print("Please enter the public holiday id to remove : ");
				id = ConsoleReader.readIntInput();
				publicHolidayBL.removePublicHoliday(id);
				break;
			case 3:
				System.out.println("Exiting....");
				break;
			default:
				System.out.println("invalid choice");
			
		}
		System.out.println("=========");
		System.out.println("|SUCCESS|");
		System.out.println("=========");
		
	}
	
	public void displayPrices(TicketPrice ticketPrice){
		System.out.println("Students:\t\t" + ticketPrice.getStudentPricing());
		System.out.println("Senior citizens:\t" + ticketPrice.getSeniorCitizenPricing());
		System.out.println("Platinum ticket prices:\t" + ticketPrice.getPlatinumTicketPricing());
		System.out.println("Thursday prices:\t" + ticketPrice.get_THU_pricing());
		System.out.println("Sat and Sun prices:\t" + ticketPrice.get_SAT_SUN_PH_pricing());
		System.out.println("Mon - Wed:\t\t" + ticketPrice.get_MON_WED_pricing());
		System.out.println("Fri after 6:\t\t" + ticketPrice.get_FRI_EVE_PH_from_6_price());
		System.out.println("Fri before 6:\t\t" + ticketPrice.get_FRI_EVE_PH_before_6_pricing());
	}
	
	public void choicesForTicketPrices(){
		System.out.println("1 - Students");
		System.out.println("2 - Senior citizens");
		System.out.println("3 - Platinum ticket pricing") ;
		System.out.println("4 - Thursday prices");
		System.out.println("5 - Sat and sun prices");
		System.out.println("6 - Mon - wed");
		System.out.println("7 - Fri after 6");
		System.out.println("8 - Fri before 6");
	}
	
	public void displayListOfPublicHolidays(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("ID\tName\t\tDate");
		System.out.println("--------------------------------");
		for(PublicHoliday ph: publicHolidayBL.getPublicHolidays()){
			System.out.println(ph.getPublicHolidayID() + ":\t" + ph.getName() + "\t" + dateFormat.format(ph.getDate()));
		}
		
	}
	
}
