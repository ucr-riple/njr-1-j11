package ninja;

import model.FoodItem;
import model.FoodItem.FoodItemStatus;

/**
 * Class that represents a Cook. In charge of preparing orders.
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
public class Cook extends Thread {

	private int _cookId;
    
	/**
     * The FoodItem this cook is currently being prepared.
     */
    private FoodItem _currentFoodItem;
    
    /**
     * Represents the current state of this ninja.
     */
    private boolean _running;

    /**
     * Constructor for a Cook.
     * 
     * @param id the cook's ID number
     */
    public Cook(int id) {
    	
    	_cookId = id;
        _running = false;
        
    }		

    /**
     * Get the food item this cook is currently working on.
     * 
     * @return the FoodItem currently being worked on
     */
    public FoodItem getCurrentFoodItem() {
        return _currentFoodItem;
    }
    
    /**
     * Deactivates the cook.
     */
    public void deactivate() {
        _running = false;
    }
    
    /**
     * Is the cook active or not?
     * 
     * @return true if the cook is activated; false otherwise
     */
    public boolean isActive() {
        return _running;
    }
    
    /**
     * Simulate the preparation of a food item.
     */ 
    private void prepare() {
    	
        // Get the current system time and set the current food item's prep start.
        _currentFoodItem.setStatus(FoodItemStatus.Preparing);
        
        if( Kitchen.PRINT_DIAGNOSTICS ) {
    		System.out.println(Time.formatTime(SystemTime.getTime()) + ": " +"Cook "+ _cookId +": Preparing a " + _currentFoodItem.getName());
        	System.out.println(" For: " + _currentFoodItem.getPrepTime() + " minutes");
        	System.out.println(" For: " + Time.scaleUp(Time.convertToMilliseconds(_currentFoodItem.getPrepTime())) + " ninja milliseconds");
        }
        
        try {
            // 'Prepare' the food item.
            Thread.sleep(Time.scaleUp(Time.convertToMilliseconds(_currentFoodItem.getPrepTime())));
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    /** 
     * Grab the next food item to be prepared.
     */
    private void retrieve() {
        
        // Wait for your turn to grab the next food item.
        while ((null == _currentFoodItem) && _running) {
        	_currentFoodItem = Kitchen.cookRetrieve();
        	
        	// Sleep for a bit.
        	try {
        		Thread.sleep( Kitchen.KITCHEN_THREAD_WAIT_TIME );
        	} catch( InterruptedException ex ) {}
        }
        
        if( Kitchen.PRINT_DIAGNOSTICS )
    		System.out.println(Time.formatTime(SystemTime.getTime()) + 
    				": " + "Cook " + _cookId + ": Retrived a " + 
    				_currentFoodItem.getName());
        
    }
    
    /**
     * When the cook is done preparing a food item, the cook
     * sends it to the oven.
     */
    private void sendToOven() {
    	
    	if( Kitchen.PRINT_DIAGNOSTICS )
    		System.out.println(Time.formatTime(SystemTime.getTime()) + 
    				": " + "Cook " + _cookId + ": Sending a " + 
    				_currentFoodItem.getName() + " to oven");
    	
        // Send current food item back to kitchen.
        Kitchen.sendToAwaitingOven(_currentFoodItem);
        
        // Reset the current food item.
        _currentFoodItem = null;
        
    }
    
    /**
     * The main loop for this cook.
     */
    public void run() {
        
        _running = true;
        
        while (_running) {
            
            retrieve();
            
            if ( null != _currentFoodItem) {
                prepare();
                sendToOven();
            }
            
        }
        
    }
    
}
