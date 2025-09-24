package org.amon;

public enum MessageType {

	DEBUG("debug"),WARNING("warning"),INFO("info");
	
	private String messageType;
	
	private MessageType(String messageType){
		this.messageType = messageType;
	}
	
	public String toString(){
		return this.messageType;
	}
}
