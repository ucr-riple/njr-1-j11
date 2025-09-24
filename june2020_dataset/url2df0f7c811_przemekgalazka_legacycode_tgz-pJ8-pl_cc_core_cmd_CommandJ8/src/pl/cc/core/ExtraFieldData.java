package pl.cc.core;

import java.util.ArrayList;

public class ExtraFieldData {
	String name;
	String mask;
	
	public ExtraFieldData(String name, String mask) {
		super();
		this.name = name;
		this.mask = mask;
	}
	
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public String getMask() {
		return mask;
	}


}
