package units.interactions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import map.Tile;
import modes.GameMode;
import modes.models.GameModel;
import serialization.SBufferedImage;
import units.Unit;
import ai.state.AIstateController;
import attribute.Attribute;
import attribute.AttributeAttack;
import attribute.AttributeAvailableActions;
import attribute.AttributeHealth;

/**
 * Basic attack interaction. Push the attack button, then pick a target unit in
 * range. Uses attributeAttack to calculate range, attacks left, and damage.
 * 
 * @author Matthew
 * 
 */
public class UnitButtonAttack extends ButtonTargetRequired {

    @Override
    public SBufferedImage buttonImage() {
        SBufferedImage imgS = new SBufferedImage();
        try {
            BufferedImage img = ImageIO.read(new File(
                    "resources/buttons/attackbutton.png"));
            imgS.setImage(img);
        } catch (IOException e) {
        }
        return imgS;
    }

    public void performButton(GameModel myModel) {
    	myModel.getMap().unhighlightRange(
                targetTileBackground());
        if (!myModel.getSelectedUnit().hasAttribute(Attribute.ATTACK)) {
            return;
        }

        AttributeAvailableActions availableActions = (AttributeAvailableActions) myModel.getSelectedUnit()
        .getAttribute(Attribute.AVAILABLE_ACTIONS);

        if ((availableActions).isAttributeUsed(Attribute.ATTACK)) {
            return;
        } else {
            availableActions.usedAttribute(Attribute.ATTACK);
        }
        
        AttributeAttack attack = (AttributeAttack) myModel.getSelectedUnit()
                .getAttribute(Attribute.ATTACK);
        if (attack.getAttacksLeft() < 1) {
        	GameMode.setCurrState(GameMode.SELECTED);
            return;
        }

        Unit myUnit = myModel.getSelectedUnit();

        int attackDistance = myModel.getSelectedTile().getDistance(
                myModel.getSelectedDestination());

        if (attackDistance > attack.getAttackRange()) {
            return;
        }
        if (myModel.getSelectedDestination().getUnit() == null) {
            return;
        }
        Unit target = myModel.getSelectedDestination().getUnit();
        ArrayList<Unit> myUnitsList = myModel.getCurrentPlayer()
                .getPlayerUnits().getData();

        if (myUnitsList.contains(target)) {
            return;
        }
        if (!target.hasAttribute("Health")) {
            return;
        }
        
        // send target to AIstateController
        AIstateController.sendInteraction(target);
        
        AttributeHealth health = (AttributeHealth) target
                .getAttribute("Health");
        health.decrementHP(attack.getAttackDamage());
        attack.decrementAttacksLeft(1);
       
        if (health.getHP() <= 0) {
            target.beDestroyed(myUnit,myModel.getMap());
        }

    }

    @Override
    public String toString() {
        return "Attack";
    }

    @Override
    protected int targetTileBackground() {
        return Tile.ATTACK_RANGE_TILE_BACKGROUND_RANK;
    }

    @Override
    protected boolean isTargetable(GameModel g, Tile tile) {

        Tile selectedTile = g.getSelectedTile();
        if(!g.getSelectedUnit().hasAttribute("Attack")){
            return false;
        }
        AttributeAttack att = (AttributeAttack) g.getSelectedUnit()
                .getAttribute("Attack");
        int attackRange = att.getAttackRange();
        int attackLeft = att.getAttacksLeft();
        if (attackLeft <= 0)
        	GameMode.setCurrState(GameMode.SELECTED);
        return (attackLeft > 0 && tile.getDistance(selectedTile) <= attackRange);
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

}
