package attribute;

import java.util.ArrayList;

import achievement.Achievement;
import achiever.Achiever;

/**
 * Attribute object used by anything that can have attributes (i.e. Player,Unit)
 * T: specifies type of data most pertinent to Attribute (i.e. ArrayList<Unit>)
 * E: specifies type of data element used to augment Attribute (i.e. Unit)
 * @author Andrew Shim, Matthew Tse
 */
public abstract class Attribute<T,E> implements java.io.Serializable, AttributeConstants {
    
    private T data;
    private ArrayList<Achievement> achievements;
    private Achiever myOwner;
    
    public Attribute(Achiever owner) {
        achievements = new ArrayList<Achievement>();
        myOwner = owner;
    }
    
    public void attachAchievement(Achievement a) {
        achievements.add(a);
    }
    
    public void removeAchievement(Achievement a) {
        achievements.remove(a);
    }
    
    /**
     * The String nametag
     * @return
     */
    public abstract String name();

    /**
     * Generic method that augments the main data parameter
     * @param dataElement
     */
    public void augmentData(E dataElement) {
        augmentDataTemplate(dataElement);
        notifyAchievements();
    }
    
    /**
     * Template method for augmentData()
     * @param dataElement
     */
    public abstract void augmentDataTemplate(E dataElement);
    
    public void notifyAchievements() {
        for (Achievement a: achievements) {
            a.notifyAchievement(myOwner,this);
        }
    }
        
    public void setData(T data) {
        this.data = data;
    }
    
    public T getData() {
        return data;
    }
    
    public Achiever getOwner() {
        return myOwner;
    }
    
    /**
     * Used to refresh parameters for attribtues that need it
     */
    public abstract void refresh();
}
