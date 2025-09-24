package modes;

import input.InputEngine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import map.LevelMap;
import modes.models.GameModel;
import modes.models.SelectedModel;
import modes.models.StandbyModel;
import modes.models.WaitingModel;
import modes.selections.Selections;
import player.Player;
import units.CelebiUnit;
import units.HewnerUnit;
import units.PikachuFactoryUnit;
import units.PikachuUnit;
import units.Unit;
import units.WeezingUnit;
import units.reactions.HealthBasedMoveReaction;
import units.reactions.HyperSpeedReaction;
import units.reactions.ParalyzingAttackReaction;
import units.reactions.Reaction;
import achievement.AchievementLose;
import achiever.upgrades.AchieverAddUnitModification;
import ai.Bot;
import ai.DumbStrategyAI;
import ai.HumanPlayer;
import attribute.AttributeReactable;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;

import environment.Environment;
import environment.Mountain;
import environment.PoisonGas;
import environment.Portal;
import environment.Snow;
import frame.TBPanel;
import frame.display.CommandDisplay;
import frame.display.Display;
import frame.display.PlayerDisplay;
import frame.display.UnitDisplay;
import frame.display.VictoryDisplay;
import frame.layout.LeftRightLayout;
import frame.layout.TopDownLayout;

public class GameMode extends TBGameMode {
    
	// for testing
	private TBPanel gameFrame;
	private Display d, e;
	private Display dd, ddd, dddd;

	public GameMode(GameEngine arg0) {
		super(arg0);
	}

	public GameMode(GameEngine arg0, File file) {
		super(arg0, file);
	}

	public GameMode(GameEngine arg0, int x, int y) {
		super(arg0);
		setxDim(x);
		setyDim(y);
		AchievementLose.setGameMode(this);
	}

	public GameMode(GameEngine arg0, File file, int x, int y) {
		this(arg0, file);
		setxDim(x);
		setyDim(y);
	}


	protected void loadFile(File file) {
		// to be implemented
	}

	public static final int STANDBY = 0;
	public static final int SELECTED = 1;
	public static final int WAITING_FOR_DESTINATION = 2;

	public void initResources() {

		gameFrame = new TBPanel(0, 0, getxDim(), getyDim());

		setFont(fontManager.getFont(getImage("resources/BitmapFont.png")));
		setButtonGroup(new SpriteGroup("Button Groups"));
		setMap(new LevelMap(10));
		setPlayfield(new PlayField());

		if (getPlayerList() == null) {
			defaultInitialization();
		} else {
			loadUnits();
		}

		getPlayfield().setBackground(getBackground());
		getPlayfield().addGroup(getButtonGroup());

		initializeModels();

		setInputEngine(new InputEngine(this.bsInput, this));

		GameMode.setCurrState(GameMode.STANDBY);

		BufferedImage s;
		try {
			s = ImageIO.read(new File("resources/TBBackgrounds/WHITE.jpg"));
			d = new CommandDisplay(s, 500, 0, 200, 200);
			dd = new UnitDisplay(s, 0, 0, 200, 200);
			ddd = new PlayerDisplay(Selections.getPlayerList().get(0), s, 0, 0,
					200, 200);
			dddd = new PlayerDisplay(Selections.getPlayerList().get(1), s, 0,
					0, 200, 200);
			e = new VictoryDisplay(s, 0, 0, 200, 50);

		} catch (IOException e) {
			e.printStackTrace();
		}

		gameFrame.setLayout(new LeftRightLayout());

		TBPanel buttonFrame = new TBPanel(0, 0, 400, 400);
		TBPanel topFrame = new TBPanel(0, 0, 400, 400);
		topFrame.setLayout(new LeftRightLayout());
		buttonFrame.setLayout(new LeftRightLayout());
		TBPanel sideFrame = new TBPanel(0, 0, 400, 700);
		sideFrame.setLayout(new TopDownLayout());

		gameFrame.add(getMap(), "LEFT");
		topFrame.add(dd, "LEFT");
		topFrame.add(d, "RIGHT");
		buttonFrame.add(ddd, "LEFT");
		buttonFrame.add(dddd, "RIGHT");

		sideFrame.add(topFrame, "TOP");
		sideFrame.add(buttonFrame, "BOTTOM");
		gameFrame.add(sideFrame, "RIGHT");
		gameFrame.add(e, "LEFT");

		super.setPlayTime(System.currentTimeMillis());

	}

	protected void initializeModels() {
		setModelMap(new HashMap<Integer, GameModel>());
		getModelMap().put(GameMode.STANDBY, new StandbyModel(getMap()));
		getModelMap().put(GameMode.SELECTED, new SelectedModel(getMap()));
		getModelMap().put(GameMode.WAITING_FOR_DESTINATION,
				new WaitingModel(getMap()));

		for (int key : getModelMap().keySet()) {
			GameModel myModel = getModelMap().get(key);
			if (myModel.getSelectedTile() == null) {
				myModel.setSelectedTile(getMap().getTileByCoords(0, 0));
			}
			myModel.setButtonGroup(getButtonGroup());
			myModel.setEnvironmentGroup(getEnvironmentGroup());
			myModel.setPlayerList(getPlayerList());
			myModel.setCurrentPlayer(getPlayerList().get(0));
			myModel.highlightPlayerUnits();
		}
	}

	public void defaultInitialization() {

		setPlayerList(new ArrayList<Player>());

		Player player1 = new HumanPlayer("Player 1");
		Player player2 = new HumanPlayer("Player 2");
		
		// To play against the AI, set player2 to Bot instead of HumanPlayer
		Player temp = new Bot("Player 2");

		getPlayerList().add(player1);
		getPlayerList().add(player2);

		// Create environmental effect

		Snow s = new Snow(0.3, 0, 10);
		Snow s2 = new Snow(0.3, 0, 10);
		Snow s3 = new Snow(0.3, 0, 10);
		Snow s4 = new Snow(0.3, 0, 10);
		Mountain m = new Mountain(1);
		PoisonGas g = new PoisonGas(1, 4, 15);
		Portal portal = new Portal(1);
		portal.setActive(false);

		SpriteGroup myEnvironments = new SpriteGroup("Environments");
		myEnvironments.add(s);
		myEnvironments.add(s2);
		myEnvironments.add(s3);
		myEnvironments.add(s4);
		myEnvironments.add(m);
		myEnvironments.add(g);
		myEnvironments.add(portal);
		setEnvironmentGroup(myEnvironments);
		getPlayfield().addGroup(getEnvironmentGroup());

		getMap().getTileByCoords(4, 8).setEnvironment(g);
		getMap().getTileByCoords(4, 4).setEnvironment(s);
		getMap().getTileByCoords(4, 4).setEnvironment(s2);
		getMap().getTileByCoords(4, 4).setEnvironment(s3);
		getMap().getTileByCoords(4, 4).setEnvironment(s4);
		getMap().getTileByCoords(4, 5).setEnvironment(m);
		getMap().getTileByCoords(0, 1).setEnvironment(portal);

		Unit weezing = new WeezingUnit();

		getMap().getTileByCoords(4, 3).setUnit(weezing);
		AchieverAddUnitModification addUnit = new AchieverAddUnitModification(
				getPlayerList().get(0), weezing);
		addUnit.modify();
		AttributeReactable react = (AttributeReactable) weezing
				.getAttribute("Reactable");

		HashMap<Environment, Reaction> Electrodereactions = new HashMap<Environment, Reaction>();
		HashMap<Environment, Boolean> Electrodemoves = new HashMap<Environment, Boolean>();

		Electrodereactions.put(s, new HealthBasedMoveReaction());
		Electrodereactions.put(s2, new HealthBasedMoveReaction());
		Electrodereactions.put(s3, new HealthBasedMoveReaction());
		Electrodereactions.put(s4, new HealthBasedMoveReaction());
		Electrodereactions.put(g, new ParalyzingAttackReaction());
		Electrodereactions.put(portal, new HyperSpeedReaction());
		Electrodemoves.put(m, new Boolean(false));
		react.setMap(Electrodereactions);
		react.setMoves(Electrodemoves);

		Unit pika5 = new PikachuUnit();
		getMap().getTileByCoords(5, 7).setUnit(pika5);
		AchieverAddUnitModification addUnit2 = new AchieverAddUnitModification(
				getPlayerList().get(1), pika5);
		addUnit2.modify();
		AttributeReactable react2 = (AttributeReactable) pika5
				.getAttribute("Reactable");
		HashMap<Environment, Reaction> pikareactions = new HashMap<Environment, Reaction>();
		HashMap<Environment, Boolean> pikamoves = new HashMap<Environment, Boolean>();

		pikareactions.put(s, new HealthBasedMoveReaction());
		pikareactions.put(s2, new HealthBasedMoveReaction());
		pikareactions.put(s3, new HealthBasedMoveReaction());
		pikareactions.put(s4, new HealthBasedMoveReaction());
		pikareactions.put(g, new ParalyzingAttackReaction());
		pikareactions.put(portal, new HyperSpeedReaction());

		react2.setMap(pikareactions);
		react2.setMoves(pikamoves);

		Unit pika6 = new CelebiUnit();
		Unit pika7 = new PikachuUnit();
		Unit pika8 = new PikachuUnit();
		Unit pika9 = new PikachuUnit();
		Unit pika10 = new PikachuUnit();
		Unit hewner = new HewnerUnit();
		// Unit testor = new TesterUnit();

		getMap().getTileByCoords(6, 7).setUnit(pika6);
		getMap().getTileByCoords(6, 6).setUnit(pika7);
		getMap().getTileByCoords(5, 6).setUnit(pika8);
		getMap().getTileByCoords(7, 7).setUnit(pika9);
		getMap().getTileByCoords(7, 6).setUnit(pika10);
		getMap().getTileByCoords(0, 0).setUnit(hewner);
		// getMap().getTileByCoords(4, 4).setUnit(testor);

		// addUnit2.changeUnit(testor);
		// addUnit2.modify();

		addUnit.changeUnit(hewner);
		addUnit.modify();
		addUnit2.changeUnit(pika6);
		addUnit2.modify();
		addUnit2.changeUnit(pika7);
		addUnit2.modify();
		addUnit2.changeUnit(pika8);
		addUnit2.modify();
		addUnit2.changeUnit(pika9);
		addUnit2.modify();
		addUnit2.changeUnit(pika10);
		addUnit2.modify();

		Unit pikaFact = new PikachuFactoryUnit();
		getMap().getTileByCoords(2, 2).setUnit(pikaFact);
		addUnit.changeUnit(pikaFact);
		addUnit.modify();

	}

	public void render(Graphics2D g) {
		refresh(g);
		// getPlayfield().render(g);
		// getMap().render(g);
		// getDisplay().render(g);

		gameFrame.render(g);
	}

	private void refresh(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getxDim(), getyDim());
	}

	public void update(long time) {
		if (super.isGameRunning()) {
			getPlayfield().update(time);
			super.setPlayTime(System.currentTimeMillis() - super.getPlayTime());

			long currentTime = System.currentTimeMillis() - getPlayTime();

			if (getModelMap().get(getCurrState()).getCurrentPlayer().getType()
					.equals("HUMAN")) {
				getInputEngine().update(time);
			}

			else if (getModelMap().get(getCurrState()).getCurrentPlayer()
					.getType().equals("BOT")) { // Bot

				((Bot) getModelMap().get(getCurrState()).getCurrentPlayer())
						.setGameModel(getModelMap().get(getCurrentModel()));

				((Bot) getModelMap().get(getCurrState()).getCurrentPlayer())
						.setStrategy(new DumbStrategyAI());
				((Bot) getModelMap().get(getCurrState()).getCurrentPlayer())
						.completeTurn(getMap(), getCurrentModel());

				cycleTurn();
			}

		} else {

		}

	}

	protected void loadUnits() {
		// maybe think of a better way to attach

		ArrayList<Unit> units = (ArrayList<Unit>) getModelMap()
				.get(getCurrState()).getCurrentPlayer()
				.getAttribute("UnitGroup").getData();

		for (int i = 0; i < units.size(); i++) {
			Unit u = units.get(i);
			if (u != null) {
				getMap().getTileByCoords(u.getXTileLoc(), u.getYTileLoc())
						.setUnit(u);
			}
		}
	}

}