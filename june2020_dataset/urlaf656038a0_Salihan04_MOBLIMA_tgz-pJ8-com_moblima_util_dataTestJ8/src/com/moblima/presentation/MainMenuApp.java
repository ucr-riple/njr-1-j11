package com.moblima.presentation;

import java.util.Scanner;

import com.moblima.businesslogic.CinemaBL;
import com.moblima.businesslogic.CineplexBL;
import com.moblima.businesslogic.StaffBL;
import com.moblima.model.Cinema;
import com.moblima.model.CinemaClass;
import com.moblima.model.Cineplex;
import com.moblima.model.Staff;
import com.moblima.util.ConsoleReader;

public class MainMenuApp {

	private static CineplexBL cineplexBL = new CineplexBL();
	private static CinemaBL cinemaBL = new CinemaBL();
	private static StaffBL staffBL = new StaffBL();

	public static void main(String[] args) {

		int choice;
		
		System.out.println("=========================================");
		System.out.println("|                MOBLIMA                |");
		System.out.println("|         BY  CZ2002 SSP5 Group 6       |");
		System.out.println("=========================================");

		do {
			System.out.println("Menu");
			System.out.println("----");
			System.out.println("1) Customer");
			System.out.println("2) Staff");
			System.out.println("3) Exit");
			System.out.print("Please enter choice: ");
			choice = ConsoleReader.readIntInput();
			System.out.println();

			switch(choice) {
			case 1:
				CustomerMenu customer = new CustomerMenu();
				customer.show();
				break;

			case 2:
				StaffMenu staff = new StaffMenu();
				staff.show();
				break;

			default:
				System.out.println("Exiting program...");
				break;
			}
		}while(choice < 3);
	}
}
