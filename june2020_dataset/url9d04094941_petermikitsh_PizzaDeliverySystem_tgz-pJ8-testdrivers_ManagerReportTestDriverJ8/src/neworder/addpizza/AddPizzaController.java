package neworder.addpizza;

import java.util.ArrayList;

import main.PDSViewManager;
import main.PizzaDeliverySystem;
import model.FoodItem;
import model.PizzaFoodItem;
import model.Topping;
import neworder.addpizza.AddPizzaView.AddPizzaInChan;
import neworder.addpizza.AddPizzaView.AddPizzaOutChan;
import viewcontroller.GeneralController;
import viewcontroller.GeneralView.InputChannel;

public class AddPizzaController extends GeneralController{

	
	/**
	 * The states of this controller.
	 */
	public enum AddPizzaControllerState implements ControllerState{
		
		CSWaitingForSize,
		CSDisplayingPizzaDetails,
		CSWaitingForToppingChoice,
		CSWaitingForQuantity;
	}
	
	/**
	 * Indicates if this controller is modifying an item.
	 */
	private boolean modifying;
	
	/**
	 * The PizzaFoodItem object that is currently being operated upon.
	 */
	private PizzaFoodItem currentPizzaFoodItem;
	
	/**
	 * The list of FoodItems to add to.
	 */
	private ArrayList<FoodItem> foodItemList;
	
	/**
	 * The topping to be added to the pizza
	 */
	private Topping newTopping = new Topping();
	
	/**
	 * The list of available toppings
	 */
	private ArrayList<FoodItem> availToppings = 
		new ArrayList<FoodItem>();
	
	/**
	 * The list of pizzas to give to the NewOrder controller.
	 */
	private ArrayList<PizzaFoodItem> pizzas = new ArrayList<PizzaFoodItem>();
	
	
	/**
	 * The default constructor.
	 * 
	 * @param foodItemList The list of food items to add stuff to.
	 */
	public AddPizzaController( ArrayList<FoodItem> foodItemList ) {
		
		this.availToppings = Topping.getDb().list();
		int index = 0;
		for (FoodItem topping: availToppings){
			if (topping.getName().equalsIgnoreCase("pepperoni")){
				availToppings.remove(index);
				availToppings.add(0, topping);
			}
			index ++;
		}
		this.currentPizzaFoodItem = new PizzaFoodItem();
		currentPizzaFoodItem.setSize(PizzaFoodItem.PizzaSize.PizzaSizeMedium);
		
		this.foodItemList = foodItemList;
		
	}
	
	/**
	 * A constructor that takes an option indicating if we are editing
	 *  an already added pizza as well as an already constructed pizza.
	 *  
	 *  
	 */
	public AddPizzaController( PizzaFoodItem pizza, boolean modifying ) {
		
		this( null );
		this.currentPizzaFoodItem = pizza;
		this.modifying = modifying;
		
	}
	
	/**
	 * Allows the current pizza to be set to a pre-made one. 
	 * Used when hitting back out of the add pizza screen in the gui.
	 */
	public void setCurrentPizza(PizzaFoodItem pizza){
		currentPizzaFoodItem = pizza;
	}
	
	public void enterInitialState() {
		
		
		
		if( modifying ) {
			// The initial state is the waiting for toppings.
			gotoDisplayingPizzaDetails();
			/////////((AddPizzaViewCL) view).getUserInput(); 
		} else {
			// The initial state is the waiting for size state.
			gotoWaitingForSize();
						/////////((AddPizzaViewCL) view).getUserInput();
		}
		
		if( PizzaDeliverySystem.RUN_WITH_GUI ) {
			PDSViewManager.pushView( (AddPizzaViewGUI)view );
		} else {
			((AddPizzaViewCL)view).getUserInput();
		}

	}
	
	/**
	 * Moves the controller to the WaitingForSize state.
	 */
	private void gotoWaitingForSize() {
		
		this.currentState = AddPizzaControllerState.CSWaitingForSize;
		
		view.displayString("Select a pizza size.", AddPizzaOutChan.OCInstructions);
		view.setChannelEnabled(AddPizzaInChan.ICSizeSelection, true);
		view.setChannelEnabled(AddPizzaInChan.ICGoBack, true);
		
		
	}
	
	/**
	 * Handles input in the WaitingForSize state.
	 */
	private void handleWaitingForSize( String message,
										AddPizzaInChan channel) {
		if( channel == AddPizzaInChan.ICSizeSelection){
			if ( message.equalsIgnoreCase(AddPizzaView.SMALL_KEY)){
				
				currentPizzaFoodItem.setSize(PizzaFoodItem.PizzaSize.PizzaSizeSmall);
				currentPizzaFoodItem.setName("Small Pizza");
				gotoDisplayingPizzaDetails();
			}
			else if ( message.equalsIgnoreCase(AddPizzaView.MEDIUM_KEY)){
				
				currentPizzaFoodItem.setSize(PizzaFoodItem.PizzaSize.PizzaSizeMedium);
				currentPizzaFoodItem.setName("Medium Pizza");
				gotoDisplayingPizzaDetails();
			}
			else if ( message.equalsIgnoreCase(AddPizzaView.LARGE_KEY)){
				
				currentPizzaFoodItem.setSize(PizzaFoodItem.PizzaSize.PizzaSizeLarge);
				currentPizzaFoodItem.setName("Large Pizza");
				gotoDisplayingPizzaDetails();
			}
		}
		else if ( channel == AddPizzaInChan.ICGoBack){
			this.active = false;
		}
		else {
			handleInputError("The input was invalid. Please try again.");
		}
		
		
		
	}
	
	
	
	/**
	 * Moves the controller to the DisplayPizzaDetails state.
	 */
	private void gotoDisplayingPizzaDetails() {
		
		this.currentState = AddPizzaControllerState.CSDisplayingPizzaDetails;
		
		view.displayObject(currentPizzaFoodItem, AddPizzaOutChan.OCDisplayPizza);
		view.displayString("Choose the side of the pizza to which you would like to add toppings." ,
				AddPizzaOutChan.OCInstructions);
		view.setChannelEnabled(AddPizzaInChan.ICLocationSelection, true);
		view.setChannelEnabled(AddPizzaInChan.ICDeleteFromList, true);
		view.setChannelEnabled(AddPizzaInChan.ICContinue, true);
		view.setChannelEnabled(AddPizzaInChan.ICGoBack, true);
		
		
	}
	
	/**
	 * Handles input in the DisplayingPizzaDetails state.
	 */
	private void handleDisplayingPizzaDetails( String message, 
												AddPizzaInChan channel) {
		boolean left = 
			currentPizzaFoodItem.canAddToppingToLocation(Topping.ToppingLocation.ToppingLocationLeft);
		boolean right = 
			currentPizzaFoodItem.canAddToppingToLocation(Topping.ToppingLocation.ToppingLocationRight);
		
		if ( channel == AddPizzaInChan.ICLocationSelection) {
			if ( message.equalsIgnoreCase(AddPizzaView.LEFT_KEY)){
				if ( left == true){
					newTopping.setLocation(Topping.ToppingLocation.ToppingLocationLeft);	
					gotoWaitingForToppingChoice();
				}
				else {
					view.displayString("Too many toppings on the left side. Topping not added.",
							AddPizzaOutChan.OCError);
				}
			}
			else if ( message.equalsIgnoreCase(AddPizzaView.RIGHT_KEY)){
				if ( right == true){
					newTopping.setLocation(Topping.ToppingLocation.ToppingLocationRight);
					gotoWaitingForToppingChoice();
				}
				else {
					view.displayString("Too many toppings on the right side. Topping not added.",
							AddPizzaOutChan.OCError);
				}
			}
			else if ( message.equalsIgnoreCase(AddPizzaView.WHOLE_KEY)){
				if ( right == true && left == true){
					newTopping.setLocation(Topping.ToppingLocation.ToppingLocationWhole);
					gotoWaitingForToppingChoice();
				}
				else if ( right == false && left == false){
					view.displayString("Too many toppings on the pizza. Topping not added.",
							AddPizzaOutChan.OCError);
				}
				else if ( right == false ){
					view.displayString("Too many toppings on the right side. Topping not added.",
							AddPizzaOutChan.OCError);
				}
				else{
					view.displayString("Too many toppings on the left side. Topping not added.",
							AddPizzaOutChan.OCError);
				}
			}
			else {
				handleInputError("The input was invalid. Please try again.");
			}
			
		}
		else if ( channel == AddPizzaInChan.ICDeleteFromList) {
			
			// The message should be a list index.  Get the list index
			// and delete/modify the food item from the order at that index.
			int index = -1;
			index = Integer.parseInt( message );
			if( index < 0 || 
					index >= currentPizzaFoodItem.getToppings().size() ) {
				handleInputError("Index out of range. Please try again.");
			}
			
			if( index >= 0 && index < currentPizzaFoodItem.getToppings().size() ) {
				
					currentPizzaFoodItem.removeTopping( 
						currentPizzaFoodItem.getToppings().get(index) );	
					gotoDisplayingPizzaDetails();
			}
		}
			
		else if ( channel == AddPizzaInChan.ICContinue ){
				
			// If we're not modifying an existing item, ask for a quantity,
			// otherwise return to the order display.
			if( !modifying ) {	
				gotoWaitingForQuantity();
			} else {
				this.active = false;
			}
		}
		else if ( channel == AddPizzaInChan.ICGoBack){
			gotoWaitingForSize();
		}
		else {
			handleInputError("The input was invalid. Please try again.");
		}
	}
		
	
	
	/**
	 * Moves the controller to the WaitingForToppingChoice.
	 */
	private void gotoWaitingForToppingChoice() {
		
		this.currentState = AddPizzaControllerState.CSWaitingForToppingChoice;
		
		view.setChannelEnabled(AddPizzaInChan.ICToppingsSelection, true);
		view.setChannelEnabled(AddPizzaInChan.ICGoBack, true);
		
		
	}
	
	/**
	 * Handles input in the WaitingForToppingChoice state.
	 */
	private void handleWaitingForToppingChoice( String message, 
												AddPizzaInChan channel){
		if ( channel == AddPizzaInChan.ICGoBack){
			newTopping = new Topping();
			gotoDisplayingPizzaDetails();
		}
		else{
			try{
				int choice = Integer.parseInt(message);
				
				if( choice >= 0 && choice < availToppings.size() ){
					String name = availToppings.get(choice).getName();
					double price = availToppings.get(choice).getPrice();
					newTopping.setName(name);
					currentPizzaFoodItem.addTopping(newTopping);
					newTopping = new Topping();
					gotoDisplayingPizzaDetails();
					
				}
				else {
					handleInputError("Invalid topping entry. Please try again.");
				}
			} catch( NumberFormatException e ) {
				handleInputError("The input was invalid. Please try again.");
			}
		}
		
		
	}
	
	
	/**
	 * Moves the controller to the WaitingForQuantity.
	 */
	private void gotoWaitingForQuantity() {
		
		this.currentState = AddPizzaControllerState.CSWaitingForQuantity;
		
		view.setChannelEnabled(AddPizzaInChan.ICQuantity, true);
		view.setChannelEnabled(AddPizzaInChan.ICGoBack, true);
		
	}
	
	/**
	 * Handles input in the WaitingForQuantity state.
	 */
	private void handleWaitingForQuantity( String message,
			AddPizzaInChan channel){

		pizzas.add(currentPizzaFoodItem);

		if( channel == AddPizzaInChan.ICQuantity){
			int quantity = Integer.parseInt(message);

			ArrayList<Topping> currToppings = 
					currentPizzaFoodItem.getToppings();
			PizzaFoodItem.PizzaSize currSize = 
					currentPizzaFoodItem.getSize();
			String currName = currentPizzaFoodItem.getName();

			//pizzas.add(currentPizzaFoodItem);

			//if ( quantity > 1){
			for( int i = 0; i < quantity; i++){

				PizzaFoodItem dupPizza = new PizzaFoodItem();
				dupPizza.setSize(currSize);
				dupPizza.setName(currName);

				for( Topping t : currToppings){
					Topping newTopp = 
							new Topping(t.getName(), t.getLocation(), t.getPrice());
					dupPizza.addTopping(newTopp);
				}
				// Add to the list of food items.
				foodItemList.add( dupPizza );

			}
			//}

			// Return to the main order view.
			active = false;
			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}

		} 
		else if ( channel == AddPizzaInChan.ICGoBack){
			gotoDisplayingPizzaDetails();
		}
		else {
			handleInputError("The input was invalid. Please try again.");
		}
		
		
	}

	
	public void respondToInput(String message, InputChannel channel) {

		// Check for a back button.
		if( channel == AddPizzaInChan.ICGoBack && 
				message.equalsIgnoreCase( AddPizzaView.BACK_KEY ) ) {
			
			// Set active to false.  In the GUI version, pop the current
			// view.
			this.active = false;
			
			if( PizzaDeliverySystem.RUN_WITH_GUI) {
				PDSViewManager.popView();
			}
		
		}

		// Check the current state, and call the appropriate handler method.
		switch( (AddPizzaControllerState)currentState ) {

		case CSWaitingForSize:
			handleWaitingForSize( message, 
										  (AddPizzaInChan)channel );
			break;
			
		case CSDisplayingPizzaDetails:
			handleDisplayingPizzaDetails( message, 
										(AddPizzaInChan)channel );
			break;
			
		case CSWaitingForToppingChoice:
			handleWaitingForToppingChoice( message, 
										(AddPizzaInChan)channel );
			break;
			
		case CSWaitingForQuantity:
			handleWaitingForQuantity( message, 
										(AddPizzaInChan)channel );
			break;
			

		}
		
	}
	
	/**
	 * Handles an error in the input that is sent from the view.  This 
	 *  simply writes an error message to the error output, and maintains
	 *  the same state (which is usually waiting for some input).
	 */
	public void handleInputError(String message) {
		
		view.displayString( message, AddPizzaOutChan.OCError );
		
	}
	
	public ArrayList<PizzaFoodItem> getPizzas(){
		
		this.active = true;
		this.enterInitialState();
		
		return pizzas;
	}
	
}

