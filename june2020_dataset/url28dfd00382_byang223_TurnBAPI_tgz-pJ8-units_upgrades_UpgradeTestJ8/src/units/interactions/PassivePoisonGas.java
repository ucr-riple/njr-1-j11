package units.interactions;

import java.util.ArrayList;

import map.Tile;
import modes.models.GameModel;
import units.Unit;
import attribute.AttributeAttack;
import attribute.AttributeExperience;
import attribute.AttributeHealth;

/**
 * Basic implementation of a passive poison gas attack. At the end of each turn,
 * it will do damage to all the units within this unit's AttackAttribute
 * attackRange.
 * 
 * @author Matthew
 * 
 */
public class PassivePoisonGas extends InteractionPassiveTurn {

    @Override
    public void performInteraction(GameModel g, Unit myUnit) {
        if (!myUnit.hasAttribute("Attack"))
            return;
        AttributeAttack att = (AttributeAttack) myUnit.getAttribute("Attack");
        ArrayList<Tile> tileList = g.getMap().getTilesList();
        for (Tile t : tileList) {
            Tile myUnitTile = g.getMap().getTileByCoords(myUnit.getXTileLoc(), myUnit.getYTileLoc());
            if(t.getDistance(myUnitTile)>att.getAttackRange()) continue; //out of range
            if (t.getUnit() == null)
                continue;
            Unit target = t.getUnit();
            ArrayList<Unit> myUnitsList = g.getCurrentPlayer().getPlayerUnits()
                    .getData();
            if (myUnitsList.contains(target))
                continue; // don't attack yourself or allied units.
            if (!target.hasAttribute("Health"))
                continue;
            AttributeHealth health = (AttributeHealth) target
                    .getAttribute("Health");
            health.decrementHP(att.getAttackDamage() / 5); // passive does 1/5
                                                           // the normal attack
                                                           // damage
            if (health.getHP() <= 0) {
                target.beDestroyed(myUnit,g.getMap());

            }

        }

    }

    @Override
    public String toString() {
        return "Poison Gas";
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

}
