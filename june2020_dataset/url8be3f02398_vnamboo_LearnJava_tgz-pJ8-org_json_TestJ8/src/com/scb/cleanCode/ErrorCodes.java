package com.scb.cleanCode;

public enum ErrorCodes {
	OK("Test"), INVALID, NO_SUCH, LOCKED, OUR_OF_RESOURCES, WAITING_FOR_EVENT;
	private static final String EMPTY_MESSAGE="";
	private String message;
	
	private ErrorCodes(String message){
		this.message=message;
	}
	
	private ErrorCodes(){
		this.message=EMPTY_MESSAGE;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	
	public static void main(String[] args) {
		ErrorCodes errorCode=ErrorCodes.OK;
		System.out.println(errorCode.getMessage());
	}
}
