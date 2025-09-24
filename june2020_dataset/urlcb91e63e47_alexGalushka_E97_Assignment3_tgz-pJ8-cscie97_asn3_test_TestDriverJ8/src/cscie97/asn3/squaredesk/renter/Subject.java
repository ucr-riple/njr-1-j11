package cscie97.asn3.squaredesk.renter;

/**
 * Implementation of Observer pattern
 * @author apgalush
 *
 */
public interface Subject 
{
    /**
     * Observer's pattern method to add Observer to the list of Observers
     */
	public void registerObserver(Observer observer);

    /**
     * Observer's pattern method to remove Observer from the list of Observers
     */
	public void removeObserver(Observer observer);

    /**
     * Observer's pattern method to notify all the registered observers
     */
	public void notifyObservers();
}
