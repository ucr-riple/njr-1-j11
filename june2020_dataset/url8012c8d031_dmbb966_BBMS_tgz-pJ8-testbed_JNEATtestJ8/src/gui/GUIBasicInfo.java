package gui;

import hex.Hex;

import java.awt.Graphics;

import javax.swing.JPanel;

import clock.Clock;
import clock.ClockControl;
import bbms.GlobalFuncs;

@SuppressWarnings("serial")
public class GUIBasicInfo extends JPanel {
	
	static String hexCoords = "";
	static String terrainType = "";
	static String elev = "";
	static String obsc = "";
	static String dens = "";
	static String obsH = "";
	static String spots = "";
	
	static String unitCallsign = "";
	static String unitType = ""; 	
	static String unitSubLoc = "";
	
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static int uniMouseX = 0;
	public static int uniMouseY = 0;
	
	private final int start = 25;
	private final int spacing = 15;
	
	public static Hex lastHex = null;
	
	public GUIBasicInfo() {
		
	}
	
	/**
	 * Updates the basic hex info display with info for hex (x, y)
	 */
	public static void UpdateHexInfo(int x, int y) {
		
		lastHex = GlobalFuncs.scenMap.getHex(x, y);
		if (lastHex != null) {
			hexCoords = "Hex: (" + x + ", " + y + ")";						
			terrainType = lastHex.tType.displayType();						
			elev = "Elev: " + lastHex.elevation + "m";																							
			obsc = "Obsc: " + lastHex.obscuration;			
			dens = "Density: " + lastHex.density;						
			obsH = "ObsH: " + lastHex.obsHeight + "m";
			spots = "Spot: " + lastHex.numSpots;
			
			if (lastHex.HexUnit != null) {
				unitCallsign = lastHex.HexUnit.callsign;
				unitType = lastHex.HexUnit.type;
				unitSubLoc = lastHex.HexUnit.DispSubHexMovement();
			}
			else {
				unitCallsign = "";
				unitType = "";
				unitSubLoc = "";
			}
		}			
	}
	
	public static void UpdateHexUnit() {
		if (lastHex != null) {
			if (lastHex.HexUnit != null) {
				unitCallsign = lastHex.HexUnit.callsign;
				unitType = lastHex.HexUnit.type;
				unitSubLoc = lastHex.HexUnit.DispSubHexMovement();
			}
			else {
				unitCallsign = "";
				unitType = "";
				unitSubLoc = "";
			}			
		}
		
	}
		
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		
		int row = start;
		
				
		if (GlobalFuncs.mapInitialized) {
			// First line: time data
			//g.drawString("Clock " + clock.Clock.time, 10, start);
			g.drawString(Clock.DisplayTimeNorm(), 10, row);
			g.drawString(ClockControl.PrintTimeScale(), 85, row);
			g.drawString("COA " + GlobalFuncs.curCOA.name, 165, row);
			
			row += spacing;
			
			// Second line: Hex info part 1
			g.drawString(hexCoords, 10, row);
			g.drawString(terrainType, 85, row);
			g.drawString(elev, 165, row);
			
			row += spacing;
			
			// Third line: Hex info part 2
			g.drawString(obsc, 10, row);
			g.drawString(dens, 85, row);
			g.drawString(obsH, 165, row);
			
			row += (spacing * 2);
			
			// Fourth line: Unit info
			g.drawString(unitCallsign, 10, row);
			g.drawString(unitType, 85, row);
			g.drawString(unitSubLoc, 165, row);
			
			row += spacing;			
			// Fourth line: Debug info - mouse cursor location
			g.drawString("Mouse at (" + mouseX + ", " + mouseY + "), (" + uniMouseX + ", " + uniMouseY + ")", 10, row);
			g.drawString(spots, 165, row);
		}				
	}
	
}
