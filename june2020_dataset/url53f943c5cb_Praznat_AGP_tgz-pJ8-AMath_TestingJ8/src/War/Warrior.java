package War;

import java.awt.*;

import Defs.P_;
import Sentiens.Clan;
import War.CombatDefs.SoldierType;

public class Warrior extends SoldierType {
	private final Point location = new Point();
	private final Rectangle areaOccupied = new Rectangle();
	private final BattleOrders orders = new BattleOrders();
	private Clan refClan;
	
	private int fleeThresh, morale;
	
	public void draw(Graphics g) {
		if (!inView()) {return;}
		
	}
	public void goOnce() {
		if (morale <= fleeThresh) {flee();}
		else {followOrders();}
		setAreaOccupied();
	}
	public void followOrders() {}
	public void flee() {}
	
	private boolean inView() {return areaOccupied().intersects(BattleField.INSTANCE.getField());}
	private void setAreaOccupied() {
		//TODO
	}
	private Rectangle areaOccupied() {return areaOccupied;}
	
	public int getProwess() {return getRefClan().FB.getPrs(P_.COMBAT);}
	public int getStrength() {return getRefClan().FB.getPrs(P_.STRENGTH);}
	public int getMarksmanship() {return getRefClan().FB.getPrs(P_.MARKSMANSHIP);}
	public Clan getRefClan() {return refClan;}
	public void setRefClan(Clan refClan) {this.refClan = refClan;}
	
	
}
