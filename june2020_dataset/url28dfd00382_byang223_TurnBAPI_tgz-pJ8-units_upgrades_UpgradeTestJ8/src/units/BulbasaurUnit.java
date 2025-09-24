package units;

import attribute.AttributeAttack;
import attribute.AttributeHealth;
import attribute.AttributeImage;
import attribute.AttributeMove;

@SuppressWarnings("serial")
public class BulbasaurUnit extends Unit{

    public BulbasaurUnit(){
        unitAttributeList.add(new AttributeMove(this,5));
        unitAttributeList.add(new AttributeAttack(this,10,2,2));
        unitAttributeList.add(new AttributeHealth(this,1));
        unitAttributeList.add(new AttributeImage(this, imageFilepath()));
        super.setImage(getImage());
        AttributeHealth h = (AttributeHealth) super.getAttribute("Health");
        h.setHP(40);
    }
    @Override
    public String unitName() {
        return "Bulbasaur";
    }

    @Override
    public String imageFilepath() {
        return "resources/unit images/Bulbasaur1.png";
    }
    
    @Override
    public String addButton() {
        return "resources/buttons/addBulbasarButton.png";
    }
}
