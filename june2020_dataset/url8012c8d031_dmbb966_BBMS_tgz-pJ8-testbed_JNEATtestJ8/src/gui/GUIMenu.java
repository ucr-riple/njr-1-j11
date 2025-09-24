package gui;

import hex.Hex;
import hex.MiniMapEnum;

import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;

import javax.swing.*;

import jneat.Organism;
import jneat.Population;
import bbms.COA;
import bbms.GlobalFuncs;
import clock.Clock;
import terrain.TerrainEnum;
import unit.FitnessTypeEnum;
import unit.JNEATIntegration;
import unit.OrganismTypeEnum;
import unit.Unit;
import unit.WaypointList;
import utilities.FIO;
import gui.DialogNewPop;

@SuppressWarnings("serial")
public class GUIMenu extends JMenuBar{
	
	public static class NewMap implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			DialogNewMap d = new DialogNewMap(GlobalFuncs.gui, true, false);
			d.setVisible(true);
		}
	}
	
	public static class NewBlankMap implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			DialogNewMap d = new DialogNewMap(GlobalFuncs.gui, true, true);
			d.setVisible(true);
		}
	}
	
	public static class SaveGame implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.saveState()) GUI_NB.GCO("Save successful.");
			else GUI_NB.GCO("Save failed.");		
		}
	}
	
	public static class LoadGame implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.loadState()) GUI_NB.GCO("Load successful.");
			else GUI_NB.GCO("Load failed.");	
		}
	}
	
	public static class SavePopulation implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.currentPop == null) {
				GUI_NB.GCO("ERROR: There is no population to save!");
				return;
			}
			
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "Save Population");
			d.setVisible(true);
			
			String fullPath = "src/saves/" + GlobalFuncs.tempStr;
			File f = new File(fullPath);
			if (!f.exists()) FIO.newFile(fullPath);
			Path p = f.toPath();
			
			GUI_NB.GCO("Saving JNEAT population to: " + fullPath);
			GlobalFuncs.currentPop.SavePopulationToFile(p);
		}
	}
	
	public static class LoadPopulation implements ActionListener {
		public void actionPerformed(ActionEvent event) {			
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "Load Population");
			d.setVisible(true);
			
			String fullPath = "src/saves/" + GlobalFuncs.tempStr;
			
			File f = new File(fullPath);
			if (!f.exists()) {
				GUI_NB.GCO("ERROR: " + fullPath + " does not exist!");
			} else {
				GUI_NB.GCO("Loading JNEAT population from: " + fullPath);
				Path p = f.toPath();
				
				GlobalFuncs.currentPop = new Population(p);
			}			
		}
	}
	
	public static class LoadScenario implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.runningTest = false;
			
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "Load Scenario");
			d.setVisible(true);
			
			String fullPath = "src/saves/" + GlobalFuncs.tempStr;
			
			File f = new File(fullPath);
			if (!f.exists()) {
				GUI_NB.GCO("ERROR: " + fullPath + " does not exist!");
			} else {
				GUI_NB.GCO("Loading Scenario from: " + fullPath);
				Path p = f.toPath();
				
				FIO.LoadScen(p);
				
			}
		}
	}
	
	public static class SaveScenario implements ActionListener {
		public void actionPerformed(ActionEvent event) {		
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "Save Scenario");
			d.setVisible(true);
			
			String fullPath = "src/saves/" + GlobalFuncs.tempStr;
			GUI_NB.GCO("Saving scenario to: " + fullPath);
			GlobalFuncs.saveScen(fullPath);
		}
	}
	
	public static class ExitGame implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("File - Exit");
			GUI_NB.GCO("File - Exit");
			System.exit(0);	
		}
	}	
	
	/** Sets the mode so that clicking on the map will now select a unit or highlight the hex
	 */
	public static class ModeSelectUnit implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now select units.");
			GlobalFuncs.placeUnit = 0;
		}
	}
	
	/** Sets the mode so that clicking on an empty hex will add a friendly M1A2 tank
	 */
	public static class ModeAddM1A2 implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now place a M1A2");
			GlobalFuncs.placeUnit = 1;
		}
	}
	
	/** Sets the mode so that clicking on an empty hex will add an enemy T72 tank
	 */
	public static class ModeAddT72 implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now place a T-72");
			GlobalFuncs.placeUnit = 2;
		}
	}
	
	/** Sets the mode so that clicking will change the given terrain to CLEAR
	 */
	public static class ModePaintClear implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now set terrain to CLEAR");
			GlobalFuncs.placeUnit = 10;
		}
	}
	
	/** Sets the mode so that clicking will change the given terrain to TREES
	 */
	public static class ModePaintTrees implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now set terrain to TREES");
			GlobalFuncs.placeUnit = 11;
		}
	}
	
	/** Sets the mode so that clicking will change the given terrain to TALL GRASS
	 */
	public static class ModePaintTallGrass implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now set terrain to TALL GRASS");
			GlobalFuncs.placeUnit = 12;
		}
	}
	
	/** Sets the mode so that clicking will change the given hex to a vapor source
	 */
	public static class ModeSetVaporSource implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now set hex to VAPOR SOURCE");
			GlobalFuncs.placeUnit = 21;
			new DisplayModeVapor().actionPerformed(event);
		}
	}
	
	/** Sets the mode so that clicking will change the given hex to a vapor sink
	 */
	public static class ModeSetVaporSink implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now set hex to VAPOR SINK");
			GlobalFuncs.placeUnit = 22;
			new DisplayModeVapor().actionPerformed(event);
		}
	}
	
	/** Sets the mode so that clicking will remove a vapor source or sink
	 */
	public static class ModeSetVaporNorm implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Clicking will now REMOVE vapor source or sink");
			GlobalFuncs.placeUnit = 20;
			new DisplayModeVapor().actionPerformed(event);
		}
	}
	
	
	
	/** Outputs all spotting this turn to the GUI console
	 */
	public static class DisplaySpotsThisTurn implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			spotting.SpotRecords x = GlobalFuncs.allSpots.getReportsTime(Clock.time);
			GUI_NB.GCO(x.DisplayRecords());
		}
	}
	
	/** Outputs all spotting data to the GUI console
	 */
	public static class DisplayAllSpots implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			spotting.SpotRecords x = GlobalFuncs.allSpots.getReportsTime(0, Clock.time);
			GUI_NB.GCO(x.DisplayRecords());
		}
	}
	
	/** Outputs all spotting data for the selected unit to the GUI console
	 */
	public static class DisplayAllSpotsForUnit implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit != null) {
				spotting.SpotRecords x = GlobalFuncs.allSpots.getReportsTarget(GlobalFuncs.selectedUnit);
				GUI_NB.GCO(x.DisplayRecords());
			}
			else {
				GUI_NB.GCO("ERROR: No unit selected!");
			}
			
		}
	}
	
	/** Sets the display mode to UNIT in the main display and info panes
	 */
	public static class DisplayModeUnit implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Display Mode set to UNIT");
			GlobalFuncs.showVapor = false;
			GlobalFuncs.showLOS = true;
			GlobalFuncs.showFOW = true;
			GUIInfoPane.changePaneMode(DetailedInfoEnum.UNIT);
		}
	}
	
	/** Sets the display mode to VAPOR in the main display and info panes
	 */
	public static class DisplayModeVapor implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Display Mode set to VAPOR");
			GlobalFuncs.showVapor = true;
			GlobalFuncs.showLOS = false;
			GlobalFuncs.showFOW = false;
			GUIInfoPane.changePaneMode(DetailedInfoEnum.VAPOR);
		}
	}
	
	public static class DisplayModeJNEAT implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Display Mode set to JNEAT");
			GlobalFuncs.showVapor = true;
			GlobalFuncs.showLOS = false;
			GlobalFuncs.showFOW = true;
			GUIInfoPane.changePaneMode(DetailedInfoEnum.JNEAT);
		}
	}
	
	/** Sets the display mode to DEBUG in the main display and info panes
	 */
	public static class DisplayModeDebug implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Display Mode set to DEBUG");
			GlobalFuncs.showVapor = false;
			GlobalFuncs.showLOS = false;
			GlobalFuncs.showFOW = false;
			GUIInfoPane.changePaneMode(DetailedInfoEnum.DEBUG);
		}
	}
	
	/** Predicts vapor equilibrium based on linear interpolation between source and sink
	 */
	public static class PredictVapor implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Predicting vapor equilibrium.");
			GlobalFuncs.scenMap.predictVaporMap();
		}
	}
	
	/** Resets vapor levels in all hexes to starting amounts (full density)
	 */
	public static class ResetVapor implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.scenMap.StandardVaporMap();
		}
	}
	
	public static class ToggleFixedSlowVapor implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.fixSlowRate = !GlobalFuncs.fixSlowRate;
			GUI_NB.GCO("Toggled fixed slow vapor rate to: " + GlobalFuncs.fixSlowRate);
		}
	}
	
	
	/** Automatically runs the clock forward until equilibrium has been reached */
	public static class RuntoEquilibrium implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			DialogFileName selectDelta = new DialogFileName(GlobalFuncs.gui, true, "Set DV Tolerance");
			selectDelta.setVisible(true);
			GlobalFuncs.dvTolerance = Integer.parseInt(GlobalFuncs.tempStr);
			if (GlobalFuncs.dvTolerance < 0) {
				GUI_NB.GCO("ERROR!  Cannot set DV Tolerance to negative.");
				return;
			}
			else {				
				if (clock.ClockControl.paused) clock.ClockControl.Pause();
				clock.ClockControl.SetTimeScale((byte) 10);
				GlobalFuncs.runtoEq = true;
			}
			
		}
	}

	
	/** Displays version info
	 */
	public static class HelpAbout implements ActionListener {
		public void actionPerformed(ActionEvent event) {				
			System.out.println("Bare Bones Military Simulator Experimental Version");
			GUI_NB.GCO("Bare Bones Military Simulator Experimental Version");
		}
	}
	
	/** Copies the contents of the GUI Console to the clipboard
	 */
	public static class CopyConsole implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GUIConsole.selectAll();
			GUI_NB.GUIConsole.copy();
			GUI_NB.GUIConsole.select(0, 0);
			GUI_NB.GCO("Copyied to clipboard.");
		}
	}
	
	/** Brings up the population dialog */
	public static class PopDialog implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GUI_NB.GCO("Displaying dialog.");
			DialogNewPop dialog = new DialogNewPop(GlobalFuncs.gui, true);
			// dialog.setLocationRelativeTo(GlobalFuncs.gui);
			dialog.setVisible(true);
		}
	}
	
	/** Allows the user to select an organism to attach to the selected unit via dialog box.*/
	public static class AddOrg implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  No unit selected!");
				return;
			}
			else if (GlobalFuncs.currentPop == null) {
				GUI_NB.GCO("ERROR!  Must generate a JNEAT population first!");
				return;
			}
			else {
				GUI_NB.GCO("There are " + GlobalFuncs.currentPop.organisms.size() + " organisms to choose from.");
				DialogFileName selectOrg = new DialogFileName(GlobalFuncs.gui, true, "Select Org 0-" + (GlobalFuncs.currentPop.organisms.size() - 1));
				selectOrg.setVisible(true);
				
				int selectedOrg = Integer.parseInt(GlobalFuncs.tempStr);
				if (selectedOrg < 0 || selectedOrg > GlobalFuncs.currentPop.organisms.size() - 1) GUI_NB.GCO("ERROR: Invalid organism number.");
				else {
					GUI_NB.GCO("You selected organism #" + selectedOrg);
					GUI_NB.GCO(GlobalFuncs.currentPop.organisms.elementAt(selectedOrg).PrintOrganism());
					GlobalFuncs.selectedUnit.org = GlobalFuncs.currentPop.organisms.elementAt(selectedOrg);
				}
			}
		}
	}
	
	/** Removes an organism from the selected unit */
	public static class RemoveOrg implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  No unit selected!");
				return;			
			}
			else {
				GlobalFuncs.selectedUnit.org = null;
				GUI_NB.GCO("Neural network removed from selected unit.");
			}
		}
	}
	
	/** Removes orgs from all units */
	public static class RemoveAllOrg implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
				Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
				
				finger.org = null;
			}
			
			GUI_NB.GCO("All organisms removed from units.");
		}
	}
	
	/** Removes selected unit */
	public static class RemoveUnit implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  No unit selected!");
				return;
			}
			
			GlobalFuncs.selectedUnit.RemoveUnit();
		}
	}
	
	/** Randomly assigns organisms from the current population to all friendly units.
	 * This overwrites any existing organism that might already be attached to them.*/
	public static class RandAddOrg implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.currentPop == null) {
				GUI_NB.GCO("ERROR!  Must generate a population first.");
				return;
			}
			
			if (GlobalFuncs.currentPop.organisms.size() < GlobalFuncs.friendlyUnitList.size()) {
				GUI_NB.GCO("ERROR!  There are not enough organisms to assign to units!");
				return;
			}
			
			int abortcount = 0;
			// Since all friendly units will have new organisms assigned to them, will clear the checkout vars
			for (int i = 0; i < GlobalFuncs.currentPop.organisms.size(); i++) {
				GlobalFuncs.currentPop.organisms.elementAt(i).checkout = false;
			}
			
			for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
				Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
				Organism org = GlobalFuncs.currentPop.organisms.elementAt(GlobalFuncs.randRange(0, GlobalFuncs.currentPop.organisms.size() - 1));
				
				if (!org.checkout) {
					GUI_NB.GCO("Assigning organism #" + org.genome.genome_id + " to friendly unit " + finger.callsign);
					finger.org = org;
					org.checkout = true;
				} 
				else {
					if (abortcount > 100) {
						GUI_NB.GCO("ERROR!  There should be enough organisms to assign, but unable to do so.");
						return;
					}
					GUI_NB.GCO("Organism #" + org.genome.genome_id + " is already checked out.  Fishing again.");
					i--;
					abortcount++;
				}
					
			}
		}
	}
	
	/** Sequentially assigns organisms from the current population to all friendly units.
	 * This overwrites any existing organism that might already be attached to them.*/
	public static class SeqAddOrg implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			unit.JNEATIntegration.FillAllScouts();			
		}
	}
	
	/** Displays the current organism to the GUI console */
	public static class ViewOrg implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  Must select a unit first.");
				return;
			}
			if (GlobalFuncs.selectedUnit.org == null) {
				GUI_NB.GCO("ERROR!  The selected unit doesn't have a network attached.");
				return;
			}
			
			GUI_NB.GCO("Organism assigned to " + GlobalFuncs.selectedUnit.callsign);
			GUI_NB.GCO(GlobalFuncs.selectedUnit.org.PrintOrganism());
		}
	}
	
	public static class VisDialog implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "Set Visibility");
			d.setVisible(true);
			
			int newVis = Integer.parseInt(GlobalFuncs.tempStr);
			if (newVis <= 0) GUI_NB.GCO("ERROR: Invalid visibility number.");
			else {
				GlobalFuncs.visibility = newVis;
				GUI_NB.GCO("Visibility set to " + GlobalFuncs.visibility);
			}
		}
	}
	
	public static class MiniMap implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.displayMiniMap = !GlobalFuncs.displayMiniMap;
		}
	}
	
	public static class MiniVaporAmt implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.MiniMapType = MiniMapEnum.VAPOR_AMT;
		}
	}
	
	public static class MiniVaporDV implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.scenMap.UpdateExactDVNorm();
			GlobalFuncs.MiniMapType = MiniMapEnum.VAPOR_DV;
		}
	}
	
	public static class MiniTerrain implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.MiniMapType = MiniMapEnum.TERRAIN;			
		}
	}
	
	/** Sets the friendly zone to the currently selected hex column and before (less than) it.*/
	public static class SetFriendlyZone implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedHex != null) {
				GlobalFuncs.scenMap.friendlyZone = GlobalFuncs.selectedHex.x;
				GUI_NB.GCO("Friendly zone set to column: " + GlobalFuncs.scenMap.friendlyZone);
			} else {
				GUI_NB.GCO("ERROR: No hex selected!");
			}
		}
	}
	
	/** Sets the enemy zone to the currently selected hex column and after (greater than) it.*/
	public static class SetEnemyZone implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedHex != null) {
				GlobalFuncs.scenMap.enemyZone = GlobalFuncs.selectedHex.x;
				GUI_NB.GCO("Enemy zone set to column: " + GlobalFuncs.scenMap.enemyZone);
			}
			else {
				GUI_NB.GCO("ERROR: No hex selected!");
			}
		}
	}
	
	public static class CycleFitnessType implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR: No unit selected!");
			} else {
				Unit finger = GlobalFuncs.selectedUnit;
				finger.fitType = FitnessTypeEnum.CycleFitType(finger.fitType);
				GUI_NB.GCO("Fitness type of unit " + finger.callsign + " is now " + finger.fitType);
			}
		}
	}
	
	public static class NextCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.curCOA.SaveCOA();
			GlobalFuncs.COAIndex++;
			
			if (GlobalFuncs.COAIndex > GlobalFuncs.allCOAs.size()) {
				GlobalFuncs.COAIndex = 1;
			}
			
			GlobalFuncs.curCOA = GlobalFuncs.allCOAs.elementAt(GlobalFuncs.COAIndex - 1);
			
			GlobalFuncs.curCOA.LoadCOA();
		}
	}
	
	public static class PrevCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.curCOA.SaveCOA();
			GlobalFuncs.COAIndex--;
			
			if (GlobalFuncs.COAIndex <= 0) {
				GlobalFuncs.COAIndex = GlobalFuncs.allCOAs.size();
			}
			
			GlobalFuncs.curCOA = GlobalFuncs.allCOAs.elementAt(GlobalFuncs.COAIndex - 1);
			
			GlobalFuncs.curCOA.LoadCOA();
		}
	}
	
	public static class RenameCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "New COA Name");
			d.setVisible(true);
			
			if (GlobalFuncs.tempStr.contains(",")) {
				GUI_NB.GCO("ERROR: COA name cannot contain commas.");
				return;
			}
			
			GlobalFuncs.curCOA.name = GlobalFuncs.tempStr;
		}
	}
	
	public static class NewCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "New COA Name");
			d.setVisible(true);
			
			if (GlobalFuncs.tempStr.contains(",")) {
				GUI_NB.GCO("ERROR: COA name cannot contain commas.");
				return;
			}
			
			
			
			GlobalFuncs.curCOA.SaveCOA();
			
			COA nCOA = new COA(GlobalFuncs.tempStr);
			GlobalFuncs.allCOAs.addElement(nCOA);
			GlobalFuncs.COAIndex = GlobalFuncs.allCOAs.size();
			GlobalFuncs.curCOA = nCOA;
			GlobalFuncs.curCOA.LoadCOA();
			GlobalFuncs.gui.repaint();
		}
	}
	
	public static class CopyCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			DialogFileName d = new DialogFileName(GlobalFuncs.gui, true, "New COA Name");
			d.setVisible(true);
			
			if (GlobalFuncs.tempStr.contains(",")) {
				GUI_NB.GCO("ERROR: COA name cannot contain commas.");
				return;
			}
			GlobalFuncs.curCOA.SaveCOA();
			
			COA nCOA = new COA(GlobalFuncs.curCOA, GlobalFuncs.tempStr);
			GlobalFuncs.allCOAs.addElement(nCOA);
			GlobalFuncs.COAIndex = GlobalFuncs.allCOAs.size();
			GlobalFuncs.curCOA = nCOA;
			GlobalFuncs.curCOA.LoadCOA();
			GlobalFuncs.gui.repaint();
		}
	}
	
	public static class DelCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.allCOAs.size() == 1) {
				GUI_NB.GCO("ERROR: You cannot delete your final COA!");
				return;
			}
			
			int curIndex = GlobalFuncs.COAIndex;
			new NextCOA();
			
			GlobalFuncs.allCOAs.removeElementAt(curIndex - 1);
		}
	}
	
	public static class SaveCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.curCOA.SaveCOA();
		}
	}
	
	public static class ReinitCOA implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			JNEATIntegration.ScenIterationSetup();
		}
	}
	
	public static class DisplaySideWP implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  Must select a unit first.");
				return;
			} else {
				GlobalFuncs.showWPs = !GlobalFuncs.showWPs;
			}
		}
	}
	
	public static class ToggleVaporUpdate implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.updateVapor = !GlobalFuncs.updateVapor;
			GUI_NB.GCO("Vapor update set to: " + GlobalFuncs.updateVapor);
		}
	}
	
	public static class TeleportUnit implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  Must select a unit first.");
				return;
			}
			
			if (GlobalFuncs.selectedHex == null) {
				GUI_NB.GCO("ERROR!  Must select a hex first.");
				return;
			}
			
			GlobalFuncs.selectedUnit.TeleportTo(GlobalFuncs.selectedHex);
		}
	}
	
	public static class UnitSensorCheck implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  Must select a unit first.");
				return;
			}
			
			GlobalFuncs.selectedUnit.UseSensors(GlobalFuncs.selectedUnit.location);
						
			GlobalFuncs.selectedUnit.DisplayLOSToRange(GlobalFuncs.visibility);
		}
	}
	
	public static class HexSensorCheck implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedHex == null) {
				GUI_NB.GCO("ERROR!  Must select a hex first.");
				return;				
			}
			
			// Unit.GetLOSToRange(GlobalFuncs.selectedHex, GlobalFuncs.visibility);
			//double sense = OrganismTypeEnum.SenseFlowFOV(GlobalFuncs.selectedHex);
			//GUI_NB.GCO("Unnormalized value for hex: " + sense + " and normalized: " + sense / GlobalFuncs.maxSpottedDV);
			unit.OrganismTypeEnum.SenseFlow60(GlobalFuncs.selectedHex, FitnessTypeEnum.SIMPLE_GREEDY);
			Unit.DisplayLOSToRange(GlobalFuncs.selectedHex, GlobalFuncs.visibility);
		}
	}
	
	public static class NormFlowRate implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			GlobalFuncs.scenMap.UpdateExactDVNorm();
		}
	}
	
	public static class PlaceUnitNN implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit == null) {
				GUI_NB.GCO("ERROR!  No unit selected.");
				return;
			}
			
			unit.JNEATIntegration.DeployOne(GlobalFuncs.selectedUnit);
			GlobalFuncs.gui.GMD.centerView(GlobalFuncs.selectedUnit.location);

		}
	}
	
	public static class PlaceAllNN implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			unit.JNEATIntegration.DeployAll();
		}
	}
	
	public static class TestPopulation implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// This dialog will either generate or load a NN population, create enough friendlies to fill fully
			GlobalFuncs.runningTest = true;
			DialogTestPop x = new DialogTestPop(GlobalFuncs.gui, false);
			x.setVisible(true);
		}
	}
	
	/** A test function that sets the fitness of all organisms attached to units to 2.0.
	 * Yes I know that fitness number is "too high."  Deal with it. */
	public static class TestFunc implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (GlobalFuncs.selectedUnit.org != null) {
				Organism finger = GlobalFuncs.selectedUnit.org;
				GUI_NB.GCO("Old org fitness: " + finger.fitness + " over " + finger.fitAveragedOver);
				double newFit = GlobalFuncs.randFloat();
				GUI_NB.GCO("Adding: " + newFit);
				
				finger.AverageFitness(newFit);
				
				GUI_NB.GCO("New org fitness: " + finger.fitness + " over " + finger.fitAveragedOver);
			}
		}
	}
	


	
	public GUIMenu() {
		GenerateMenu();		
	}
	
	public void GenerateMenu() {
		FileMenu();
		
		if (GlobalFuncs.mapInitialized) {
			DisplayMenu();
			SetupMenu();
			ActionsMenu();
			NeuralNetMenu();
			MapMenu();
			ScenarioMenu();
		}
		
		HelpMenu();
	}
	
	public void ScenarioMenu() {
		JMenu menu = new JMenu("Scenario");
		menu.setMnemonic(KeyEvent.VK_S);
		this.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Next COA");
		menuItem.addActionListener(new NextCOA());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Prev COA");
		menuItem.addActionListener(new PrevCOA());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Reinitialize this COA");
		menuItem.addActionListener(new ReinitCOA());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("New COA");
		menuItem.addActionListener(new NewCOA());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Copy COA");
		menuItem.addActionListener(new CopyCOA());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Delete COA");
		menuItem.addActionListener(new DelCOA());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Rename COA");
		menuItem.addActionListener(new RenameCOA());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save COA");
		menuItem.addActionListener(new SaveCOA());
		menu.add(menuItem);
	}
	
	
	
	public void DisplayMenu() {
		JMenu menu = new JMenu("Display");
		menu.setMnemonic(KeyEvent.VK_D);
		this.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Unit Display Mode");
		menuItem.addActionListener(new DisplayModeUnit());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Vapor Display Mode");
		menuItem.addActionListener(new DisplayModeVapor());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("JNEAT Display Mode");
		menuItem.addActionListener(new DisplayModeJNEAT());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Debug Display Mode");
		menuItem.addActionListener(new DisplayModeDebug());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Show Waypoints of Selected Side");
		menuItem.addActionListener(new DisplaySideWP());
		menu.add(menuItem);
		
	}

	
	public void FileMenu() {		
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		this.add(menu);
		
		JMenuItem menuItem = new JMenuItem("New Rand Map", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new NewMap());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("New Blank Map", KeyEvent.VK_B);
		menuItem.addActionListener(new NewBlankMap());
		menu.add(menuItem);
		
		if (bbms.GlobalFuncs.mapInitialized) {
			menuItem = new JMenuItem("Save", KeyEvent.VK_S);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			menuItem.addActionListener(new SaveGame());
			menu.add(menuItem);	
		}

		menuItem = new JMenuItem("Load", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new LoadGame());
		menu.add(menuItem);	
		
		if (bbms.GlobalFuncs.mapInitialized) {
			menu.addSeparator();
			
			menuItem = new JMenuItem("Save JNEAT Population");
			menuItem.addActionListener(new SavePopulation()); 
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Load JNEAT Population");
			menuItem.addActionListener(new LoadPopulation());
			menu.add(menuItem);
			
			menu.addSeparator();
			
			menuItem = new JMenuItem("Save Scenario");
			menuItem.addActionListener(new SaveScenario());
			menu.add(menuItem);
						
		}
		
		menuItem = new JMenuItem("Load Scenario");
		menuItem.addActionListener(new LoadScenario());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Test Population");
		menuItem.addActionListener(new TestPopulation());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ExitGame());
		menu.add(menuItem);
	}
	
	
	public void SetupMenu() {
		JMenu menu = new JMenu("Setup");
		menu.setMnemonic(KeyEvent.VK_S);
		this.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Mode: Select Unit");		
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ModeSelectUnit());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Place M1A2");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ModeAddM1A2());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Place T-72");		
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ModeAddT72());				
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Set Clear Terrain");		
		menuItem.addActionListener(new ModePaintClear());				
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Set Tree Terrain");		
		menuItem.addActionListener(new ModePaintTrees());				
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Set Tall Grass Terrain");		
		menuItem.addActionListener(new ModePaintTallGrass());				
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Set Vapor Source");
		menuItem.addActionListener(new ModeSetVaporSource());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Set Vapor Sink");
		menuItem.addActionListener(new ModeSetVaporSink());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Mode: Remove Vapor Source/Sink");
		menuItem.addActionListener(new ModeSetVaporNorm());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Teleport Selected Unit");
		menuItem.addActionListener(new TeleportUnit());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Remove Selected Unit");
		menuItem.addActionListener(new RemoveUnit());
		menu.add(menuItem);
	}
	
	
	public void ActionsMenu() {
		JMenu menu = new JMenu("Actions");
		menu.setMnemonic(KeyEvent.VK_A);			
		this.add(menu);						
		
		JMenuItem menuItem = new JMenuItem("Display spots for this turn");		
		menuItem.addActionListener(new DisplaySpotsThisTurn());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Display spots for all time");		
		menuItem.addActionListener(new DisplayAllSpots());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Display spots for selected unit");		
		menuItem.addActionListener(new DisplayAllSpotsForUnit());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Predict vapor equilibrium");
		menuItem.addActionListener(new PredictVapor());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Reset vapor density to default");
		menuItem.addActionListener(new ResetVapor());
		menu.add(menuItem);
				
		menuItem = new JMenuItem("Toggle Fixed Slow Vapor Rate");
		menuItem.addActionListener(new ToggleFixedSlowVapor());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Run to equilibrium");
		menuItem.addActionListener(new RuntoEquilibrium());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Toggle constant vapor update");
		menuItem.addActionListener(new ToggleVaporUpdate());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Copy console output to clipboard");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new CopyConsole());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Set Visibility");
		menuItem.addActionListener(new VisDialog());
		menu.add(menuItem);
		

		
		menuItem = new JMenuItem("Test Function");
		menuItem.addActionListener(new TestFunc());
		menu.add(menuItem);
		
	}
	
	public void NeuralNetMenu() {
		JMenu menu = new JMenu("JNEAT");
		menu.setMnemonic(KeyEvent.VK_J);
		this.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Generate Population");
		menuItem.addActionListener(new PopDialog());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Assign Organism to Selected Unit");
		menuItem.addActionListener(new AddOrg());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Remove Organism from Selected Unit");
		menuItem.addActionListener(new RemoveOrg());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("View Organism of Selected Unit");
		menuItem.addActionListener(new ViewOrg());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Randomly Assign Organisms to Friendlies");
		menuItem.addActionListener(new RandAddOrg());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Sequentially Assign Organisms to Friendlies");
		menuItem.addActionListener(new SeqAddOrg());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Remove all Organisms from Friendlies");
		menuItem.addActionListener(new RemoveAllOrg());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Set friendly zone to sel. hex");
		menuItem.addActionListener(new SetFriendlyZone());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Set enemy zone to sel. hex");
		menuItem.addActionListener(new SetEnemyZone());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Cycle unit fitness function");
		menuItem.addActionListener(new CycleFitnessType());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Sensor check for unit");
		menuItem.addActionListener(new UnitSensorCheck());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Sensor check for sel. hex");
		menuItem.addActionListener(new HexSensorCheck());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Normalize Flow Rates");
		menuItem.addActionListener(new NormFlowRate());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Place selected unit via NN");
		menuItem.addActionListener(new PlaceUnitNN());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("PLace all units via NN");
		menuItem.addActionListener(new PlaceAllNN());
		menu.add(menuItem);
	}
	
	public void MapMenu() {
		JMenu menu = new JMenu("Map");
		menu.setMnemonic(KeyEvent.VK_M);
		this.add(menu);
		
		JMenuItem menuItem;
		
		menuItem = new JMenuItem("Toggle Minimap");
		menuItem.addActionListener(new MiniMap());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Show Vapor Amount");
		menuItem.addActionListener(new MiniVaporAmt());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Show Vapor DV");
		menuItem.addActionListener(new MiniVaporDV());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Show Terrain");
		menuItem.addActionListener(new MiniTerrain());
		menu.add(menuItem);
	}
	
	public void HelpMenu() {		
		JMenu menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		this.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Keyboard Shortcuts", KeyEvent.VK_K);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		menuItem.setAction(new GUIKeyboard.HelpKeyboardShortcuts());
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("About", KeyEvent.VK_A);		
		menuItem.addActionListener(new HelpAbout());
		menu.add(menuItem);					
	}
}
