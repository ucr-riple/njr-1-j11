package hex;

import gui.GUI_NB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bbms.GlobalFuncs;
import terrain.*;
import unit.MoveClass;
import unit.SideEnum;


public class Hex {
	
	public int x;
	public int y;
	public TerrainType tType;	// Type of terrain
	public TerrainEnum tEnum;
	public int elevation;		// Relative height of the ground (m)
	public int obsHeight;		// Additional height from buildings/obstacles
	public int density;			// Obstructs line of sight if cumulative density >30
	public int vapor;			// 0-255, used in the gas diffusion model
	public int vaporOut;		// How much the vapor level in this hex will change at the next iteration.
	public int vaporIn;
	public int deltaVapor;
	public int numSpots = 1;	// Number of friendly units that have eyes on this hex + 1.  Used for shared spotting fitness functions.
	
	public VaporEnum vaporType = VaporEnum.NONE;
	
	public boolean shaded;		// Will the hex be drawn shaded or not?
	public Color shadedColor;
	
	public boolean displayText;
	public String hexText;
	public Color textColor;
	
	public boolean highlighted;	// Is this hex highlighted (apart from shading)
	
	public int obscuration;	// Level of visual obscuration on the hex (adds to density, but can dissipate over time)	
	
	public unit.Unit HexUnit = null;
	
	// TODO: Include unit list of all units in this hex
	// TODO: Include a cover value which affects the deadliness of various types of weapons
	
	// Constructor with random height, obsHeight, and density
	public Hex (int xi, int yi, TerrainEnum iTerrain, int iElev) {
		this(xi, yi, iTerrain, 
				iElev + iTerrain.tType.generateHeight(), 
				iTerrain.tType.generateObsHeight(),
				iTerrain.tType.generateDensity(),
				0, 25500, 0, VaporEnum.NONE);		
	}
	
	// Constructor with specific height, obsHeight, density, and obscuration
	public Hex (int xi, int yi, TerrainEnum iTerrain, int iElev, int iObsHeight, int iDensity, int iObsc, int iVapor, int dV, VaporEnum vType) {
		x = xi;
		y = yi;		
				
		shaded = false;
		shadedColor = Color.WHITE;
		highlighted = false;	
		
		displayText = false;
		hexText = "";
		textColor = Color.WHITE;
		tEnum = iTerrain;				
		tType = tEnum.tType;		// There's probably a more efficient way of doing this.
				
		elevation = iElev;		
		obsHeight = iObsHeight;
		density = iDensity;				
		
		obscuration = iObsc;		
		vapor = iVapor;
		//vapor = GlobalFuncs.randRange(0,  100);
		deltaVapor = dV;
		vaporIn = 0;
		vaporOut = 0;
		
		switch (vType) {
		case NONE:
			vaporType = vType;
			break;
		case SOURCE:
			SetVaporSource();
			break;
		case SINK:
			SetVaporSink();
			break;
		}		
	}
	
	/**
	 * Clones hex data EXCEPT terrain data (e.g. elevation, obs. height, density)
	 * @param h
	 */
	public void CloneHexData(Hex h) {
		shaded = h.shaded;
		shadedColor = h.shadedColor;
		highlighted = h.highlighted;
		
		displayText = h.displayText;
		hexText = h.hexText;
		textColor = h.textColor;
		
		obscuration = h.obscuration;
		vapor = h.vapor;
		deltaVapor = h.deltaVapor;
	}
	
	public void DisplayInfo() {
		System.out.print ("Hex: (" + x + ", " + y + ") is terrain type: " + tType.displayType() + " (" + tType.displayChar() + ")\n");
		System.out.print ("Elevation: " + elevation + "   Obs Height: " + obsHeight + "    Density: " + density + "    Obscur: " + obscuration + "\n");		
	}
	
	public void GCODisplay() {
		gui.GUI_NB.GCO("Hex: (" + x + ", " + y + ") is terrain type: " + tType.displayType() + " (" + tType.displayChar() + ")");
		gui.GUI_NB.GCO("Elevation: " + elevation + "   Obs Height: " + obsHeight + "    Density: " + density + "    Obscur: " + obscuration);
	}
	
	/**
	 * Sets the vapor component of this hex, between 0 and 255 inclusive.
	 * @param v
	 */
	public void SetVapor(int vi) {
		if (vi >= 0 && vi <= 25500) vapor = vi;							
	}
	
	public void UpdateVapor() {
		vapor += vaporIn;
		vapor -= vaporOut;
		deltaVapor = vaporIn + vaporOut;
		if (deltaVapor > GlobalFuncs.maxDelta && vaporType == VaporEnum.NONE){
			// if (!clock.ClockControl.paused) clock.ClockControl.Pause();
			GUI_NB.GCODTG("WARNING!!  Hex (" + x + ", " + y + ") has greater DV than the sink!  Reducing flow rate this tick.");
			GlobalFuncs.ticksStable = 0;
			GlobalFuncs.reduceRate = true;
			GlobalFuncs.flowRateCap = Math.max(2.95, GlobalFuncs.flowRate - GlobalFuncs.flowStep);
			//GlobalFuncs.flowRate -= GlobalFuncs.flowStep;
		}
		if (vapor < 0 && vaporType != VaporEnum.SINK) {
			//if (!clock.ClockControl.paused) clock.ClockControl.Pause();
			GUI_NB.GCODTG("ERROR!!  Hex (" + x + ", " + y + ") has negative vapor!  Flow rate set to 1.");
			GlobalFuncs.flowRate = 1.00;
		}
		
		GlobalFuncs.totalVapor += vapor;
		
		vaporIn = 0;
		vaporOut = 0;
	}
	
	/**
	 * Sets this hex to be a vapor sink
	 */
	public void SetVaporSink() {
		if (vaporType == VaporEnum.SINK) return;
		else if (vaporType == VaporEnum.SOURCE) GlobalFuncs.scenMap.vaporSourceList.remove(this);						
		// We don't need to reset the stability since we already check sources and sinks, and we are
		// switching from one to another.
		
		GlobalFuncs.scenMap.vaporSinkList.addElement(this);
		vaporType = VaporEnum.SINK;
	}
	
	/**
	 * Sets this hex to be a vapor source
	 */
	public void SetVaporSource() {
		if (vaporType == VaporEnum.SINK) GlobalFuncs.scenMap.vaporSinkList.remove(this);		
		else if (vaporType == VaporEnum.SOURCE) return;
		// We don't need to reset the stability since we already check sources and sinks, and we are
		// switching from one to another.
		
		GlobalFuncs.scenMap.vaporSourceList.addElement(this);
		vaporType = VaporEnum.SOURCE;		
	}
	
	/**
	 * Un-sets any source or sink designation in the vapor model
	 */
	public void SetVaporNormal() {
		if (vaporType == VaporEnum.SINK) {
			GlobalFuncs.scenMap.vaporSinkList.remove(this);
			GlobalFuncs.ticksStable = 0;
		}
		else if (vaporType == VaporEnum.SOURCE) {
			GlobalFuncs.scenMap.vaporSourceList.remove(this);
			GlobalFuncs.ticksStable = 0;			// Removing a source shouldn't lead to a huge DV, but just in case.
		}
		
		vaporType = VaporEnum.NONE;
	}
		
	/**
	 * Goes through the calculations that CalcVapor does, but stores them in temporary variables and returns it
	 * @return
	 */
	public int ReturnVaporCalc() {
		int vOut = 0;
				
		for (int direction = 0; direction < 6; direction++) {
			HexOff target = new HexOff(x, y).findNeighbor(direction);
			Hex tgtHex = GlobalFuncs.scenMap.getHex(target.x, target.y);			
			int vaporXferAmount = CalcVaporOneHex(this, tgtHex);			
			
			// We don't need to worry about restricting updates to positive responses since we are only looking
			// at a single hex in isolation (whereas when doing a map update, the surrounding hexes will
			// add equal and opposite flows.  You know - skew symmetry, or something like that.
			vOut += vaporXferAmount;		 							
		}		
		
		return Math.abs(vOut);
	}
	
	/**
	 * Calculates the vapor transfer out of the origin hex to the target
	 * @param origin
	 * @param target
	 * @return
	 */
	public int CalcVaporOneHex(Hex origin, Hex tgtHex) {
		int vOut = 0;
		
		if (tgtHex.tEnum != TerrainEnum.INVALID) {
			int moveCost = Math.max(this.tType.getMoveCost(MoveClass.TRACK), 
					tgtHex.tType.getMoveCost(MoveClass.TRACK));
			double vaporXferRate = (0.16 * GlobalFuncs.flowRate) / moveCost;
			int dVapor = origin.vapor - tgtHex.vapor;				
			
			vOut = (int)(dVapor * vaporXferRate); 
		}
		return vOut;
	}
	
	/**
	 * DEFAULTS to MoveClass.TRACK
	 */
	public void CalcVapor() {
		for (int direction = 0; direction < 6; direction++) {
			HexOff target = new HexOff(x, y).findNeighbor(direction);
			Hex tgtHex = GlobalFuncs.scenMap.getHex(target.x, target.y);
			
			int vaporXferAmount = CalcVaporOneHex(this, tgtHex);
			
			if (vaporXferAmount > 0) {
				vaporOut += vaporXferAmount;
				tgtHex.vaporIn += vaporXferAmount;
			}
								
		}
	}
	
	public double CalcSharedDV() {
		return (double)deltaVapor / numSpots;
	}
	
	/**
	 * Predicts the equilibrium vapor level of this hex using a heuristic
	 * (linear interpolation between the nearest source and sink).
	 * Must have both a source and a sink on the map; otherwise it does nothing.
	 */
	public void PredictVaporLevel() {
		if (GlobalFuncs.scenMap.vaporSourceList.size() == 0 || GlobalFuncs.scenMap.vaporSinkList.size() == 0) return;
		if (vaporType != VaporEnum.NONE) return;
		
		int sourceDist = 9999;
		int sinkDist = 9999;
		HexOff myLoc = new HexOff(this.x, this.y);
		
		// Find the nearest source
		for (int i = 0; i < GlobalFuncs.scenMap.vaporSourceList.size(); i++) {
			Hex finger = GlobalFuncs.scenMap.vaporSourceList.elementAt(i);
			HexOff locSrc = new HexOff(finger.x, finger.y);
			int distance = myLoc.DistFrom(locSrc);
			
			if (distance < sourceDist) sourceDist = distance;
		}
		
		// Find the nearest sink
		for (int i = 0; i < GlobalFuncs.scenMap.vaporSinkList.size(); i++) {
			Hex finger = GlobalFuncs.scenMap.vaporSinkList.elementAt(i);
			HexOff locSink = new HexOff(finger.x, finger.y);
			int distance = myLoc.DistFrom(locSink);
			
			if (distance < sinkDist) sinkDist = distance;
		}
		
		int prediction = (25500 * sinkDist) / (sinkDist + sourceDist);
		vapor = prediction;
		
	}
	
	/** Draws a square size x size pixel representation of this hex to the GUI Main Display */
	public void DrawHexMini(int xi, int yi, int size, Graphics g) {
		Color oldBrush = g.getColor();
		Color c = this.tType.getColor();
		
		double scale = 0.0;
		int colorScale = 0;
		
		switch (GlobalFuncs.MiniMapType) {
		case VAPOR_AMT:
			scale = (double)this.vapor / 25500.0;
			colorScale = (int) (scale * 255);
			
			
			if (this.vaporType == VaporEnum.SINK) g.setColor(Color.RED);
			else if (this.vaporType == VaporEnum.SOURCE) g.setColor(Color.ORANGE); 
			else g.setColor(new Color(0, 0, colorScale));
			
			break;
		case VAPOR_DV:
			scale = (double)this.deltaVapor / GlobalFuncs.maxsingleDV;
			colorScale = (int) (scale * 255 / GlobalFuncs.flowRate);
			
			colorScale = Math.min(255, colorScale);
									
			
			if (this.vaporType == VaporEnum.SINK) g.setColor(Color.RED);
			else if (this.vaporType == VaporEnum.SOURCE) g.setColor(Color.ORANGE); 
			else g.setColor(new Color(0, 0, colorScale));			
			break;
		case TERRAIN:
			if (this.HexUnit != null) {
				if (this.HexUnit.side == SideEnum.FRIENDLY) g.setColor(Color.BLUE);
				else g.setColor(Color.MAGENTA);
			}
			else if (this.vaporType == VaporEnum.SINK) g.setColor(Color.RED);
			else if (this.vaporType == VaporEnum.SOURCE) g.setColor(Color.ORANGE); 
			else g.setColor(c);
			
			break;
		}
		
		if (this.tEnum == TerrainEnum.INVALID) g.setColor(Color.GRAY);
		
		g.fillRect(xi, yi, size, size);
		
		g.setColor(Color.BLACK);
		g.drawRect(xi, yi, size, size);
		
		
		int xCent = xi + (size / 2);
		int yCent = yi + (size / 2);
		
		if (GlobalFuncs.MiniMapType == MiniMapEnum.VAPOR_AMT || GlobalFuncs.MiniMapType == MiniMapEnum.VAPOR_DV) {
			
			
			g.setColor(tType.getColor());
			if (tEnum != TerrainEnum.INVALID && tEnum != TerrainEnum.CLEAR) g.fillRect(xCent - 1,  yCent - 1,  2,  2);
		} else {
			if (x == GlobalFuncs.scenMap.friendlyZone) g.setColor(Color.BLUE);
			else if (x == GlobalFuncs.scenMap.enemyZone) g.setColor(Color.RED);
			else {
				g.setColor(oldBrush);
				return;
			}
			
			if (tEnum != TerrainEnum.INVALID) g.fillRect(xCent - 2, yCent - 2, 4, 4); 
		}
		
		g.setColor(oldBrush);
		//GUI_NB.GCO("DEBUG: " + (xi + size) + ", " + (yi + size));
	}
	
	/**
	 * Draws this hex (to include terrain and any units in the hex) to the GUI Main Display.
	 * Size is the size of the hex in pixels
	 */
	public void DrawHex(int xi, int yi, int size, Graphics g) {
		// Loads the appropriate hex icon
		File background = null;
		File foreground = null;
		
		int hWidth = (int) (Math.sqrt(3) * size);
		int hHeight = (int) (1.5 * size);
		
		int x = xi - (hWidth / 2) - 1;
		int y = yi - (hHeight / 2) - 8;
		
		try {
			background = new File(tType.getTerrainEnum().backgroundFile);
			if (tType.getTerrainEnum().foregroundFile != null) {
				foreground = new File(tType.getTerrainEnum().foregroundFile);
			}
						
			BufferedImage img = ImageIO.read(background);
			g.drawImage(img,  x,  y, null);
			if (foreground != null) {
				img = ImageIO.read(foreground);
				g.drawImage(img,  x,  y,  null);
			}
			
			if (tEnum == TerrainEnum.INVALID) return;
			
			if (displayText) {
				Color oldBrush = g.getColor();
				g.setColor(textColor);
				g.drawString(hexText, xi - hexText.length() * 3, yi + 6);
				
				g.setColor(oldBrush);
			}
			
			if (GlobalFuncs.showVapor){
				Color oldBrush = g.getColor();
				g.setColor(textColor);
				String vaporText = "";
				if (vaporType == VaporEnum.NONE) {
					vaporText = String.valueOf(vapor);										
				} else if (vaporType == VaporEnum.SINK){
					g.setColor(Color.ORANGE);
					vaporText = "SINK";					
				} else if (vaporType == VaporEnum.SOURCE){
					g.setColor(Color.CYAN);
					vaporText = "SRC";
				}
				g.drawString(vaporText,  xi - vaporText.length() * 3,  yi - 6);
				
				
				vaporText = String.valueOf(deltaVapor);
				g.drawString(vaporText, xi - vaporText.length() * 3, yi + 6);
				
				g.setColor(oldBrush);
			}
			
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
			GUI_NB.GCO(ie.getMessage());
		}				
	}

	// https://community.oracle.com/thread/1269537?start=0&tstart=0
	public static BufferedImage convertType(BufferedImage img, int typeByteIndexed) {
		if (img.getType() == typeByteIndexed) return img;
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), typeByteIndexed);
		Graphics2D g2d = result.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		//g2d.drawRenderedImage(img,  null);
		g2d.dispose();
		return result;
	}
	
	// https://community.oracle.com/thread/1269537?start=0&tstart=0
	public static IndexColorModel rescale (IndexColorModel icm, float scaleFactor, float offset) {
		int size = icm.getMapSize();
		byte[] reds = new byte[size];
		byte[] greens = new byte[size];
		byte[] blues = new byte[size];
		byte[] alphas = new byte[size];
		
		icm.getReds(reds);
		icm.getGreens(greens);
		icm.getBlues(blues);
		icm.getAlphas(alphas);
		
		rescale(reds, scaleFactor, offset);
		rescale(greens, scaleFactor, offset);
		rescale(blues, scaleFactor, offset);
		
		return new IndexColorModel(8, size, reds, greens, blues, alphas);
	}
	
	// https://community.oracle.com/thread/1269537?start=0&tstart=0
	public static void rescale(byte[] comps, float scaleFactor, float offset) {
		for (int i = 0; i < comps.length; i++) {
			int comp = 0xff & comps[i];			
			int newComp = Math.round(comp * scaleFactor + offset);
			if (newComp < 0) newComp = 0;
			else if (newComp > 255) newComp = 255;
			comps[i] = (byte) newComp;
		}
	}
	
	// https://community.oracle.com/thread/1269537?start=0&tstart=0
	public static BufferedImage rescale (BufferedImage indexed, float scaleFactor, float offset) {
		IndexColorModel icm = (IndexColorModel) indexed.getColorModel();
		return new BufferedImage(rescale(icm, scaleFactor, offset), indexed.getRaster(), false, null);
	}
	
	public HexOff toHO() {
		HexOff converted = new HexOff(x, y);
		return converted;
	}	
	
	public String saveHexHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Hex information format: tEnum, elev, obsH, dens, osc, vapor, dVapor, vaporType\n");
		
		return buf.toString();
	}
	
	// NOTE: Does NOT save hex text 
	public String saveHex() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append(tEnum + ", ");
		buf.append(elevation + ", ");
		buf.append(obsHeight + ", ");
		buf.append(density + ", ");
		buf.append(obscuration + ", ");
		buf.append(vapor + ", ");
		buf.append(deltaVapor + ", ");
		buf.append(vaporType + "\n");
		
		return buf.toString();
	}
	
	public Hex(int xi, int yi, String readL) {
		this(xi, yi, TerrainEnum.INVALID, 0);		// Generic constructor, will fill in the gaps below
		
		String[] result = readL.split(", ");
		
		tEnum = TerrainEnum.valueOf(result[0]);
		tType = tEnum.tType;
		elevation = Integer.parseInt(result[1]);
		obsHeight = Integer.parseInt(result[2]);
		density = Integer.parseInt(result[3]);
		obscuration = Integer.parseInt(result[4]);
		vapor = Integer.parseInt(result[5]);
		deltaVapor = Integer.parseInt(result[6]);
		vaporType = VaporEnum.valueOf(result[7]);
		
		if (vaporType == VaporEnum.SINK) GlobalFuncs.scenMap.vaporSinkList.add(this);
		if (vaporType == VaporEnum.SOURCE) GlobalFuncs.scenMap.vaporSourceList.add(this); 
		
		if (result.length > 8) GUI_NB.GCO("Error reading data for hex!  Input string too long.");
	}
	
	/** Takes the direction (which of the six hex faces) from the current hex to the destination */
	public int DirectionTo(Hex destination) {
		
		double azimuth = this.toHO().AzimuthTo(destination.toHO());
		
		return (int) (azimuth / 60);
	}
	
	public String DisplayCoords() {
		return ("(" + x + ", " + y + ")");
	}
	
}
