package gui;

import hex.Hex;
import hex.HexOff;
import hex.VaporEnum;
import bbms.GlobalFuncs;
import terrain.TerrainEnum;
import unit.SideEnum;

public class GUIMouse {

	/**
	 * On mouse click, paints the terrain type specified in parameter tE.
	 * @param e
	 * @param tE
	 */
	public static void SetPaintTerrain(java.awt.event.MouseEvent e, TerrainEnum tE) {
		HexOff cursorHexOff = GUIMainDisp.pixelToHexOff(e.getX(), e.getY(), -GlobalFuncs.gui.GMD.defaultHexSize, -GlobalFuncs.gui.GMD.defaultHexSize);
		Hex h = GlobalFuncs.scenMap.getHex(cursorHexOff.getX(), cursorHexOff.getY());		

		PaintHex(h, tE);	
	}
	
	/** As SetPaintTerrain, but does it for a blob of seven hexes instead of a single one.
	 * 
	 * @param e
	 * @param tE
	 */
	public static void SetPaintTerrainBlob(java.awt.event.MouseEvent e, TerrainEnum tE) {
		HexOff cursorHexOff = GUIMainDisp.pixelToHexOff(e.getX(), e.getY(), -GlobalFuncs.gui.GMD.defaultHexSize, -GlobalFuncs.gui.GMD.defaultHexSize);
		Hex h = GlobalFuncs.scenMap.getHex(cursorHexOff.getX(), cursorHexOff.getY());		
		
		PaintHex(h, tE);
		
		// Now paints surrounding hexes
		for (int i = 0; i < 6; i++) {
			HexOff neighbor = HexOff.NeighborOff(cursorHexOff,  i);
			h = GlobalFuncs.scenMap.getHex(neighbor.getX(), neighbor.getY());
			PaintHex(h, tE);
		}
	
	}
	
	public static void PaintHex(Hex h, TerrainEnum tE) {
		if (h != null) {
			if (h.tEnum == TerrainEnum.INVALID) return; 
			
			Hex replacement = new Hex(h.x, h.y, tE, 0);
			replacement.CloneHexData(h);
			
			GlobalFuncs.scenMap.hexArray[h.x][h.y] = replacement;
			GlobalFuncs.gui.repaint();
		}
		
	}
	
	
	
	/**
	 * On mouse click, adds a unit to the appropriate side.
	 * @param e
	 * @param unitType
	 * @param side
	 */
	public static void SetPlaceUnit(java.awt.event.MouseEvent e, String unitType, SideEnum side) {		
		HexOff cursorHexOff = GUIMainDisp.pixelToHexOff(e.getX(), e.getY(), -GlobalFuncs.gui.GMD.defaultHexSize, -GlobalFuncs.gui.GMD.defaultHexSize);
		Hex h = GlobalFuncs.scenMap.getHex(cursorHexOff.getX(), cursorHexOff.getY());
		GUI_NB.GCO("Adding " + unitType);
		if (h != null && h.HexUnit == null) {
			String callsign = "";
			double heading = 0.0;
			if (side == SideEnum.FRIENDLY) {
				callsign = "Blue ";
				heading = 90.0;
			}
			else if (side == SideEnum.ENEMY) {
				callsign = "Red ";
				heading = 270.0;
			}
			h.HexUnit = new unit.Unit(h, side, unitType, callsign + (GlobalFuncs.getUnitCount() + 1), heading, 0.0, null, false);	
			GUI_NB.GCO(h.HexUnit.DispUnitInfo());
		}			
		else {
			GUI_NB.GCO("ERROR: Invalid hex selected or hex already has a unit.");
		}
	}
	
	public static void SetVaporType(java.awt.event.MouseEvent e, VaporEnum vType) {
		HexOff cursorHexOff = GUIMainDisp.pixelToHexOff(e.getX(), e.getY(), -GlobalFuncs.gui.GMD.defaultHexSize, -GlobalFuncs.gui.GMD.defaultHexSize);
		Hex h = GlobalFuncs.scenMap.getHex(cursorHexOff.getX(), cursorHexOff.getY());
		
		switch (vType) {
		case NONE:
			h.SetVaporNormal();
			break;
		case SOURCE:
			h.SetVaporSource();
			break;
		case SINK:
			h.SetVaporSink();
			break;
		}
	}
}
