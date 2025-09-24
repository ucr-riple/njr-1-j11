package com.moblima.presentation;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.moblima.util.ConsoleReader;

public class SalesReportView {

	Scanner scanner;

	public SalesReportView() {
		scanner = new Scanner(System.in);
	}

	public void printReport() {

		int choice;

		do {
			System.out.println("How do you want to print the Sale Revenue Report?");
			System.out.println("1) By Movie");
			System.out.println("2) By Cineplex");
			System.out.println("3) By Period");
			System.out.println("4) Back");
			System.out.print("Please enter choice: ");
		
			choice = ConsoleReader.readIntInput();
			System.out.println();

			switch(choice) {
			case 1:
				reportByMovie();
				System.out.println();
				break;
			case 2:
				reportByCineplex();
				System.out.println();
				break;
			case 3:
				reportByPeriod();
				System.out.println();
				break;
			default:
				break;
			}
		}while(choice != 4);
	}
	
	public void reportByMovie() {
		SalesReport sr = new SalesByMovie();
		sr.show();
	}
	
	public void reportByCineplex() {
		SalesReport sr = new SalesByCineplex();
		sr.show();
	}
	
	public void reportByPeriod() {
		
		int c;
		SalesReport sr;
		
		do{
			System.out.println("Choose period");
			System.out.println("1) Day");
			System.out.println("2) Month");
			System.out.println("3) Back");
			System.out.print("Enter choice: ");
			
			c = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
		}while(c>3 || c<1);
				
		switch(c) {
		case 1:
			sr = new SalesByDay();
			sr.show();
			System.out.println();
			break;
			
		case 2:
			sr = new SalesByMonth();
			sr.show();
			System.out.println();
			break;
			
		default:
			break;
		}
	}
	
	/*public static void main(String[] args) {
		SalesReportView view = new SalesReportView();
		view.printReport();
	}*/
}
