package editmode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import modes.EditMode;
import modes.EditMode.GameState;
import modes.GameMode;
import serialization.EnvironmentElement;
import serialization.GameElement;
import serialization.MapElement;
import serialization.UnitElement;

public class CommandSave extends EditModeCommand {
	
	private GameState State;
	private EditMode EM;

	public CommandSave(double x, double y) {
		super(x, y);
		super.setImageFilepath("resources/editmode/save2.png");
        super.setClickedImageFilepath("resources/editmode/save2.png");
		super.setImage();
	}

	public String CommandName() {
		return "CommandSave";
	}

	public void performCommand(GameState State, EditMode Mode) {
		this.State = State;
		this.EM = Mode;
		
		savemap();
		savegame();
	}

	public void savemap() {

		ArrayList<UnitElement> UnitList = EditMode.getUnitElements();
		ArrayList<EnvironmentElement> EnviromentList = EditMode.getEnvironmentElements();
		int level = EditMode.getLevel();

		MapElement map = new MapElement();
		map.setState(State);
		map.setpixelsX(500);
		map.setpixelsY(500);
		map.settilesX(10);
		map.settilesY(10);
		map.setunitlist(UnitList);
		map.setEvironmentlist(EnviromentList);
		map.setLevel(level);

		String savename = JOptionPane.showInputDialog(null,
				"(MAP) ENTER FILENAME:",
				"SAVE DATA",
				JOptionPane.QUESTION_MESSAGE);

		savename += "map.ser";

		try
		{
			FileOutputStream fileOut =
					new FileOutputStream(savename);
			ObjectOutputStream out =
					new ObjectOutputStream(fileOut);
			out.writeObject(map);
			out.close();
			fileOut.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	public void savegame() {
		HashMap<Integer, MapElement> LevelMap = EditMode.getLevelMapping();

		GameElement game = new GameElement();
		game.setLevelMap(LevelMap);

		String savename = JOptionPane.showInputDialog(null,
				"(GAME) ENTER FILENAME:",
				"SAVE DATA",
				JOptionPane.QUESTION_MESSAGE);

		savename += "game.ser";

		try
		{
			FileOutputStream fileOut =
					new FileOutputStream(savename);
			ObjectOutputStream out =
					new ObjectOutputStream(fileOut);
			out.writeObject(game);
			out.close();
			fileOut.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}
}