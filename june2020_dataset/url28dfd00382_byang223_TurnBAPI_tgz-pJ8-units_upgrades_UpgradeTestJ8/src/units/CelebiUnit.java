package units;

import units.interactions.ButtonEvolve;
import units.interactions.UnitButtonAttack;
import units.interactions.UnitButtonMove;
import attribute.AttributeAchievementList;
import attribute.AttributeAttack;
import attribute.AttributeDeathEffect;
import attribute.AttributeExperience;
import attribute.AttributeHealth;
import attribute.AttributeImage;
import attribute.AttributeKills;
import attribute.AttributeLevel;
import attribute.AttributeMove;
import attribute.AttributeReactable;
import attribute.AttributeUpgrades;

public class CelebiUnit extends Unit {

 public static final String PIKACHU_ADD_BUTTON = "resources/buttons/addPikachuButton.png";
    
    public CelebiUnit(){
        unitAttributeList.add(new AttributeMove(this,5));
        unitAttributeList.add(new AttributeAttack(this,10, 2,1));
        unitAttributeList.add(new AttributeHealth(this,5));
        unitAttributeList.add(new AttributeReactable(this));
        unitAttributeList.add(new AttributeLevel(this));
        unitAttributeList.add(new AttributeExperience(this));

        unitAttributeList.add(new AttributeImage(this,imageFilepath()));
        unitAttributeList.add(new AttributeUpgrades(this));
        // turn attribute, determines only one attribute interaction per turn
        // maybe connect this more to interactions class

        unitAttributeList.add(new AttributeAchievementList(this));
        unitAttributeList.add(new AttributeKills(this));
        unitAttributeList.add(new AttributeDeathEffect(this));
        
        interactionList.add(new UnitButtonMove());
        interactionList.add(new UnitButtonAttack());
        interactionList.add(new ButtonEvolve());
    }
    @Override
    public String unitName() {
        return "Pikachu";
    }
    
    @Override
    public String imageFilepath() {
        return "resources/unit images/Celebi.png";
    }

    @Override
    public String addButton() {
        return PIKACHU_ADD_BUTTON;
    }

}
