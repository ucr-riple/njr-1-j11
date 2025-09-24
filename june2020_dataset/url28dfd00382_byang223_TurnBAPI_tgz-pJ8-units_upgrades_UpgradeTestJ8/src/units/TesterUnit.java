package units;

import units.interactions.UnitButtonAttack;
import units.interactions.UnitButtonMove;
import attribute.AttributeHealth;
import attribute.AttributeImage;
import attribute.AttributeMove;

public class TesterUnit extends Unit{
    public TesterUnit(){
        unitAttributeList.add(new AttributeMove(this, 5));
        unitAttributeList.add(new AttributeImage(this, imageFilepath()));
        unitAttributeList.add(new AttributeHealth(this, 10));
        interactionList.add(new UnitButtonMove());
        interactionList.add(new UnitButtonAttack());
    }
    @Override
    public String unitName() {
        return "TESTOR";
    }

    @Override
    public String imageFilepath() {
        return "resources/unit images/Lugia.png";

    }

    @Override
    public String addButton() {
        // TODO Auto-generated method stub
        return null;
    }

}
