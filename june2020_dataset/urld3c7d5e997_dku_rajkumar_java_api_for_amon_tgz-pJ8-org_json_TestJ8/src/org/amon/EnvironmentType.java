package org.amon;

public enum EnvironmentType {
	
	PRODUCTION("Production"),TEST("Test"),DEVELOPMENT("Development");
	
	private String environmentType;
	
	private EnvironmentType(String environmentType){
		this.environmentType = environmentType;
	}
	
	public String toString(){
		return this.environmentType;
	}
}
