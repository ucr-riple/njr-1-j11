package ninja;

import model.FoodItem;

/**
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
public class DeliveryRunner extends Thread {
    
    public boolean _running;
    
    public FoodItem _currentFoodItem;
    
    /**
     * Default constructor.
     */
    public DeliveryRunner() {
        _running = false;
    }
    
    /**
     * Deactivate this runner.
     */
    public void deactivate() {
        _running = false;
    }
    
    /**
     * Get the FoodItem this runner is running.
     * 
     * @return the current FoodItem
     */
    public FoodItem getCurrentFoodItem() {
        return _currentFoodItem;
    }
    
    /**
     * Forcibly modify the FoodItem this runner is running.
     */
    public void setCurrentFoodItem(FoodItem newFI) {
        _currentFoodItem = newFI;
    }
    
    /**
     * Get the next item to be placed in waiting area.
     */
    private void retreive() {
        
    	long time = SystemTime.getTime();
        
    	while ((null == _currentFoodItem) && _running) {
        	
        	_currentFoodItem = Kitchen.deliveryRunnerRetrieve();
        	// Sleep for a bit.
        	try {
        		Thread.sleep( Kitchen.KITCHEN_THREAD_WAIT_TIME );
        	} catch( InterruptedException ex ) {}
        	time = SystemTime.getTime();
        }
        
        if( Kitchen.PRINT_DIAGNOSTICS )
    		System.out.println(Time.formatTime(time) + ": " + 
    				"DeliveryRunner retrieved " + _currentFoodItem.getName());
        	
    }
    
    /**
     * Send current food item to warming area for delivery.
     * 
     * @see Kitchen.sendToOven()
     */
    private void runToWaiting() {
        
    	if( Kitchen.PRINT_DIAGNOSTICS )
    		System.out.println(Time.formatTime(SystemTime.getTime()) + 
    				": " +"DeliveryRunner putting " + _currentFoodItem.getName() + 
    				" in warming area");
        
    	Kitchen.sendToWarmingArea(_currentFoodItem);
        _currentFoodItem = null;
        
    }
    
    /**
     * The main loop of this runner
     */
    public void run() {
        
        _running = true;
        while (_running) {
            retreive();
            if (_currentFoodItem != null)
            	runToWaiting();
        }
        
    }
    
}
