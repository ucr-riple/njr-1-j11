package achiever.upgrades;

import attribute.Attribute;


public abstract class AchieverAttributeModification<T extends Attribute,E> extends AchieverDecorator<E> {

    private T attribute;
    
    public AchieverAttributeModification(AchieverUpgradable achiever, E value, String attr) {
        super(achiever, value);
        attribute = (T) this.getDecoratedAchiever().getAttribute(attr);
    }

    public T getAttribute() {
        return attribute;
    }
    
    public void setAttribute(T attr) {
        this.attribute = attr;
    }
    
    @Override
    public void modify() {
        if (getAttribute() == null || getData() == null) {
        }
        else {
            getAttribute().augmentData(getData());
        }
    }
    
}
