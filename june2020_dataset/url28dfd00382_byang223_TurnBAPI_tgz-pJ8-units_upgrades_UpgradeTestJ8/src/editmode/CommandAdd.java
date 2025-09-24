package editmode;

/**
 * Function for adding units onto map
 * @author tianyu shi
 * 
 */

import java.util.ArrayList;

import map.Tile;
import modes.EditMode;
import modes.EditMode.GameState;
import modes.EditMode.Type;
import modes.TBGame;
import serialization.EnvironmentElement;
import serialization.UnitElement;
import units.Unit;
import environment.Environment;

public class CommandAdd extends EditModeCommand{

	private Unit pokemonUnit;
	private Environment environment;
	private String toadd;
	private Type myType;
	private Tile destination;
	private GameState State;
	private EditMode EM;

	public CommandAdd(Double x, Double y) {
		super(x, y);
		super.setImageFilepath("resources/editmode/addButton.png");
		super.setClickedImageFilepath("resources/editmode/clickedaddButton.png");
		super.setImage();	
	}

	/**
	 * After the user clicks a unit to input in the GUI, those variable related to the object to be create
	 * are set into edit mode where CommandAdd uses those elements to make a unit
	 * 
	 */
	
	public void getunit() {
		this.toadd = EM.getkey();
		this.myType = EM.getType();

		if(toadd!=null && myType!=null) {
			if(myType.equals(myType.UNIT) | myType.equals(myType.FACTORY)) {
				processunit(toadd);
			}
			else if(myType.equals(myType.ENVIRONMENT)) {
				processenvironment(toadd);
			}
		}
	}

	public String CommandName() {
		return "CommandAdd";
	}
	
	/**
	 * Creation of specific units below
	 * 
	 */

	public void processunit(String pokemon) {
		pokemonUnit = TBGame.UnitAddMap.get(pokemon);
		super.setImageFilepath(pokemonUnit.imageFilepath());
		super.setImage();
	}

	public void processenvironment(String e) {
		environment = TBGame.EnviromentAddMap.get(e);
		super.setImageFilepath(environment.getImageURL());
		super.setImage();
	}
	
	/**
	 * In the below, the State part make sure that a unit can only be added after the add button has been clicked.
	 * It also makes sure that no NullPointerExceptions are met by checking the conditions of the gamestate
	 * 
	 * Other than that, it creates a unit based on the type of unit it is and then saves those units into an arraylist of UnitElements which it informs editmode of
	 * 
	 * 
	 */

	public void performCommand(GameState State, EditMode Mode) {
		this.State = State;
		this.EM = Mode;

		getunit();

		if(State.equals(GameState.STANDBY)) {
			Mode.setGameState(GameState.WAITING_FOR_DESTINATION);
			return;
		}
		else{
			destination = EM.getClicked();
			EditModeCommand selectedButton = EM.getselectedButton();

			if(toadd!=null && myType!=null) {

				if(destination!=null) {
					if(selectedButton!=null) {
						getunit();

						if(myType.equals(myType.UNIT) | myType.equals(myType.FACTORY)) {
							if (destination.getUnit() == null) {
								try {
									destination.setUnit(pokemonUnit.getClass().newInstance());

									ArrayList<UnitElement> UE = EM.getUnitElements();

									Unit myUnit = destination.getUnit();
									UnitElement U_E = new UnitElement();
									U_E.setUnit(myUnit);
									U_E.setUnitOwner(EM.getCurrOwner());

									UE.add(U_E);

									EM.setUnitElements(UE);

								} catch (InstantiationException e) {e.printStackTrace();
								} catch (IllegalAccessException e) {e.printStackTrace();
								}
							}
						}

						else if(myType.equals(myType.ENVIRONMENT)) {
							destination.setEnvironment(environment);

							ArrayList<EnvironmentElement> EE = EM.getEnvironmentElements();

							ArrayList<Environment> myEnvironment = destination.getEnvironment();
							EnvironmentElement E_E = new EnvironmentElement();
							E_E.setX(destination.getTileCoordinateX());
							E_E.setY(destination.getTileCoordinateY());
							E_E.setEnviroment(myEnvironment);
							EE.add(E_E);							
							EM.setEnvironmentElements(EE);
						}

						else{
							//System.out.println("SELECT ELEMENT TO PLACE!");
						}
					}
				}
			}
		}
	}

	public Type getType() {
		return myType;
	}
}