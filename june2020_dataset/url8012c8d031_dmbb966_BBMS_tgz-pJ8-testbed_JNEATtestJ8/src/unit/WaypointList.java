package unit;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import utilities.FIO;
import gui.GUI_NB;
import hex.HexMap;
import hex.HexOff;

public class WaypointList {
	
	public LinkedList<HexOff> waypointList;
	
	public WaypointList() {
		waypointList = new LinkedList<HexOff>();
	}
	
	/** Copies waypoint lists - for COA copy */
	public WaypointList(WaypointList w) {
		waypointList = new LinkedList<HexOff>();
		
		for (int i = 0; i < w.waypointList.size(); i++) {
			HexOff finger = w.waypointList.get(i);
			
			this.waypointList.add(new HexOff(finger.getX(), finger.getY()));
		}
	}
	
	
	
	public boolean addWaypoint(int x, int y) {
		if (!HexMap.checkMapBounds(x, y)) return false;
		
		HexOff newWayPoint = new HexOff(x, y);
		waypointList.addLast(newWayPoint);		
		return true;
	}
	
	public boolean addFirstWaypoint(int x, int y) {
		if (!HexMap.checkMapBounds(x, y)) return false;
		
		HexOff newWayPoint = new HexOff(x, y);
		waypointList.addFirst(newWayPoint);		
		return true;
	}
	
	public boolean removeLastWaypoint() {
		if (waypointList.size() == 0) return false;
		
		waypointList.removeLast();
		return true;
	}
	
	public boolean removeFirstWaypoint() {
		if (waypointList.size() == 0) return false;
		
		waypointList.removeFirst();
		return true;
	}
	
	public HexOff getFirstWaypoint() {
		if (waypointList.size() == 0) return new HexOff(-1, -1);
		HexOff currentWP = waypointList.getFirst();
		
		return currentWP;
	}
	
	public HexOff getLastWaypoint() {
		if (waypointList.size() == 0) return new HexOff(-1, -1);
		HexOff lastWP = waypointList.getLast();
		
		return lastWP;
	}
	
	public String displayWaypoints() {
		String output = "Total size: " + waypointList.size() + "\n";
				
		for (int i = 0; i < waypointList.size(); i++) {			
			HexOff finger = waypointList.get(i);
			output = output + "Waypoint " + i + ": (" + finger.getX() + ", " + finger.getY() + ")\n"; 
		}
		
		return output;
	}
	
	public String saveWaypoints() {
		StringBuffer buf = new StringBuffer("");
		
		for (int i = 0; i < waypointList.size(); i++) {
			HexOff finger = waypointList.get(i);
			buf.append("(" + finger.getX() + "; " + finger.getY() + ")");
			if (i != waypointList.size() - 1) buf.append(", ");
		}
		
		return buf.toString();
	}
	
	public WaypointList(String[] result, int start) {
		this();
		
		for (int i = start; i < result.length; i++) {
			String sub[] = result[i].split("; ");
			int newX = Integer.parseInt(sub[0].substring(1));
			int newY = Integer.parseInt(sub[1].substring(0, sub[1].length() - 1));
			// GUI_NB.GCO("Sub1: >" + sub[0] + "<  Sub2: >" + sub[1] + "< == (" + newX + ", " + newY + ")");
			
			addWaypoint(newX, newY);
		}
	}
	
	/*
	 * Reads waypoints in the format (x, y) and returns the appropriate HexOff
	 */
	public static HexOff readWaypoint(String wpStr) {
		String chunk1 = "";
		String chunk2 = "";
		boolean tog = false;
		for (int i = 0; i < wpStr.length(); i++) {
			char f = wpStr.charAt(i);
			
			if (f == ' ' || f == '(') {}
			else if (f == ',') tog = !tog;
			else {
				if (!tog) chunk1 += f;
				else chunk2 += f;
			}
		}
	
		gui.GUI_NB.GCO("Input: >" + wpStr + "<  || Output is: >" + chunk1 + "< and >" + chunk2 + "<");
		
		//return new HexOff(0, 0);
	    return new HexOff(Integer.parseInt(chunk1), Integer.parseInt(chunk2));
	}
}
