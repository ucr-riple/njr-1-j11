package bbms;

import gui.GUI_NB;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import unit.SideEnum;
import unit.Unit;

public class COA {
	public Vector<unit.Unit> unitList = new Vector<Unit>();
	
	public String name = "";
	
	public COA(String n) {
		name = n;
	}
	
	public void SaveCOA() {
		// Saves the current unit list to this COA
		this.unitList = GlobalFuncs.duplicateUnitVec(GlobalFuncs.unitList);
	}
	
	public void LoadCOA() {				
		// GUI_NB.GCO("Starting friendlies: " + GlobalFuncs.friendlyUnitList.size());
		
		// First remove existing units
		Iterator<Unit> itr_unit = GlobalFuncs.unitList.iterator();
		while (itr_unit.hasNext()) {
			Unit finger = itr_unit.next();
			finger.RemovefromSide(finger.side);
			finger.location.HexUnit = null;			
		}
	
		//GlobalFuncs.unitList.clear();
		//GlobalFuncs.gui.repaint();
		
		GlobalFuncs.selectedUnit = null;
		GlobalFuncs.selectedHex = null;
		
		//GlobalFuncs.unitList = this.unitList;
		GlobalFuncs.unitList = GlobalFuncs.duplicateUnitVec(this.unitList);
		GUI_NB.GCO("DEBUG: Original unit list size: " + this.unitList.size() + " and now stored in GlobalFuncs: " + GlobalFuncs.unitList.size());
		// Add to appropriate lists
		GlobalFuncs.enemyUnitList = new Vector<Unit>();
		GlobalFuncs.friendlyUnitList = new Vector<Unit>();
		GlobalFuncs.destroyedUnitList = new Vector<Unit>();
		
		//GUI_NB.GCO("Middling friendlies: " + GlobalFuncs.friendlyUnitList.size());
		
		for (int i = 0; i < GlobalFuncs.unitList.size(); i++) {
			Unit finger = GlobalFuncs.unitList.elementAt(i);
			
			// Only model eenmy units in a COA.
			if (finger.side != SideEnum.FRIENDLY) {				
				finger.AddtoSide(finger.side);
				GlobalFuncs.scenMap.getHex(finger.location.toHO()).HexUnit = finger;
			} else {
				GUI_NB.GCO("Skipping friendly unit on COA Load");
			
			}
			
			//finger.location.HexUnit = finger;
		}
		
		//GUI_NB.GCO("Endling friendlies: " + GlobalFuncs.friendlyUnitList.size());
		
		GUI_NB.GCO("COA " + name + " successfully loaded.");
	}
		
	public COA(COA parent, String name) {
		
		
		for (int i = 0; i < parent.unitList.size(); i++) {
			System.out.println("Parent unit size: " + parent.unitList.size());
			Unit finger = parent.unitList.elementAt(i);
			Unit newUnit = new Unit(finger);
			this.unitList.addElement(newUnit);
		}
				
		this.name = name;
	}
	
	public COA(BufferedReader buf, String readR) {
		// First line (string readL) contains COA name		
		this.name = readR.substring(5, readR.length() - 1);
		
		try {
			String readL = buf.readLine();
			while (!readL.contentEquals("")) {
				// GUI_NB.GCO("String: >" + readL + "<");
				if (readL.startsWith(">Last")) {return;}
				if (!readL.startsWith("#")) {
					Unit finger = new Unit(readL);
					this.unitList.addElement(finger);	
				}
				
				readL = buf.readLine();
			}	
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		GUI_NB.GCO("Loaded COA " + name);
	}
	
	public String PrintCOA() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("COA >" + name + "<\n");
		
		for (int i = 0; i < this.unitList.size(); i++) {
			Unit finger = this.unitList.elementAt(i);
			
			buf.append(finger.SaveUnit() + "\n");
		}
		buf.append("\n");
		
		return buf.toString();
	}

}
