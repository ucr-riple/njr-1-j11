package com.moblima.presentation;

import java.util.Scanner;

import com.moblima.businesslogic.StaffBL;
import com.moblima.util.ConsoleReader;

public class StaffMenu {

	private StaffBL staffBL = new StaffBL();
	private MovieListing_CLI movieListingView = new MovieListing_CLI();
	private ManageShowTimes_CLI manageShowTimes = new ManageShowTimes_CLI();
	private SystemSetting_CLI systemSetting = new SystemSetting_CLI();
	private SalesReportView view = new SalesReportView();
	private String username;
	private String password;

	public void show() {

		int choice;
		boolean tryAgain;
		
		do{
			System.out.print("Enter Username: ");
			username = ConsoleReader.readString();
	
			System.out.print("Enter Password: ");
			password = ConsoleReader.readString();
	
			tryAgain = !staffBL.isValid(username, password);
			
			if(tryAgain) {
				System.out.println("Login Invalid! Please try again.");
			}
		}
		while(tryAgain);

		System.out.println();
		
		do {
			System.out.println("1) Create/ Update/ Remove movie listing");
			System.out.println("2) Create/ Update/ Remove cinema showtimes and the movies to be shown");
			System.out.println("3) Print sale revenue report by movie, cinema and period");
			System.out.println("4) Configure system settings");
			System.out.println("5) Back");

			System.out.print("Please enter choice: ");
			choice = ConsoleReader.readIntInput();
			System.out.println();

			switch(choice) {
			case 1:
				movieListingView.show();
				System.out.println();
				break;

			case 2:
				manageShowTimes.showMenu();
				System.out.println();
				break;

			case 3:
				view.printReport();
				System.out.println();
				break;

			case 4:
				systemSetting.show();
				System.out.println();
				break;

			default:
				break;
			}

		}while(choice < 5);
	}
}
