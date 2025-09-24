package attribute;

import achiever.Achiever;

/**
 * Boolean attribute indicated alive or dead
 * TRUE = ALIVE
 * FALSE = DEAD
 * @author aks
 *
 */
public class AttributeAlive extends Attribute<Boolean,Boolean> {

    public AttributeAlive(Achiever owner) {
        super(owner);
        super.setData(true);
    }

    @Override
    public String name() {
        return "Alive";
    }

    @Override
    public void augmentDataTemplate(Boolean dataElement) {
        super.setData(dataElement);
    }

    @Override
    public void refresh() {
        
    }

}
