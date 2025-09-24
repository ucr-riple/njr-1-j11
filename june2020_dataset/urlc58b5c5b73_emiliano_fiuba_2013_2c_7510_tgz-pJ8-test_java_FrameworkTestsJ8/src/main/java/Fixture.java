package main.java;

import java.util.HashMap;

public class Fixture {

	private HashMap<String,Register> registers;
	
	public Fixture() {
		registers = new HashMap<String,Register>();	
	}
	
	public void addRegister(String registerName, Register register) {
		registers.put(registerName, register);
	}
}
