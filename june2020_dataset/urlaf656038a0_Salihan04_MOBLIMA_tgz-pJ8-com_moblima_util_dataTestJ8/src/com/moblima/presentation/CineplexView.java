package com.moblima.presentation;

import java.util.List;
import java.util.Scanner;

import com.moblima.businesslogic.CineplexBL;
import com.moblima.model.Cineplex;
import com.moblima.util.ConsoleReader;

public class CineplexView {
	
	private CineplexBL cineplexController;
	
	public CineplexView() {
		cineplexController = new CineplexBL();
	}
	
	public void show() {
		int choice;
		
		do {
			System.out.println();
			System.out.println("Cineplex Menu");
			System.out.println("=============");
			System.out.println("1) List Cineplexes");
			System.out.println("2) Create Cineplex");
			System.out.println("3) Update Cineplex");
			System.out.println("4) Delete Cineplex");
			System.out.println("5) Back");
			
			System.out.print("Please enter your choice: ");
			choice = ConsoleReader.readIntInput();
			
			switch(choice) {
				case 1:
					showListCineplexes();
					break;
				case 2:
					showCreateCineplex();
					break;
				case 3:
					showUpdateCineplex();
					break;
				case 4:
					showDeleteCineplex();
					break;
			}
			
		}while(choice != 5);
	}
	
	private void showCreateCineplex() {
		String cineplexName;
		String location;
		
		System.out.println();
		
		System.out.println("New Cineplex");
		System.out.println("============");
		
		System.out.print("Enter Cineplex Location: ");
		cineplexName = ConsoleReader.readString();
		
		System.out.print("Enter Cineplex Location: ");
		location = ConsoleReader.readString();
		
		cineplexController.createCineplex(cineplexName, location);
	}
	
	private void showListCineplexes() {
		System.out.println();
		
		System.out.println("Cineplexes");
		System.out.println("==========");
		System.out.println("Cineplex Code\tCineplex Name");
		
		List<Cineplex> cineplexes = cineplexController.getCineplexes();
		
		for(Cineplex cineplex: cineplexes) {
			System.out.println(cineplex.getCineplexCode() + "\t\t" + cineplex.getCineplexName());
		}
	}
	
	private void showUpdateCineplex() {
		int cineplexCode;
		String location;
		
		System.out.println();
//		
//		System.out.println("Update Cineplex");
//		System.out.println("===============");
//		System.out.print("Enter Cineplex Code: ");
//		cineplexCode = scanner.nextInt();
//		
//		System.out.print("Enter Cineplex Name: ");
//		scanner.nextLine(); //To flush the \n
//		cineplex = scanner.nextLine();
//		
//		System.out.print("Enter Cineplex Location: ");
//		scanner.nextLine(); //To flush the \n
//		location = scanner.nextLine();
//		
//		cineplexController.updateCineplex(cineplexCode, location);
	}
	
	private void showDeleteCineplex() {
		int cineplexCode;
		
		System.out.println();
		System.out.println("Delete Cineplex");
		System.out.println("===============");
		System.out.print("Enter Cineplex Code : ");
		cineplexCode = ConsoleReader.readIntInput();
		
		cineplexController.removeCineplex(cineplexCode);
	}
	
	public static void main(String[] args) {
		CineplexView view = new CineplexView();
		view.show();
	}
}
