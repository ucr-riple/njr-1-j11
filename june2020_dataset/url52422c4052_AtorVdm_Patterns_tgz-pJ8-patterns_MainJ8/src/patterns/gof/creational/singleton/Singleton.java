package patterns.gof.creational.singleton;

import patterns.gof.helpers.Pattern;

public class Singleton implements Pattern {
	private static Singleton instance = null;
	private Singleton() {};
	public static synchronized Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	//----- Singleton content -----
	private String data;
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}