package gui;

import hex.HexOff;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import bbms.DebugFuncs;
import bbms.GlobalFuncs;
import clock.Clock;
@SuppressWarnings("serial")
public class GUIKeyboard {

	/**
	 * Keyboard shortcut to scroll the map up
	 * @author Brian
	 *
	 */
	public static class ScrollUp extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.gui.GMD.mapDisplayY -= 2;
			GlobalFuncs.gui.repaint();			
		}		
	}
	
	/**
	 * Scrolls the map display down and repaints the GUI
	 * @return
	 */
	public static class ScrollDown extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.gui.GMD.mapDisplayY += 2;
			GlobalFuncs.gui.repaint();			
		}
	}
			
	/**
	 * Scrolls the map display left and repaints the GUI
	 * @return
	 */
	public static class ScrollLeft extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.gui.GMD.mapDisplayX -= 2;
			GlobalFuncs.gui.repaint();		
		}		
	}
	
	/**
	 * Scrolls the map display left and repaints the GUI
	 * @return
	 */
	public static class ScrollRight extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.gui.GMD.mapDisplayX += 2;
			GlobalFuncs.gui.repaint();			
		}
	}
	
	/**
	 * Toggles whether or not there is a throttled, accelerated gas flow model.
	 * If toggled off, flow rates are the default x1.00
	 * Otherwise the program scales the rate up to x3.00
	 * @author Brian
	 *
	 */
	public static class ToggleThrottle extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.flowRateCap != 0.95) {
				GlobalFuncs.flowRateCap = 0.95;
				GlobalFuncs.flowRate = 1.00;
				GUI_NB.GCO("Variable flow rate turned OFF - max flow will now be x1.00");
			}
			else {
				GlobalFuncs.flowRateCap = 2.95;
				GUI_NB.GCO("Variable flow rate turned ON - max flow will now be x3.00");
			}
		}
	}
	
	/**
	 * Displays or hides shaded (highlighted) hexes.
	 * @author Brian
	 *
	 */
	public static class ToggleVisibility extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.showShaded = !GlobalFuncs.showShaded;
			GlobalFuncs.gui.repaint();
			if (GlobalFuncs.showShaded) GUI_NB.GCO("Shaded hexes toggled to ON");
			else GUI_NB.GCO("Shaded hexes toggled to OFF");	
		}
	}
	
	/**
	 * Displays or hides gas vapor diffusion information (amount and flow rate)
	 * @author Brian
	 *
	 */
	public static class ToggleVapor extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.showVapor = !GlobalFuncs.showVapor;
			GlobalFuncs.gui.repaint();
			if (GlobalFuncs.showVapor) GUI_NB.GCO("Gas vapor data toggled to ON");
			else GUI_NB.GCO("Gas vapor data toggled to OFF");	
		}
	}
	
	/**
	 * Displays or hides unspotted units
	 * @author Brian
	 *
	 */
	public static class ToggleFogofWar extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.showFOW = !GlobalFuncs.showFOW;
			GlobalFuncs.gui.repaint();
			if (GlobalFuncs.showFOW) GUI_NB.GCO("Fog of war toggled to ON");
			else GUI_NB.GCO("Fog of war toggled to OFF");	
		}
	}
	
	/**
	 * Clears all shading and text on the map
	 * @author Brian
	 *
	 */
	public static class ClearHexes extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GUI_NB.GCO("Clearing shaded and texted hexes.");
			GlobalFuncs.scenMap.unshadeAll();
			GlobalFuncs.scenMap.clearTextAll();
		}
	}
	
	/** Clears all waypoints from a unit list */
	public static class ClearWaypoints extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				GUI_NB.GCO("Clearing all waypoints from selected unit.");
				GlobalFuncs.selectedUnit.waypointList.waypointList.clear();
			}
		}
	}
	
	/**
	 * Rotates either the hull or turret of the selected unit to the left by 10 degrees
	 * Depends on the GlobalFuncs boolean "RotateHull"
	 * @author Brian
	 *
	 */
	public static class RotateLeft extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				if (GlobalFuncs.RotateHull) {
					GlobalFuncs.selectedUnit.hullOrientation = GlobalFuncs.normalizeAngle(GlobalFuncs.selectedUnit.hullOrientation - 10);
				}
				else {
					GlobalFuncs.selectedUnit.turretOrientation = GlobalFuncs.normalizeAngle(GlobalFuncs.selectedUnit.turretOrientation - 10);
				}
				// DebugFuncs.rotateDebugDisplay();
			} else GUI_NB.GCO("No unit selected!");
			
			GlobalFuncs.gui.repaint();
		}			
	}
	
	/**
	 * Rotates either the hull or turret of the selected unit to the right by 10 degrees
	 * Depends on the GlobalFuncs boolean "RotateHull"
	 * @author Brian
	 *
	 */
	public static class RotateRight extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				if (GlobalFuncs.RotateHull) {
					GlobalFuncs.selectedUnit.hullOrientation = GlobalFuncs.normalizeAngle(GlobalFuncs.selectedUnit.hullOrientation + 10);
				}
				else {
					GlobalFuncs.selectedUnit.turretOrientation = GlobalFuncs.normalizeAngle(GlobalFuncs.selectedUnit.turretOrientation + 10);
				}
				// DebugFuncs.rotateDebugDisplay();
			} else GUI_NB.GCO("No unit selected!");
			
			GlobalFuncs.gui.repaint();
		}
	}
	
	/**
	 * Toggles rotation between the turret and the hull.
	 * @author Brian
	 *
	 */
	public static class ToggleRotation extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.RotateHull = !GlobalFuncs.RotateHull;
			if (GlobalFuncs.RotateHull) GUI_NB.GCO("Rotating hull");
			else GUI_NB.GCO("Rotating turret");
		}
	}
	
	/**
	 * Takes the selected unit and orients it to its target.
	 * Whether the hull or turret is oriented to the target depends on the GlobalFuncs boolean "RotateHull"
	 * @author Brian
	 *
	 */
	public static class OrientUnit extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				if (GlobalFuncs.selectedUnit.target != null) {
					if (GlobalFuncs.RotateHull) {
						GlobalFuncs.selectedUnit.OrientHullTo(GlobalFuncs.selectedUnit.target.location.x, 
															  GlobalFuncs.selectedUnit.target.location.y);
					}
					else {
						GlobalFuncs.selectedUnit.OrientTurretTo(GlobalFuncs.selectedUnit.target.location.x,  
																GlobalFuncs.selectedUnit.target.location.y);
					}
					GlobalFuncs.gui.repaint();
					GUI_NB.GCO("Orienting on target");
				}
			}
		}
	}
	
	/**
	 * Takes the selected unit and displays the LOS to the highlighted hex.
	 * @author Brian
	 *
	 */
	public static class DisplayLOStoHex extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GUI_NB.GCO("Find LOS");
			if (GlobalFuncs.selectedUnit != null && GlobalFuncs.selectedUnit.location != GlobalFuncs.selectedHex) {
				GlobalFuncs.selectedUnit.DisplayLOSTo(GlobalFuncs.selectedHex.x, GlobalFuncs.selectedHex.y, true);					
			}
		}
	}
	
	/**
	 * Takes the selected unit and displays its LOS in 360 degrees around it out to the XDim of the map
	 * @author Brian
	 *
	 */
	public static class DisplayCircularLOS extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			GUI_NB.GCO("Displaying circular LOS to range: " + GlobalFuncs.visibility);
			if (GlobalFuncs.selectedUnit != null) {
				GlobalFuncs.selectedUnit.DisplayLOSToRange(GlobalFuncs.visibility);
			}
		}
	}
	
	/**
	 * Takes the selected unit and displays the LOS to all enemies.
	 * @author Brian
	 *
	 */
	public static class DisplayLOStoEnemy extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				GUI_NB.GCO("Displaying LOS to enemies.");
				GlobalFuncs.selectedUnit.DisplayLOSToEnemies();
			}
			else {
				GUI_NB.GCO("ERROR: No unit selected!");
			}
		}
	}
	
	/**
	 * Takes the selected unit and adds a waypoint at the highlighted hex
	 * Next waypoint cannot be at the unit's current location or in the same place as its last waypoint
	 * @author Brian
	 *
	 */
	public static class AddWaypoint extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null && GlobalFuncs.selectedHex != null) {	
				int x = GlobalFuncs.selectedHex.x;
				int y = GlobalFuncs.selectedHex.y;
				HexOff lastWP = GlobalFuncs.selectedUnit.waypointList.getLastWaypoint();
				
				if (x == GlobalFuncs.selectedUnit.location.x && y == GlobalFuncs.selectedUnit.location.y) {
					GUI_NB.GCO("Can't add waypoint since highlighted hex is the unit's current location.");
				}
				else if (x != lastWP.getX() || y != lastWP.getY()) 
				{
					GlobalFuncs.selectedUnit.waypointList.addWaypoint(GlobalFuncs.selectedHex.x, GlobalFuncs.selectedHex.y);
					GUI_NB.GCO("Added waypoint at (" + x + ", " + y + ")");
					GUI_NB.GCO(GlobalFuncs.selectedUnit.waypointList.displayWaypoints());						
				}
				else {
					GUI_NB.GCO("Can't add waypoint since highlighted hex is the unit's last waypoint.");
				}
			}
		}
	}
	
	/**
	 * Takes the selected unit and removes its next waypoint 
	 * @author Brian
	 *
	 */
	public static class RemoveWaypoint extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				if (GlobalFuncs.selectedUnit.waypointList.waypointList.size() > 0) {
					GlobalFuncs.selectedUnit.waypointList.removeFirstWaypoint();
					GUI_NB.GCO("Removed the next waypoint of the unit.");
					GUI_NB.GCO(GlobalFuncs.selectedUnit.waypointList.displayWaypoints());
				}
				else {
					GUI_NB.GCO("Can't remove waypoint as this unit doesn't have any.");
				}
				
			}
		}
	}
	
	/**
	 * Takes the selected unit and displays its waypoints on the map 
	 * @author Brian
	 *
	 */
	public static class DisplayWaypoints extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedUnit != null) {
				if (GlobalFuncs.selectedUnit.waypointList.waypointList.size() > 0) {
					GlobalFuncs.selectedUnit.DisplayWaypoints();
					GUI_NB.GCO(GlobalFuncs.selectedUnit.waypointList.displayWaypoints());
				}
				else {
					GUI_NB.GCO("This unit has no waypoints.");
				}
				
			}
		}
	}
	
	/**
	 * Steps the clock forward one time unit and moves all units 
	 * @author Brian
	 *
	 */
	public static class ClockStep extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			Clock.moveAllUnits(1000);
			//Clock.updateLOSFriendly();
		}
	}
	
	/** Reduces time acceleration by one step
	 */
	public static class DecelerateTime extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			clock.ClockControl.DecelTime();
			GlobalFuncs.gui.repaint();
		}
	}
	
	/** Increases time acceleration by one step
	 */
	public static class AccelerateTime extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			clock.ClockControl.AccelTime();
			GlobalFuncs.gui.repaint();
		}
	}
	
	/** Pauses or unpauses the clock
	 */
	public static class PauseTime extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			clock.ClockControl.Pause();
			GlobalFuncs.gui.repaint();
		}
	}
	
	/** Checks to see whether the selected hex is in the enemy, friendly, or recon zone. */
	public static class CheckZones extends AbstractAction{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalFuncs.selectedHex == null) {
				GUI_NB.GCO("ERROR!  No hex selected!");
				return;
			}
			
			boolean eZ = GlobalFuncs.scenMap.inEnemyZone(GlobalFuncs.selectedHex);
			boolean fZ = GlobalFuncs.scenMap.inFriendlyZone(GlobalFuncs.selectedHex);
			
			if (eZ) GUI_NB.GCO("Hex is in the enemy zone");
			
			if (fZ) GUI_NB.GCO("Hex is in the friendly zone");
			
			if (GlobalFuncs.scenMap.inReconZone(GlobalFuncs.selectedHex)) GUI_NB.GCO("Hex is in the recon zone");
			
			GUI_NB.GCO("Unit size: " + GlobalFuncs.unitList.size() + " with " + GlobalFuncs.friendlyUnitList.size() + " friendlies and " + GlobalFuncs.enemyUnitList.size() + " enemies.");
			
			
		}
	}
	
	public static class MiniMapToggle extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GlobalFuncs.displayMiniMap = !GlobalFuncs.displayMiniMap;
			GlobalFuncs.gui.repaint();
		}
	}
		
	
	/** Displays Keyboard Shortcut Info - also accessed from the Help Menu
	 */
	public static class HelpKeyboardShortcuts extends AbstractAction {
		public HelpKeyboardShortcuts() {
			super("Keyboard Shortcuts (F1)");
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {				
			GUI_NB.GCO("W/A/S/D - Scroll the map");
			GUI_NB.GCO("V - Toggle shaded hex visibility (CAPS = show gas model)");
			GUI_NB.GCO("A (CAPS) - Toggle auto-throttling of gas model");
			GUI_NB.GCO("[] - shift rotation");
			GUI_NB.GCO("T - switches between rotating turret and hull");
			GUI_NB.GCO("F - orients the turret to target");
			GUI_NB.GCO("L - finds LOS to the selected hex (CAPS = 360 view)");
			GUI_NB.GCO("C - clears any shaded hexes");
			GUI_NB.GCO("E - Display LOS to all enemies of selected unit");
			GUI_NB.GCO("' - Displays waypoint list for the current unit");
			GUI_NB.GCO("; - Adds a waypoint for the currently selected unit");
			GUI_NB.GCO(": - Removes the next waypoint for the currently selected unit");
			GUI_NB.GCO("Q - Step the clock.");
			GUI_NB.GCO("X - Test key, evals if in enemy/friendly zone");
			GUI_NB.GCO("T - Toggle minimap");
		}
	}
	

	/**
	 * Initialize keyboard commands once the map loads
	 */	
	public static void initializeKeyCommands() {
		
		GUI_NB.GCO("Keyboard commands initialized.");
		InputMap imap = GlobalFuncs.gui.MainDisplay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = GlobalFuncs.gui.MainDisplay.getActionMap();
		
		imap.clear();
		amap.clear();
		
		// NOTE: In a finalized version, implement input mapping for capital keys too
		KeyStroke k = KeyStroke.getKeyStroke('w');
		
		imap.put(KeyStroke.getKeyStroke('w'),  "scroll up");
		amap.put("scroll up", new ScrollUp());				
		imap.put(KeyStroke.getKeyStroke('s'), "scroll down");		
		amap.put("scroll down", new ScrollDown());
				
		imap.put(KeyStroke.getKeyStroke('a'), "scroll left");
		amap.put("scroll left", new ScrollLeft());					
		imap.put(KeyStroke.getKeyStroke('d'), "scroll right");
		amap.put("scroll right", new ScrollRight());
		
				
		imap.put(KeyStroke.getKeyStroke('v'), "toggle visibility");
		amap.put("toggle visibility", new ToggleVisibility());	
		imap.put(KeyStroke.getKeyStroke('V'), "toggle vapor");
		amap.put("toggle vapor", new ToggleVapor());
		imap.put(KeyStroke.getKeyStroke('A'), "toggle throttle");
		amap.put("toggle throttle", new ToggleThrottle());
		imap.put(KeyStroke.getKeyStroke('W'), "toggle fog");
		amap.put("toggle fog", new ToggleFogofWar());
		imap.put(KeyStroke.getKeyStroke('c'), "clear shading");
		amap.put("clear shading", new ClearHexes());
		
		
		imap.put(KeyStroke.getKeyStroke('t'), "toggle rotation");
		amap.put("toggle rotation",  new ToggleRotation());
		imap.put(KeyStroke.getKeyStroke('['), "decrease rotation");	
		amap.put("decrease rotation", new RotateLeft());				
		imap.put(KeyStroke.getKeyStroke(']'), "increase rotation");
		amap.put("increase rotation", new RotateRight());
								
		imap.put(KeyStroke.getKeyStroke('f'), "orient unit");
		amap.put("orient unit", new OrientUnit());
		
		imap.put(KeyStroke.getKeyStroke('l'), "LOS to hex");
		amap.put("LOS to hex", new DisplayLOStoHex());
		imap.put(KeyStroke.getKeyStroke('L'), "Circular LOS");
		amap.put("Circular LOS", new DisplayCircularLOS());
		imap.put(KeyStroke.getKeyStroke('e'), "LOS to enemy");
		amap.put("LOS to enemy", new DisplayLOStoEnemy());
					
		imap.put(KeyStroke.getKeyStroke(';'), "add waypoint");
		amap.put("add waypoint", new AddWaypoint());
		imap.put(KeyStroke.getKeyStroke(':'), "remove waypoint");
		amap.put("remove waypoint", new RemoveWaypoint());
		imap.put(KeyStroke.getKeyStroke('\''), "display waypoints");
		amap.put("display waypoints", new DisplayWaypoints());
				
		imap.put(KeyStroke.getKeyStroke('c'), "step the clock");
		amap.put("step the clock", new ClockStep());
		
		imap.put(KeyStroke.getKeyStroke('x'), "clear waypoints");
		amap.put("clear waypoints", new ClearWaypoints());
		
		imap.put(KeyStroke.getKeyStroke("F1"), "keyboard shortcuts");
		amap.put("keyboard shortcuts", new HelpKeyboardShortcuts());
		
		// Clock controls
		imap.put(KeyStroke.getKeyStroke('-'), "decelerate time");
		amap.put("decelerate time", new DecelerateTime());
		imap.put(KeyStroke.getKeyStroke('='), "accelerate time");
		imap.put(KeyStroke.getKeyStroke('+'), "accelerate time");
		amap.put("accelerate time", new AccelerateTime());
		imap.put(KeyStroke.getKeyStroke('p'), "pause");
		amap.put("pause", new PauseTime());
		
		// Other
		imap.put(KeyStroke.getKeyStroke('q'), "minimap");
		amap.put("minimap", new MiniMapToggle());
			
	}
	
	public static void graphicsShifting() {
		InputMap imap = GlobalFuncs.gui.MainDisplay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = GlobalFuncs.gui.MainDisplay.getActionMap();
		
		// Overwrites existing key bindings for 2/4/6/8 - intended for NumPad, but you can't specify location
		// when using Action Maps.
		imap.remove(KeyStroke.getKeyStroke('2'));
		imap.remove(KeyStroke.getKeyStroke('4'));
		imap.remove(KeyStroke.getKeyStroke('6'));
		imap.remove(KeyStroke.getKeyStroke('8'));
		
		// Insert replacement key mappings here...
		
		// TODO
	}
	

}
