package editmode;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.golden.gamedev.object.SpriteGroup;

/**
 * Concrete superclass of the subfunctions to be instantiated
 * @author tianyu shi
 * 
 */

public class EditCommandGroup extends SpriteGroup {

	private ArrayList<EditModeCommand> buttons;

	public EditCommandGroup (String groupName) {
		super(groupName);
		buttons = new ArrayList<EditModeCommand>();
	}

	public void add(EditModeCommand button) {
		buttons.add(button);
	}

	public void remove(EditModeCommand button) {
		buttons.remove(button);
	}

	public EditModeCommand get(String name) {
		for(EditModeCommand E:buttons) {
			if(E.CommandName().equals(name)) {
				return E;
			}
		}
		return null;
	}
	
	public ArrayList<EditModeCommand> getButtons() {
		return buttons;
	}
	
	/**
	 * Because each button is a sprite that can be clicked on, it needs to be rendered
	 * 
	 */

	public void render(Graphics2D g) {
		for (EditModeCommand e: buttons) {
			e.render(g);
		}
	}
}