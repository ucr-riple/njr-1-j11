package units;

import java.awt.Graphics2D;
import java.util.ArrayList;

import map.LevelMap;
import units.interactions.Interaction;
import units.upgrades.UnitUpgradable;
import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeAchievementList;
import attribute.AttributeList;
import attribute.AttributeUnitGroup;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.util.ImageUtil;

/**
 * Base unit class. It extends Sprite and it is placed on tiles on the map. New
 * unit types are created by subclassing this class, and then adding Attribute
 * objects and Interaction objects to that unit.
 * 
 * @author Matthew
 * 
 */
@SuppressWarnings("serial")
public abstract class Unit extends Sprite implements UnitUpgradable, java.io.Serializable, Achiever {


    protected AttributeList<Attribute> unitAttributeList;
    protected ArrayList<Interaction> interactionList;
    protected int xTileLoc, yTileLoc;
    private boolean selected;
    private Achiever owner;
    

    public Unit() {
        unitAttributeList = new AttributeList<Attribute>();
        interactionList = new ArrayList<Interaction>();
        unitAttributeList.add(new AttributeAchievementList(this));
//        this.setImage(unitImage());

        selected = false;
    }

    public abstract String unitName();

    public abstract String imageFilepath();

    public void beDestroyed(Unit killer, LevelMap mapModifiable) {
        this.setActive(false);
            killer.getAttribute(Attribute.KILLS).augmentData(this);
                killer.getOwner().getAttribute(Attribute.KILLS)
                        .augmentData(this);
                ((AttributeUnitGroup) owner.getAttribute(Attribute.UNIT_GROUP))
                        .remove(this);
            if (this.getAttribute(Attribute.DEATH_EFFECT) != null) {
                mapModifiable.getAttribute(Attribute.MAP_MODIFIABLE)
                        .augmentData(true);
            }
    }

    @Override
    public void addAttribute(Attribute attribute) {
        unitAttributeList.add(attribute);
    }

    @Override
    public void removeAttribute(Attribute attribute) {
        unitAttributeList.remove(attribute);
    }

    
    public ArrayList<Interaction> getInteractionList() {
        return interactionList;
    }

    public AttributeList<Attribute> getAttributeList() {
        return unitAttributeList;
    }

    /**
     * Returns an Attribute from the given String. If the Unit does not contain
     * the Attribute corresponding to that string, returns null.
     * 
     * @param attributeName
     * @return Attribute
     */
    public Attribute getAttribute(String attributeName) {
        return unitAttributeList.get(attributeName);
    }


    /**
     * returns whether a unit has a particular attribute tagged by its String.
     * 
     * @param attributeName
     * @return
     */
    public boolean hasAttribute(String attributeName) {
        for (Attribute a : unitAttributeList)
            if (a.name().equals(attributeName))
                return true;
        return false;
    }

    /**
     * Returns an Interaction from the given String tag. If the Unit does not
     * contain the Attribute corresponding to that tag string, returns null.
     * 
     * @param interactionName
     * @return
     */
    public Interaction getInteraction(String interactionName) {
        for (Interaction interaction : interactionList)
            if (interaction.toString().equals(interactionName)) {
                return interaction;
            }
        return null;
    }

    /**
     * returns whether a unit has a particular Interaction tagged by its String.
     * 
     * @param attributeName
     * @return
     */
    public boolean hasInteraction(String interactionName) {
        for (Interaction a : interactionList)
            if (a.toString().equals(interactionName))
                return true;
        return false;
    }

    public int getXTileLoc() {
        return xTileLoc;
    }

    public int getYTileLoc() {
        return yTileLoc;
    }

    public void setLoc(int xTile, int yTile) {
        xTileLoc = xTile;
        yTileLoc = yTile;
    }

    /**
     * This has to exist but starts empty. Ask Alex
     */
    public void modify() {

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public Achiever getOwner() {
    	return owner;
    }
    
    public void setOwner(Achiever player) {
    	this.owner = player;
    }

    public abstract String addButton();
    
    public void setAttributeList(AttributeList<Attribute> attributeList) {
        this.unitAttributeList = attributeList;
    }

    public void setInteraction(ArrayList<Interaction> interactionList) {
        this.interactionList = interactionList;
    }

    public void resize(int dx, int dy) {
    	if(this.getImage()==null) return;
        setImage(ImageUtil.resize(this.getImage(), dx, dy));
    }

    public void render(Graphics2D g) {
        super.render(g);
    }
    
    public void addAchievementMessage(String message) {
        unitAttributeList.get(Attribute.ACHIEVEMENTS).augmentData(message);
    }
}
