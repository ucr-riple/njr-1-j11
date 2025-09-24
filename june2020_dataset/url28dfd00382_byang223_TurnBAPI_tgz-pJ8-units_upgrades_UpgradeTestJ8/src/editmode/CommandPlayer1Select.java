package editmode;

import map.Tile;
import modes.EditMode;
import modes.GameMode;
import modes.EditMode.GameState;
import modes.EditMode.Type;
import modes.EditMode.UnitOwner;
import units.Unit;

/**
 * Function for changing player to player1
 * @author tianyu shi
 * 
 */

public class CommandPlayer1Select extends EditModeCommand{
	private GameState State;
	private EditMode EM;

	public CommandPlayer1Select (double x, double y) {
		super(x, y);
		super.setImageFilepath("resources/editmode/player1.png");
		super.setClickedImageFilepath("resources/editmode/clickedPlayer1.png");
		super.setImage();
	}

	@Override
	public String CommandName() {
		return "CommandPlayer1Select";
	}

	@Override
	public void performCommand(GameState State, EditMode Mode) {
		this.State = State;
		this.EM = Mode;

		Mode.setCurrOwner(UnitOwner.PLAYER1);
	}
}