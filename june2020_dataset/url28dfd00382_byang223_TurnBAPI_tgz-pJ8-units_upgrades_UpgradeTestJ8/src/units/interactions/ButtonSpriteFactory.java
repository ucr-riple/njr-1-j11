package units.interactions;

import java.util.ArrayList;

import units.Unit;
/**
 * A button sprite factory that takes a unit, and changes all UnitButtons into sprites that the game will render.
 * @author Matthew
 *
 */
public class ButtonSpriteFactory {
    ArrayList<ButtonSprite> myButtons;
    Unit myUnit;
    public ButtonSpriteFactory(Unit u){
        myButtons = new ArrayList<ButtonSprite>();
        myUnit = u;
        populateMyButtons();
    }
    private void populateMyButtons(){
        ArrayList<Interaction> list = myUnit.getInteractionList();
        for(Interaction i: list){
            if(InteractionUnitButton.class.isAssignableFrom(i.getClass())){
                myButtons.add(new ButtonSprite((InteractionUnitButton) i));
            }
        }
    }
    public ArrayList<ButtonSprite> getButtonSpriteList(){
        return myButtons;
    }
}
