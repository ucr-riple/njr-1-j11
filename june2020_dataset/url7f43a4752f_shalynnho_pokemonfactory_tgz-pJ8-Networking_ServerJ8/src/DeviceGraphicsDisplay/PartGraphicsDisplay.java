package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Location;
import factory.PartType;

public class PartGraphicsDisplay extends DeviceGraphicsDisplay  {
	Location partLocation;
	PartType partType;
	boolean invisible;
	
	public void setInvisible(boolean invisible){
		this.invisible = invisible;
	}
	
	public boolean getInvisible(){
		return this.invisible;
	}
	boolean quality = true;
	TransitionGraphicsDisplay trans;

	private final Image partImage;
	private final Image badImage;
	private final Image pokeballImage;
	private final Image badPokeballImage;

	public PartGraphicsDisplay(PartType pt) {
		invisible = false;
		partType = pt;
		partImage = partType.getImage();
		badImage = partType.getBadImage();
		pokeballImage = partType.getPokeballImage();
		badPokeballImage = partType.getBadPokeballImage();
		trans = new TransitionGraphicsDisplay(partType);
	}

	@Override
	public void setLocation(Location newLocation) {
		partLocation = new Location(newLocation);
	}

	@Override
	public void draw(JComponent c, Graphics2D g) {
		drawWithOffset(c, g, 0);
	}

	public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
		if (!quality) {
			g.drawImage(badImage, partLocation.getX() + offset,
					partLocation.getY(), c);
		} else {
			g.drawImage(partImage, partLocation.getX() + offset,
					partLocation.getY(), c);
		}
	}

	// Neetu added this

	public void drawTransition(int offset, Location loc, JComponent jc,
			Graphics2D g) {
		trans.drawTrans(offset, loc, jc, g);
	}

	// Neetu added this too
	public void drawPokeball(int offset, Location loc, JComponent jc,
			Graphics2D g) {
		if (!quality) {
			g.drawImage(badPokeballImage, loc.getX() + offset, loc.getY(), jc);
		} else {
			trans.drawPokeball(offset, loc, jc, g, pokeballImage);
		}
	}

	public Location getLocation() {
		return partLocation;
	}

	public PartType getPartType() {
		return partType;
	}

	public void setQuality(boolean quality) {
		this.quality = quality;
	}

	@Override
	public void receiveData(Request req) {
	}

}
