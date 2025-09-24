package achievement;

import java.util.ArrayList;

import observers.GameObserver;
import achiever.Achiever;
import attribute.Attribute;

/**
 * Achievement object super class
 * @author aks
 *
 */

public abstract class Achievement<T extends Attribute> implements java.io.Serializable {
    
    public static final String ACHIEVEMENT_TAG = "[ACHIEVEMENT]: ";
    public static final String NO_ACHIEVEMENT_MESSAGE = "No achievement!";
    
    private T observable;
    private ArrayList<GameObserver> observers;
    private String achievementMessage;
    
    public Achievement() {
        observers = new ArrayList<GameObserver>();
    } 
    
    /**
     * Called by attributed that are observed by achievements
     * @param achiever
     * @param observable
     */
    public void notifyAchievement(Achiever achiever, T observable) {
        if (handleObservable(observable)) notifyObservers(achiever);
    }
    
    /**
     * Used to set the observable field
     * @param observable
     * @return
     */
    public boolean handleObservable(T observable) {
        this.observable = observable;
        return satisfiesConditions();
    }
    
    /**
     * Implemented by Achievement subclasses to define conditions for achieving Achievement
     * @return
     */
    public abstract boolean satisfiesConditions();
    
    public T getObservable() {
        return this.observable;
    }
    
    /**
     * Iterates through observers and notifies them
     * @param achiever
     */
    public void notifyObservers(Achiever achiever) {
        for (GameObserver o: observers) {
            notifyObserver(o,achiever);
            setAchievementMessage(createAchievementMessage(achiever));
        }
    }
    
    /**
     * Template method for notify observer, can be implemented differently by the Achievement subclasses
     * @param o
     * @param achiever
     */
    abstract void notifyObserver(GameObserver o, Achiever achiever);
    
    public void attachObserver(GameObserver o) {
        observers.add(o);
    }
    
    public void removeObserver(GameObserver o) {
        observers.remove(o);
    }
    
    public void setAchievementMessage(String message) {
        achievementMessage = message;
    }
    
    public String getAchievementMessage() {
        if (achievementMessage == null) {
            return NO_ACHIEVEMENT_MESSAGE;
        }
        return achievementMessage;
    }
    
    /**
     * Creates specific achievement message which can be retrieved later to display Achievement information
     * @param achiever
     * @return
     */
    abstract String createAchievementMessage(Achiever achiever);
    
    
}