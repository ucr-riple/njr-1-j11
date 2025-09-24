package com.moblima.presentation;

import java.util.Scanner;

import com.moblima.util.ConsoleReader;

public class CustomerMenu {
	
	private SearchListMovieAndCreateBooking_CLI searchListBook = new SearchListMovieAndCreateBooking_CLI();
	private CheckBookingAndHistory_CLI check = new CheckBookingAndHistory_CLI();
	
	public CustomerMenu() {
	}

	public void show() {

		int choice;

		do {
			System.out.println("1) Search/ List movies for showtimes and booking");
			System.out.println("2) Check Booking status or Booking history");
			System.out.println("3) Back");
			System.out.print("Please enter choice: ");
			choice = ConsoleReader.readIntInput();
			System.out.println();
			
			switch(choice) {
			
			case 1:
				searchListBook.show();
				break;
				
			case 2:
				check.show();
				break;
				
			default:
				break;
			}
		}while(choice < 3);
	}
}
