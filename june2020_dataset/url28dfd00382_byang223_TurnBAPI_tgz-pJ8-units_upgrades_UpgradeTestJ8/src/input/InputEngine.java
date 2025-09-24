package input;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import modes.GameMode;

import com.golden.gamedev.engine.BaseInput;
/**
 * Our implementation of TBInputEngine
 * @author prithvi
 *
 */
public class InputEngine extends TBInputEngine{

	public InputEngine(BaseInput b, GameMode g) {
		super(b, g);
	}

	/**
	 * Our implemented database of default key bindings
	 */
	public void createDefaultKeyBindingsMap(
			HashMap<String, Integer> map) {
		map.put("input.KeyDeselectEvent", KeyEvent.VK_D);
		map.put("input.MouseDeselectEvent", MouseEvent.BUTTON3);
		map.put("input.MouseSelectEvent", MouseEvent.BUTTON1);
		map.put("input.CyclePlayerEvent", KeyEvent.VK_SPACE);
		map.put("input.MoveRightEvent", KeyEvent.VK_RIGHT);
		map.put("input.MoveLeftEvent", KeyEvent.VK_LEFT);
		map.put("input.MoveUpEvent", KeyEvent.VK_UP);
		map.put("input.MoveDownEvent", KeyEvent.VK_DOWN);
		map.put("input.KeySelectEvent", KeyEvent.VK_ENTER);
		map.put("input.MouseHoverEvent", MouseEvent.MOUSE_MOVED);
		map.put("input.AttackCommandEvent", KeyEvent.VK_A);
		map.put("input.MoveCommandEvent", KeyEvent.VK_M);
		map.put("input.ProduceCommandEvent", KeyEvent.VK_P);
		map.put("input.NovaCommandEvent", KeyEvent.VK_N);
		map.put("input.EvolveCommandEvent", KeyEvent.VK_E);
	}

	public String getFileName() {
		return "KeyBindings.txt";
	}

	/**
	 * Check every 1 second for file change
	 */
	public int getFileCheckTime() {
		return 1000;
	}

}
