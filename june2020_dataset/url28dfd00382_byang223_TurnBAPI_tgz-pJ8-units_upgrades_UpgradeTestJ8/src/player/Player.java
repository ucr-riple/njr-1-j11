package player;

import java.util.ArrayList;

import units.Unit;
import achiever.Achiever;
import achiever.upgrades.AchieverUpgradable;
import attribute.Attribute;
import attribute.AttributeAvailableActions;
import attribute.AttributeExperience;
import attribute.AttributeKills;
import attribute.AttributeLevel;
import attribute.AttributeList;
import attribute.AttributeUnitGroup;
import attribute.AttributeUnitTypes;

import com.golden.gamedev.object.Sprite;

public abstract class Player implements java.io.Serializable, AchieverUpgradable, Achiever {
    
	private String playerName;
	private AttributeList<Attribute> playerAttributeList;

	public Player(String name) {
		playerName = name;
		playerAttributeList = new AttributeList();
	}

	@Override
	public void addAttribute(Attribute attribute) {
		playerAttributeList.add(attribute);
	}

	@Override
	public void removeAttribute(Attribute attribute) {
		playerAttributeList.remove(attribute);
	}

	public Attribute getAttribute(String name) {
		return playerAttributeList.get(name);
	}

	public void setAttribute(String name, Object data) {
		playerAttributeList.get(name).setData(data);
	}
	
	public AttributeUnitGroup getPlayerUnits() {
	    return (AttributeUnitGroup) playerAttributeList.get(Attribute.UNIT_GROUP);
	}

	public AttributeUnitTypes getPlayerUnitTypes() {
	    return (AttributeUnitTypes) playerAttributeList.get(Attribute.UNITS);
	}
	
	public AttributeExperience getPlayerExperience() {
	    return (AttributeExperience) playerAttributeList.get(Attribute.EXPERIENCE);
	}

	public AttributeLevel getPlayerLevel() {
	    return (AttributeLevel) playerAttributeList.get(Attribute.LEVEL);
	}
	
	public AttributeKills getPlayerKills() {
	    return (AttributeKills) playerAttributeList.get(Attribute.KILLS);
	}
	
	public abstract String getType(); 
	
	public String getPlayerName() {
		return playerName;
	}    
	
	public void cycleUnitTurns() {
		ArrayList<Unit> units = getPlayerUnits().getData();
		for (Sprite u: units) {
			if (u != null && ((Unit) u).hasAttribute(Attribute.AVAILABLE_ACTIONS)) {
				((AttributeAvailableActions) ((Unit) u).getAttribute(Attribute.AVAILABLE_ACTIONS))
				.cycleAttributes();
//				 update the unit environmental effect if still on same tile ((Unit) u)
			}
		}
	}

	public void modify() {};
	
}
