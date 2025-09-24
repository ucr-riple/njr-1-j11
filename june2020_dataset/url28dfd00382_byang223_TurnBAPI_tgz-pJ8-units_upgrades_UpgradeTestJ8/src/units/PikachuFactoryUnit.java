package units;

import attribute.AttributeHealth;
import attribute.AttributeImage;
import attribute.AttributeProduction;
import units.interactions.UnitButtonProduce;

public class PikachuFactoryUnit extends Unit{
        
    public PikachuFactoryUnit(){
        Class pikaClass = PikachuUnit.class;
        unitAttributeList.add(new AttributeProduction(this,2));
        unitAttributeList.add(new AttributeHealth(this,50)); 
        unitAttributeList.add(new AttributeImage(this,imageFilepath()));
        interactionList.add(new UnitButtonProduce(new PikachuUnit()));
    }

    @Override
    public String unitName() {
        return "PikachuFactory";
    }

    @Override
    public String imageFilepath() {
        return "resources/unit images/pikaFactory.png";
    }

    public void addUnitType(Unit unit) {
        interactionList.add(new UnitButtonProduce(unit));
    }

    @Override
    public String addButton() {
        return null;
    }

}
