package editmode;

/**
 * Function for changing player to player 2
 * @author tianyu shi
 * 
 */

import modes.EditMode;
import modes.GameMode;
import modes.EditMode.GameState;
import modes.EditMode.UnitOwner;

public class CommandPlayer2Select extends EditModeCommand{
	private GameState State;
	private EditMode EM;

    public CommandPlayer2Select (double x, double y) {
        super(x, y);
        super.setImageFilepath("resources/editmode/player2.png");
        super.setClickedImageFilepath("resources/editmode/clickedPlayer2.png");
        super.setImage();
    }
    
    @Override
    public String CommandName() {
        return "CommandPlayer2Select";
    }

    @Override
    public void performCommand(GameState State, EditMode Mode) {
		this.State = State;
		this.EM = Mode;

		Mode.setCurrOwner(UnitOwner.PLAYER2);
    }
}