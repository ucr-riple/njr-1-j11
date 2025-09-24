package org.amon;

public enum ProtocolType {
    HTTP("http"),HTTPS("https");
	
	private String protocolType;
	
	private ProtocolType(String protocolType){
		this.protocolType = protocolType;
	}
	
	public String toString(){
		return this.protocolType;
	}
}
