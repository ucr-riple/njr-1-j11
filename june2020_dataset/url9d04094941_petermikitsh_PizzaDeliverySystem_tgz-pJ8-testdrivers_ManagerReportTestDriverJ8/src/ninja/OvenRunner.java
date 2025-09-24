package ninja;

import model.FoodItem;
import model.Oven;

/**
 * An oven runner is responsible for transferring Food items from the awaiting
 * oven space to the oven
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
public class OvenRunner extends Thread {

	public boolean _running;

	public FoodItem _currentFoodItem;

	/**
	 * Default constructor.
	 */
	public OvenRunner() {
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
	 * @return
	 */
	public FoodItem getCurrentFoodItem() {
		return _currentFoodItem;
	}

	/**
	 * Forcibly modify the FoodItem this running is running.
	 */
	public void setCurrentFoodItem(FoodItem newFI) {
		_currentFoodItem = newFI;
	}

	/**
	 * Get the next item to be placed in oven.
	 */
	private void retreive() {

		while ((null == _currentFoodItem) && _running) {
			
			_currentFoodItem = Kitchen.ovenRunnerRetrieve();

			// Sleep for a bit.
			try {
				Thread.sleep(Kitchen.KITCHEN_THREAD_WAIT_TIME);
			} catch (InterruptedException ex) {}
			
		}
		
		if( Kitchen.PRINT_DIAGNOSTICS )
    		System.out.println(Time.formatTime(SystemTime.getTime()) + 
    				": " +"OvenRunner retrieved " + _currentFoodItem.getName());

	}

	/**
	 * Attempt to add current food item to the next available oven
	 * 
	 * @see Kitchen.sendToOven()
	 */
	private void runToOven() {
		// get the next available oven
		if( Kitchen.PRINT_DIAGNOSTICS )
    		System.out.println(Time.formatTime(SystemTime.getTime()) + 
    				": " +"OvenRunner looking for oven with " + 
    				_currentFoodItem.getOvenSpaceUnits() + " avilable unit(s)");
		
		Oven avbl = Kitchen.getAvailableOven(_currentFoodItem.getOvenSpaceUnits());

		// If there is an available oven...
		if (null != avbl) {

			if( Kitchen.PRINT_DIAGNOSTICS ) {
	    		System.out.println(Time.formatTime(SystemTime.getTime()) + ": " +"OvenRunner found oven with " + _currentFoodItem.getOvenSpaceUnits() + " avilable unit(s)");
	    		System.out.println("\t" +_currentFoodItem.getName() + " should be in oven for " + _currentFoodItem.getCookTime() + " minutes");
	    		System.out.println("\t" +_currentFoodItem.getName() + " should be in oven for " + Time.scaleUp(Time.convertToMilliseconds(_currentFoodItem.getCookTime())) + " ninja milliseconds");
			}
			
			// Add the food item.
			Kitchen.sendToOven(_currentFoodItem, avbl);
			_currentFoodItem = null;

		} else {
			
			if( Kitchen.PRINT_DIAGNOSTICS )
	    		System.out.println(Time.formatTime(SystemTime.getTime()) + ": " +
	    				"OvenRunner cannot find available oven for " + _currentFoodItem.getName());
			
			// ...otherwise send the food item back to the awaiting oven space.
			Kitchen.sendToAwaitingOven(_currentFoodItem);
			
		}

	}

	/**
	 * The main loop of this runner.
	 */
	public void run() {

		_running = true;
		
		while (_running) {
			retreive();
			if (_currentFoodItem != null)
				runToOven();
		}
		
	}

}
