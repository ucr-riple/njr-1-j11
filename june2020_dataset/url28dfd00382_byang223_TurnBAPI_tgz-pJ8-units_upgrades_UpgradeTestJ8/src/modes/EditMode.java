package modes;

/**
 * EditMode, allows for the creation of units
 * 
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import map.LevelMap;
import map.Tile;
import player.Player;
import serialization.EnvironmentElement;
import serialization.GameElement;
import serialization.MapElement;
import serialization.UnitElement;
import units.Unit;
import util.Wait;
import GUI.EditModeGUI;
import ai.HumanPlayer;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ColorBackground;

import editmode.CommandAdd;
import editmode.CommandDelete;
import editmode.CommandLoad;
import editmode.CommandPlayer1Select;
import editmode.CommandPlayer2Select;
import editmode.CommandSave;
import editmode.EditCommandGroup;
import editmode.EditModeCommand;
import environment.*;

public class EditMode extends GameObject implements java.io.Serializable {

	public enum Type {
		UNIT, ENVIRONMENT, FACTORY;
	}

	public enum UnitOwner {
		PLAYER1, PLAYER2;
	}

	public enum GameState {
		STANDBY, WAITING_FOR_DESTINATION;
	}

	private EditModeGUI GUI;
	private int pixelsX;
	private int pixelsY;
	private int tilesX;
	private int tilesY;
	private String key;
	private Type myType;
	private boolean wasclicked;

	private EditCommandGroup buttonGroup;
	private PlayField playfield;
	private Background background;
	private Background menu;
	private LevelMap map;
	private Tile selectedTile;
	private Unit selectedUnit;
	private GameFont font;
	private UnitOwner currOwner;
	private EditModeCommand selectedButton;
	private GameElement save_game;
	private MapElement save_file;
	private Wait pause;
	private Tile clicked;

	private static int level;
	private static int totallevels;
	private static GameState currState;
	private static ArrayList<Player> playerList;
	private static ArrayList<UnitElement> UE;
	private static ArrayList<EnvironmentElement> EE;
	private static HashMap<Integer, MapElement> levelmap;

	public EditMode(GameEngine arg0, MapElement save_file) {
		super(arg0);
		this.save_file = save_file;
	}

	public EditMode(GameEngine arg0, GameElement save_game) {
		super(arg0);
		this.save_game = save_game;
	}

	public void initResources() {

		background = new ColorBackground(Color.WHITE, 500, 500);
		menu = new ColorBackground(Color.YELLOW, 200, 500);

		buttonGroup = new EditCommandGroup("Button Groups");

		EditModeCommand saveButton = new CommandSave(new Double(550), new Double(100));
		EditModeCommand loadButton = new CommandLoad(new Double(550), new Double(175));
		EditModeCommand player1Select = new CommandPlayer1Select(new Double(500), new Double(450));
		EditModeCommand player2Select = new CommandPlayer2Select(new Double(600), new Double(450));
		EditModeCommand addButton = new CommandAdd(new Double(550), new Double(250));
		EditModeCommand deleteButton = new CommandDelete(new Double(550), new Double(325));

		buttonGroup.add(saveButton);
		buttonGroup.add(loadButton);
		buttonGroup.add(addButton);
		buttonGroup.add(deleteButton);

		player1Select.setClickedButtonImage();
		buttonGroup.add(player1Select);
		buttonGroup.add(player2Select);

		selectedButton = null;

		background = new ColorBackground(Color.WHITE, 500, 500);

		map = new LevelMap(10);
		wasclicked = false;

		playfield = new PlayField();
		playfield.setBackground(background);
		currState = GameState.STANDBY;

		playerList = new ArrayList<Player>();
		UE = new ArrayList<UnitElement>();
		EE = new ArrayList<EnvironmentElement>();

		Player player1 = new HumanPlayer("Player 1");
		Player player2 = new HumanPlayer("Player 2");

		playerList.add(player1);
		playerList.add(player2);

		currOwner = UnitOwner.PLAYER1;

		tilesX = 10;
		tilesY = 10;
		level = 1;	

		/**
		 * Creation of the number of levels that a user wants the game to have below
		 * 
		 */

		String levelstring = JOptionPane.showInputDialog(null,
				"ENTER # OF LEVELS:",
				JOptionPane.QUESTION_MESSAGE);

		totallevels = Integer.parseInt(levelstring);


		levelmap = new HashMap<Integer, MapElement>();
		for(int i=1; i<totallevels+1; i++) {
			levelmap.put(i, new MapElement());
		}

		GUI = new EditModeGUI(this);
		GUI.launchFrame();
	}

	@Override
	public void render(Graphics2D g) {
		menu.render(g, 0, 0, 500, 0, 200, 500);
		playfield.render(g);
		buttonGroup.render(g);
		map.render(g);
		g.setColor(Color.BLACK);
		g.drawString("Current player: " + currOwner, 520, 445);
		g.drawString("Current level: " + level,544,433);
		if (selectedButton != null && selectedButton.CommandName().equals("Save")) {
			g.setColor(Color.BLACK);
			g.drawString("Saved configuration",535,421);
		}
		if (selectedButton != null && selectedButton.CommandName().equals("Load")) {
			g.setColor(Color.BLACK);
			g.drawString("Loaded configuration",535,421);
		}
	}

	/**
	 * Updates editmode based on user input. In the code below, input is based on clicking. When the user has not clicked on the add function even if a unit is selected
	 * it makes sure that you cannot add. Other than that, the code below uses reflection to decide the specific button to choose when something is clicked
	 * 
	 */

	@Override
	public void update(long time) {
		playfield.update(time);

		if (click()) {

			Tile clickedTile = map.getTileByPixels(getMouseX(), getMouseY());
			clicked = clickedTile;

			if(clicked!=null) {
				if(selectedButton!=null) {
					wasclicked = selectedButton.wasClicked(getMouseX(), getMouseY());
					selectedButton.performCommand(currState, this);
					wasclicked = false;
				}
			}

			ArrayList<EditModeCommand> myButtons = buttonGroup.getButtons();

			for (EditModeCommand e : myButtons) {
				if (e != null) {
					if (e.wasClicked(getMouseX(), getMouseY())) {
						wasclicked = true;

						if(selectedButton!=null) {
							selectedButton.setUnclickedButtonImage();
						}

						EditModeCommand myButton = e;
						e.performCommand(currState, this);

						Class myObjectClass = e.getClass();
						String myObjName = myObjectClass.getSimpleName();

						myButton.setUnclickedButtonImage();

						buttonGroup.get(myObjName).setClickedButtonImage();
						selectedButton = myButton;

						if(myObjName.equals("CommandPlayer1Select")) {
							buttonGroup.get("CommandPlayer2Select").setUnclickedButtonImage();
						}

						else if(myObjName.equals("CommandPlayer2Select")) {
							buttonGroup.get("CommandPlayer1Select").setUnclickedButtonImage();
						}
						wasclicked = false;
						break;
					}
				}
			}
		}

		if (keyPressed(KeyEvent.VK_ESCAPE)) {
			finish();
		}
	}

	/**
	 * Renders a gamefile
	 * 
	 */

	public void rendergame(GameElement tocreate) {
		levelmap = tocreate.getLevelMap();
	}

	/**
	 * Renders a mapfile
	 * The code takes the elements of the mapfile and inputs it into editmode, using reflection to instantiate objects such as Units and Environments
	 * There is actually some error in this code because while I wrote the code to create environments, which did work. Extra features implements to environment such as extra parameters
	 * caused the code to not work.
	 * 
	 */

	public void rendermap(MapElement tocreate) {

		map.clearTiles();
		UE = new ArrayList<UnitElement>();
		UE = (ArrayList<UnitElement>) tocreate.getunitlist();
		EE = new ArrayList<EnvironmentElement>();
		EE = (ArrayList<EnvironmentElement>) tocreate.getEnviromentList();

		pixelsX = tocreate.getpixelsX();
		pixelsY = tocreate.getpixelsY();
		tilesX = tocreate.gettilesX();
		tilesY = tocreate.gettilesY();
		level = tocreate.getLevel();
		currState = tocreate.getState();

		for(UnitElement U:UE) {
			Unit unit = U.getUnit();
			UnitOwner owner = U.getUnitOwner();

			int x = unit.getXTileLoc();
			int y = unit.getYTileLoc();

			try {
				Unit toset = unit.getClass().newInstance();
				map.getTileByCoords(x, y).setUnit(toset);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for(EnvironmentElement E:EE) {
			ArrayList<Environment> EArray = E.getEnviroment();
			int x = E.getX();
			int y = E.getY();

			for(Environment EA: EArray) {
				try {

					Class myObjectClass = EA.getClass();
					String myObjName = myObjectClass.getSimpleName();
				
					//double size = TBGame.EnviromentSizeMap.get(myObjName);
					//
					//Constructor<Environment> ctor = myObjectClass.getConstructor(double.class);
					//Environment instance = ctor.newInstance(size); 
					//map.getTileByCoords(x, y).setEnvironment(instance);

					if(myObjName.equals("Mountain")) {
						map.getTileByCoords(x, y).setEnvironment(new Mountain(1));
					}else if(myObjName.equals("PoisonGas")) {
						map.getTileByCoords(x, y).setEnvironment(new PoisonGas(1, 0, 1000));
					}else if(myObjName.equals("Snow")) {
						map.getTileByCoords(x, y).setEnvironment(new Snow(0.3, 0, 1000));
					}

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}	
		}
	}


	public static ArrayList<UnitElement> getUnitElements() {
		ArrayList<UnitElement> unitlist = new ArrayList<UnitElement>();
		for(UnitElement u: UE) {
			unitlist.add(u);
		}
		return unitlist;
	}

	public void setUnitElements(ArrayList<UnitElement> unitlist) {
		this.UE = unitlist;
	}

	public static ArrayList<EnvironmentElement> getEnvironmentElements() {
		ArrayList<EnvironmentElement> environmentlist = new ArrayList<EnvironmentElement>();
		for(EnvironmentElement u: EE) {
			environmentlist.add(u);
		}
		return environmentlist;
	}

	public void setEnvironmentElements(ArrayList<EnvironmentElement> environmentlist) {
		this.EE = environmentlist;
	}

	public static HashMap<Integer, MapElement> getLevelMapping() {
		HashMap<Integer, MapElement> LM = new HashMap<Integer, MapElement>();
		for(int L: levelmap.keySet()) {
			LM.put(L, levelmap.get(L));
		}
		return LM;
	}

	public static GameState getState() {
		return currState;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static int getLevel() {
		return level;
	}

	public static int getTotalLevels() {
		return totallevels;
	}

	public void setTotalLevels(int totallevels) {
		this.totallevels = totallevels;
	}

	public void exit() {
		TBGame exitgame = new TBGame();
		parent.nextGameID = exitgame.TITLE_SCREEN;
		GUI.hideFrame();
		finish();
	}

	public UnitOwner getCurrOwner() {
		return this.currOwner;
	}

	public void setCurrOwner(UnitOwner currOwner) {
		this.currOwner = currOwner;
	}

	public void setGameState(GameState currstate) {
		this.currState = currstate;
	}

	public LevelMap getLevelMap() {
		return map;
	}

	public void SetLevelMap(LevelMap map) {
		this.map = map;
	}

	public String getkey() {
		return key;
	}

	public void setkey(String key) {
		this.key = key;
	}

	public Type getType() {
		return myType;
	}

	public void setType(Type myType) {
		this.myType = myType;
	}

	public Tile getClicked() {
		return clicked;
	}

	public boolean wasclicked() {
		return this.wasclicked;
	}

	public EditModeCommand getselectedButton() {
		return selectedButton;
	}

	public void setselectedButton(EditModeCommand selectedButton) {
		this.selectedButton = selectedButton;
	}

	public void saveCurrent() {
		MapElement map = new MapElement();
		map.setState(currState);
		map.setpixelsX(500);
		map.setpixelsY(500);
		map.settilesX(10);
		map.settilesY(10);
		map.setunitlist(UE);
		map.setEvironmentlist(EE);
		map.setLevel(level);
		save_file = map;
	}

	/**
	 * When a level changes, you save the level in the game element and then proceed to render the level that you wish to edit.
	 * 
	 */

	public void changelevel(int level) {
		saveCurrent();

		levelmap.put(this.level, save_file);
		this.level = level;

		MapElement toget= new MapElement();
		toget = levelmap.get(level);
		toget.setLevel(level);

		if(currState.equals(GameState.WAITING_FOR_DESTINATION)) {
			toget.setState(GameState.WAITING_FOR_DESTINATION);
		}
		else{
			toget.setState(GameState.STANDBY);
		}

		rendermap(toget);
	}
}