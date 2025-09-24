package attribute;

import java.util.ArrayList;

import player.Player;
import units.PikachuFactoryUnit;
import units.Unit;
import units.interactions.Interaction;

public class AttributeUnitGroup extends Attribute<ArrayList<Unit>,Unit> {
    
    public AttributeUnitGroup(Player player) {
        super(player);
        super.setData(new ArrayList<Unit>());
      
    }
    
    public void addNewUnitType(Unit unit) {
        PikachuFactoryUnit factory = null;
        for (Unit f: super.getData()) {
            if (f.unitName().equals("PikachuFactory")) {
                factory = (PikachuFactoryUnit) f;
            }
        }
        if (factory == null) {
            return;
        }
        factory.addUnitType(unit);
    }
        
    public String name() {
        return "UnitGroup";
    }
        
    public int getUnitGroupSize() {
        return super.getData().size();
    }

    public void refreshAttributesAndInteractions(){
        for (int i=0; i< super.getData().size(); i++){
            Unit u = super.getData().get(i);
            if (u !=null){
                for(Attribute a:u.getAttributeList()) a.refresh();
                for(Interaction interaction:u.getInteractionList()) interaction.refresh();
            }
        }
    }
    
    public void remove(Unit unit) {
        super.getData().remove(unit);
        super.notifyAchievements();
    }

    @Override
    public void augmentDataTemplate(Unit data) {
        super.getData().add(data);        
    }

    @Override
    public void refresh() {
        
    }
}