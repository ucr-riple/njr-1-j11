package observers;

import achiever.Achiever;
import attribute.Attribute;


public abstract class GameObserver<T extends Achiever> implements java.io.Serializable {
    
    public void notifyObserver(T achiever) {
        notifyObserverTemplate(achiever);
    }
     
    abstract void notifyObserverTemplate(T achiever);
        
}
