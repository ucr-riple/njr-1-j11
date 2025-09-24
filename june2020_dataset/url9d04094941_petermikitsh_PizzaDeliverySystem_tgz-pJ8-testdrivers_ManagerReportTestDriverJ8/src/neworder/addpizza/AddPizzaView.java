package neworder.addpizza;

import viewcontroller.GeneralView;

public abstract class AddPizzaView extends GeneralView{

	/**
	 * The menu selection key for selecting small pizza.
	 */
	public static final String SMALL_KEY = "SML";
		
	/**
	 * The menu selection key for selecting medium pizza.
	 */
	public static final String MEDIUM_KEY = "MED";
	
	/**
	 * The menu selection key for selecting large pizza.
	 */
	public static final String LARGE_KEY = "LRG";
	
	/**
	 * The menu selection key for adding topping to left
	 * side of pizza.
	 */
	public static final String LEFT_KEY = "LFT";
	
	/**
	 * The menu selection key for adding topping to right
	 * side of pizza.
	 */
	public static final String RIGHT_KEY = "RGT";
	
	/**
	 * The menu selection key for adding topping to whole
	 * pizza.
	 */
	public static final String WHOLE_KEY = "WHO";
	
	/**
	 * The menu selection key for deleting a topping.
	 */
	public static final String DELETE_KEY = "DEL";
	
	/**
	 * The menu selection key for continue.
	 */
	public static final String CONTINUE_KEY = "DON";
	
	/**
	 * The menu selection key for back.
	 */
	public static final String BACK_KEY = "BCK";
	
	
	/**
	 * The input channels for this view.
	 */
	public enum AddPizzaInChan implements InputChannel {
		
		ICSizeSelection,
		ICLocationSelection,
		ICDeleteFromList,
		ICNumericInput,
		ICContinue,
		ICToppingsSelection,
		ICQuantity,
		ICGoBack;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum AddPizzaOutChan implements OutputChannel {
		
		OCInstructions,
		OCPizzaToppingsList,
		OCAvailToppingsList,
		OCDisplayPizza,
		OCError;
		
	}
}
