package editmode;

import map.Tile;
import modes.EditMode;
import modes.GameMode;
import modes.EditMode.GameState;
import modes.EditMode.Type;
import units.Unit;

public class CommandDelete extends EditModeCommand {
	private GameState State;
	private EditMode EM;

	private Tile todelete;

	public CommandDelete (double x, double y) {
		super(x, y);
		super.setImageFilepath("resources/editmode/deleteButton.png");
		super.setClickedImageFilepath("resources/editmode/clickeddeleteButton.png");
		super.setImage();
	}

	@Override
	public String CommandName() {
		return "CommandDelete";
	}

	/**
	 * In the below, the State part make sure that a unit can only be added after the add button has been clicked.
	 * It also makes sure that no NullPointerExceptions are met by checking the conditions of the gamestate. After making sure that everything's ok, it then clears the tile
	 * 
	 */
	
	@Override
	public void performCommand(GameState State, EditMode Mode) {
		this.State = State;
		this.EM = Mode;

		if(State.equals(GameState.STANDBY)) {
			Mode.setGameState(GameState.WAITING_FOR_DESTINATION);
			return;
		}
		else{
			todelete = Mode.getClicked();
			EditModeCommand selectedButton = Mode.getselectedButton();
			if(todelete!=null) {
				if(selectedButton!=null) {
					todelete.clearUnitAndEnvironment();
				}
			}
		}
	}
}
