package serialization;

/**
 * For serialization of an enviroment element and to keep track of where to place it
 * @author tianyu shi
 * 
 */

import java.util.ArrayList;

import environment.Environment;

public class EnvironmentElement implements java.io.Serializable  {
	
	private int X;
	private int Y;
	private ArrayList<Environment> environment;
	
	public ArrayList<Environment> getEnviroment() {
		return this.environment;
	}
	
	public void setEnviroment(ArrayList<Environment> environment) {
		this.environment = environment;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public int getX() {
		return this.X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public String toString() {
		return "EnvironmentElement [environment=" + environment + "]";
	}
}