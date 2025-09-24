package org.amon;

public class Amon {
	
	private AmonBuilder builder;

	protected Amon(AmonBuilder builder){
		this.builder = builder;
	}
	
	public int log(){
		if(builder != null){
			AmonLogger logger = new AmonLogger(builder);
			return logger.getResponseCode();
		}
		return 0;
	}	
}
