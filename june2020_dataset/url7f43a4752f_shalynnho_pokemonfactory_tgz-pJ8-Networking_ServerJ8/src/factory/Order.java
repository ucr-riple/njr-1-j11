package factory;

import java.io.Serializable;


public class Order implements Serializable, FactoryData {    
	public orderState state;    
	public KitConfig kitConfig;    
	public int numKits;  
	public boolean cancel;
	
	public static enum orderState {PENDING, ORDERED, CANCEL, FINISHED};
	
	public Order(KitConfig kc, int numKits){    
		this.state = orderState.PENDING;    
		this.kitConfig = kc;    
		this.numKits = numKits;
		cancel = false;
	}
	
	public KitConfig getConfig() {
		return kitConfig;
	}
	
	public int getNumKits() {
		return numKits;
	}

	@Override
	public void setName(String name) {	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getID() {
		return "";
	}
}