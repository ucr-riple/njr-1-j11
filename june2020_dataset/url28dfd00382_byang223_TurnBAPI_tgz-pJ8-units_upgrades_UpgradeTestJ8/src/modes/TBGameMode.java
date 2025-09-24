package modes;

import input.TBInputEngine;

import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import map.LevelMap;
import map.Tile;
import modes.models.GameModel;
import modes.models.SelectedModel;
import modes.selections.Selections;
import player.Player;
import achievement.AchievementTime;
import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeList;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
/**
 * Super class which will be extended when one wishes to make a new game.  Links together all parts of the game.
 * @author Matthew
 *
 */
public abstract class TBGameMode extends GameObject {
    
	private SpriteGroup buttonGroup;
	private PlayField playfield;
	private Background background;
	private static LevelMap map;
	private GameFont font;
	private static int currState;
	private ArrayList<Player> playerList;
	private TBInputEngine inputEngine;
	private static Background tileBackground;
	private int xDim;
	private int yDim;
	private long playTime;
	private HashMap<Integer, GameModel> modelMap;
	private SpriteGroup environmentGroup;
	private boolean running;
	
	public TBGameMode(GameEngine arg0) {
		super(arg0);
		running = true;
	}
	
	public TBGameMode(GameEngine arg0, File file) {
        super(arg0);
        loadFile(file);
        running = true;
    }
    /**
     * Loads pre-saved files
     * 
     * @param file
     */
	abstract protected void loadFile(File file);
	/**
	 * Abstract method that needs to be implemented by Golden Tee objects.
	 */
	abstract public void initResources();

    abstract public void render(Graphics2D arg0);
    /**
     * Update function that gets called as the Golden Tee Framework update function is called.
     */
	abstract public void update(long arg0);
	
	/**
	 * default construction of game.  Create new units, set them on Tiles, set them to players, etc.
	 */
	abstract protected void defaultInitialization();
	
	abstract protected void loadUnits();
	
	abstract protected void initializeModels();

	public void setButtonGroup(SpriteGroup buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	public SpriteGroup getButtonGroup() {
		return buttonGroup;
	}
	
	public void setEnvironmentGroup(SpriteGroup environmentGroup) {
		this.environmentGroup = environmentGroup;
	}

	public SpriteGroup getEnvironmentGroup() {
		return environmentGroup;
	}

	public void setPlayfield(PlayField playfield) {
		this.playfield = playfield;
	}

	public PlayField getPlayfield() {
		return playfield;
	}

	public void setBackground(Background background) {
		this.background = background;
	}

	public Background getBackground() {
		return background;
	}

	public void setModelMap(HashMap<Integer, GameModel> modelMap) {
		this.modelMap = modelMap;
	}

	public HashMap<Integer, GameModel> getModelMap() {
		return modelMap;
	}

	/**
     * Keeps track of game play time.
     * @param playTime
     */
	public void setPlayTime(long playTime) {
		this.playTime = playTime;
		AchievementTime.checkPlayTime(this.playTime);
	}

	public long getPlayTime() {
		return playTime;
	}

	public void setyDim(int yDim) {
		this.yDim = yDim;
	}

	public int getyDim() {
		return yDim;
	}

	public void setxDim(int xDim) {
		this.xDim = xDim;
	}

	public int getxDim() {
		return xDim;
	}

	public static void setCurrState(int currState) {
		TBGameMode.currState = currState;
	}

	public static int getCurrState() {
		return currState;
	}

	public void setFont(GameFont font) {
		this.font = font;
	}

	public GameFont getFont() {
		return font;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public static void setTileBackground(Background tileBackground) {
		TBGameMode.tileBackground = tileBackground;
	}

	public static Background getTileBackground() {
		return tileBackground;
	}

	public void setInputEngine(TBInputEngine inputEngine) {
		this.inputEngine = inputEngine;
	}

	public TBInputEngine getInputEngine() {
		return inputEngine;
	}

	public void select(int mouseX, int mouseY) {
		getModelMap().get(getCurrState()).select(mouseX, mouseY);
	}

	public void deselectAll() {
		getModelMap().get(getCurrState()).deselectAll();
	}

	public void moveTile(int dx, int dy) {
		getModelMap().get(getCurrState()).moveTile(dx, dy);
	}

	public void selectCommand(String commandName) {
		if (getCurrentModel().getClass().getSimpleName().equals("SelectedModel"))
			((SelectedModel) getModelMap().get(getCurrState())).selectCommand(commandName);
	}

	public void cycleTurn() {
		getModelMap().get(getCurrState()).cycleTurn();
	}

	public Tile getTile() {
		return getModelMap().get(getCurrState()).getTile();
	}

	public GameModel getCurrentModel() {
		return getModelMap().get(getCurrState());
	}

	public void nullCheck() {
		getModelMap().get(getCurrState()).nullCheck();		
	}

	public static void setMap(LevelMap map) {
		TBGameMode.map = map;
	}

	public static LevelMap getMap() {
		return map;
	}
	
	public void stopGame() {
	    running = false;
	}
	
	public boolean isGameRunning() {
	    return new Boolean(running);
	}

}
