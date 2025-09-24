package units;

import units.interactions.UnitButtonAttack;
import units.interactions.UnitButtonMove;
import units.interactions.UnitButtonNova;
import attribute.AttributeAchievementList;
import attribute.AttributeAttack;
import attribute.AttributeExperience;
import attribute.AttributeHealth;
import attribute.AttributeImage;
import attribute.AttributeKills;
import attribute.AttributeLevel;
import attribute.AttributeMove;
import attribute.AttributeReactable;

public class HewnerUnit extends Unit{
    
    public HewnerUnit(){
        unitAttributeList.add(new AttributeMove(this, 8));
        unitAttributeList.add(new AttributeAttack(this, 20, 6, 1));
        unitAttributeList.add(new AttributeHealth(this, 1000));
        unitAttributeList.add(new AttributeLevel(this));
        unitAttributeList.add(new AttributeExperience(this));
        unitAttributeList.add(new AttributeReactable(this));

        unitAttributeList.add(new AttributeAchievementList(this));
        unitAttributeList.add(new AttributeKills(this));
        unitAttributeList.add(new AttributeImage(this,imageFilepath()));
        interactionList.add(new UnitButtonMove());
        interactionList.add(new UnitButtonAttack());
        interactionList.add(new UnitButtonNova());
    }
    @Override
    public String unitName() {
        return "Mike Hewner";
    }

    @Override
    public String imageFilepath() {
        return "resources/unit images/hewner.png";
    }

    @Override
    public String addButton() {
        // TODO Auto-generated method stub
        return null;
    }

}
