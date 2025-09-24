package attribute;

import java.util.HashMap;

import player.Player;
import units.PikachuUnit;
import units.Unit;

public class AttributeUnitTypes extends Attribute<HashMap<String,Unit>,Unit> {

    public static final Unit DEFAULT_UNIT = new PikachuUnit();
        
    public AttributeUnitTypes(Player player) {
        super(player);
        HashMap<String,Unit> playerUnits = new HashMap<String,Unit>();
        playerUnits.put(DEFAULT_UNIT.unitName(), DEFAULT_UNIT);
        super.setData(playerUnits);
    }
    
    @Override
    public String name() {
        return "Units";
    }

    public Unit createUnit(String unitName) throws InstantiationException, IllegalAccessException {
        return super.getData().get(unitName).getClass().newInstance();
    }

    @Override
    public void augmentDataTemplate(Unit data) {
        super.getData().put(data.unitName(), data);
        ((Player) super.getOwner()).getPlayerUnits().addNewUnitType(data);
        
        //TODO: CASTING HERE REMOVE
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }
    
}
