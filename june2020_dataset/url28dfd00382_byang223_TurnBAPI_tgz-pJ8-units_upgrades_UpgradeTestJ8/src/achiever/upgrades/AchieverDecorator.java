package achiever.upgrades;

import attribute.Attribute;

/**
 * Abstract class used to upgrade Achievers 
 * Requires extension to modify specific attributes
 * @author aks
 *
 * @param <T>
 */
public abstract class AchieverDecorator<T> implements AchieverUpgradable {

    private AchieverUpgradable decoratedAchiever;
    private T data;
    
    
    public AchieverDecorator(AchieverUpgradable achiever, T value) {
        this.setDecoratedPlayer(achiever);
        data = value;
    }
            
    abstract public void modify();
   
    
    public AchieverUpgradable getDecoratedAchiever() {
        return decoratedAchiever;
    }
    
    public void setDecoratedPlayer(AchieverUpgradable decoratedAchiever) {
        this.decoratedAchiever = decoratedAchiever;
    }
    
    public Attribute getAttribute(String attr) {
        return getDecoratedAchiever().getAttribute(attr);
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T newValue) {
        this.data = newValue;
    }
}
