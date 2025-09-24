package units;

import units.interactions.UnitButtonAttack;
import units.interactions.UnitButtonMove;
import units.interactions.UnitButtonNova;
import attribute.Attribute;
import attribute.AttributeAchievementList;
import attribute.AttributeAttack;
import attribute.AttributeAvailableActions;
import attribute.AttributeExperience;
import attribute.AttributeHealth;
import attribute.AttributeImage;
import attribute.AttributeKills;
import attribute.AttributeLevel;
import attribute.AttributeMove;
import attribute.AttributeReactable;
import attribute.AttributeUpgrades;


public class ElectrodeUnit extends Unit {

  public static final String ELECTRODE_ADD_BUTTON = "resources/buttons/addElectrodeButton.png";
    
    public ElectrodeUnit(){
        unitAttributeList.add(new AttributeMove(this,5));
        unitAttributeList.add(new AttributeAttack(this,10, 2,2));
        unitAttributeList.add(new AttributeHealth(this,5));
        unitAttributeList.add(new AttributeReactable(this));
        unitAttributeList.add(new AttributeLevel(this));
        unitAttributeList.add(new AttributeExperience(this));

        unitAttributeList.add(new AttributeImage(this,imageFilepath()));
        unitAttributeList.add(new AttributeUpgrades(this));
        // turn attribute, determines only one attribute interaction per turn
        // maybe connect this more to interactions class
        AttributeAvailableActions turn = new AttributeAvailableActions(this);
        turn.augmentData(Attribute.MOVES);
        turn.augmentData(Attribute.ATTACK);
        unitAttributeList.add(turn);
        unitAttributeList.add(new AttributeAchievementList(this));
        unitAttributeList.add(new AttributeKills(this));
        
        interactionList.add(new UnitButtonMove());
        interactionList.add(new UnitButtonAttack());
        interactionList.add(new UnitButtonNova());
    }
    
    @Override
    public String unitName() {
        return "Electrode";
    }
    
    @Override
    public String imageFilepath() {
        return "resources/unit images/Electrode.png";
    }

    @Override
    public String addButton() {
        return ELECTRODE_ADD_BUTTON;
    }



    public String toString() {
        return "ElectrodeUnit [unitName()=" + unitName() + ", imageFilepath()=" + imageFilepath() + ", addButton()=" + addButton() + ",attributeList=" + unitAttributeList + ", interactionList=" + interactionList +"]";
    }

}
