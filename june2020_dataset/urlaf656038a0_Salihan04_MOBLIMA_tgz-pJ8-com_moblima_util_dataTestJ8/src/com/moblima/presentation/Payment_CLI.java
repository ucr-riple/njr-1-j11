package com.moblima.presentation;

import java.util.Scanner;

import com.moblima.util.ConsoleReader;

public class Payment_CLI {
	
	public Payment_CLI() {
	}
	
	public boolean show(float amount) {
		int choice;
		
		System.out.println();
		System.out.println("PAYMENT");
		System.out.println("=======");
		System.out.println("Amount : $" + amount);
		System.out.println();
		
		System.out.println("Are you sure you want to pay?");
		System.out.println("1) Yes    2) No");
		System.out.print("Please enter your choice : ");
		choice = ConsoleReader.readIntInput();
		
		switch(choice) {
		case 1:
			return true;
		case 2:
			return false;
		}
		
		return true;
	}
	
}
