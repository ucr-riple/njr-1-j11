package attribute;

import java.util.HashMap;

import achiever.Achiever;

public class AttributeAvailableActions extends Attribute<HashMap<String,Boolean>,String> {
    
    public AttributeAvailableActions(Achiever owner) {
        super(owner);
        super.setData(new HashMap<String,Boolean>());
    }
    

    @Override
    public void refresh() {

    }
    
    public void usedAttribute(String attributeName) {
        super.getData().put(attributeName, true);
    }
    
    public void cycleAttributes() {
        for (String s: super.getData().keySet()) {
            super.augmentData(s);
        }
    }
    
    public boolean isAttributeUsed(String attributeName) {
        return new Boolean(super.getData().get(attributeName));
    }


    @Override
    public String name() {
        return "AvailableActions";
    }

    @Override
    public void augmentDataTemplate(String dataElement) {
        super.getData().put(dataElement, false);
    }
}
