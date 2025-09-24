package Utils;

import java.io.Serializable;

public class Location implements Serializable {
	private double x;
	private double y;
	
	public Location(double newX, double newY) {
		x = newX;
		y = newY;
	}
	
	/**
	 * Taming dat stupid Java. Pass in a location from Constants. 
	 */
	public Location(Location loc) {
		x = loc.getX();
		y = loc.getY();
	}
	
	/**
	 * Find the distance from one Location object to another by passing in another Location object -Aaron
	 */
	public double distance(Location l) {
		return Math.sqrt(Math.pow(l.getX()-getX(),2)+Math.pow(l.getY()-getY(),2));
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public double getXDouble() {
		return x;
	}
	
	public double getYDouble() {
		return y;
	}
	
	/**
	 * Call this with no params to increment x by 1. Returns x after increment.
	 */
	public double incrementX() {
		incrementX(1);
		return x;
	}
	
	/**
	 * Specify how much to increase to x. Can be negative. Returns x after increment. 
	 */
	public double incrementX(double toAdd) {
		x += toAdd;
		return x;
	}
	
	/**
	 * Call this with no params to increment y by 1. Returns y after increment.
	 */
	public double incrementY() {
		incrementY(1);
		return y;
	}
	
	/**
	 * Specify how much to increase to y. Can be negative. Returns y after increment. 
	 */
	public double incrementY(double toAdd) {
		y += toAdd;
		return y;
	}
	
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
		y = newY;
	}
	
	public boolean equals(Location otherLoc) {
		return x == otherLoc.getX() && y == otherLoc.getY(); 
	}
	
	/**
	 * Compares this locaiton's X coordinate with another location's X coordinate. 
	 * @param: l - Location object to be compared
	 * @return: a negative integer, zero, or a positive integer if this location's X-coord is less than, equal to, or greater than the other location's X-coord.  
	 */
	public int compareToX(Location l) {
		return getX() - l.getX();
	}
	
	/**
	 * Compares this locaiton's Y coordinate with another location's Y coordinate. 
	 * @param: l - Location object to be compared
	 * @return: a negative integer, zero, or a positive integer if this location's Y-coord is less than, equal to, or greater than the other location's Y-coord.  
	 */
	public int compareToY(Location l) {
		return getY() - l.getY();
	}
	
	public String toString() {
		return "Location (" + x + ", " + y + ")";
	}
}
