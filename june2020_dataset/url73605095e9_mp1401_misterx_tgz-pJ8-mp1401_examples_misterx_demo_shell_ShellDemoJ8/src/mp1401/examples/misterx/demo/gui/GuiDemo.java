package mp1401.examples.misterx.demo.gui;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.game.GameImpl;

public class GuiDemo {

	public static void main(String[] args) {

		Game game = GameImpl.getInstance();
		new DemoGameController(game);
	}

}
