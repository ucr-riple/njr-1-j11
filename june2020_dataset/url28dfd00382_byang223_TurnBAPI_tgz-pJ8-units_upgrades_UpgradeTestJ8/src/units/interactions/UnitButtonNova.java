package units.interactions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ai.state.AIstateController;
import attribute.AttributeAttack;
import attribute.AttributeExperience;
import attribute.AttributeHealth;

import map.LevelMap;
import map.Tile;
import modes.models.GameModel;
import serialization.SBufferedImage;
import units.Unit;

/**
 * Basic Area of Effect attack. Uses AttributeAttack to determine AOE range,
 * damage done, and number of AOE attacks left.
 * 
 * @author Matthew
 * 
 */
public class UnitButtonNova extends InteractionUnitButton {

    @Override
    public SBufferedImage buttonImage() {
        SBufferedImage imgS = new SBufferedImage();
        try {
            BufferedImage img = ImageIO.read(new File(
                    "resources/buttons/nova.png"));
            imgS.setImage(img);
        } catch (IOException e) {
        }
        return imgS;
    }

    @Override
    public void performButton(GameModel g) {
        if (!g.getSelectedUnit().hasAttribute("Attack")) {
            return;
        }
        AttributeAttack attack = (AttributeAttack) g.getSelectedUnit()
                .getAttribute("Attack");
        if (attack.getAttacksLeft() < 1) {
            return;
        } else
            attack.decrementAttacksLeft(1);
        Tile sourceTile = g.getSelectedTile();

        Unit myUnit = g.getSelectedUnit();
        for (Tile t : g.getMap().getTilesList()) {
            int attackDistance = attack.getAttackRange();
            ArrayList<Unit> myUnitsList = g.getCurrentPlayer().getPlayerUnits()
                    .getData();
            if (myUnitsList.contains(t.getUnit()))
                continue; // don't attack yourself or allied units.
            if (t.getDistance(sourceTile) >= attackDistance)
                continue; // out of range
            if (t.getUnit() == null)
                continue; // no unit at this tile
            Unit target = t.getUnit();
            if (!target.hasAttribute("Health"))  //cannot attack a unit without health
                continue;
            
            // send target to AIstateController
            AIstateController.sendInteraction(target);
            
            AttributeHealth health = (AttributeHealth) target
                    .getAttribute("Health");
            health.decrementHP(attack.getAttackDamage());
            if (health.getHP() <= 0) {
                target.beDestroyed(myUnit,g.getMap());

            }

        }

    }

    @Override
    public String toString() {
        return "Nova";
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

}
