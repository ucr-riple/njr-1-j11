package mp1401.examples.misterx.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.gameitems.City;
import mp1401.examples.misterx.model.gameitems.Detective;
import mp1401.examples.misterx.model.gameitems.MisterX;
import mp1401.examples.misterx.model.mapparser.MapDataParser;


public class GameCycleSimplifier {
	
	private final Game game;
	private final MisterX misterX;
	private final List<Detective> detectives;
	private int timeoutInSeconds;
	
	public GameCycleSimplifier(Game game, MisterX misterX, Detective...detectives) {
		this.game = game;
		this.misterX = misterX;
		this.detectives = new ArrayList<Detective>(Arrays.asList(detectives));
		this.timeoutInSeconds = 0;
	}
	
	public void createAndStartGame(MapDataParser mapDataParser) {
		game.init();
		game.fillMap(mapDataParser);
		game.addMisterX(misterX);
		addDetectives();
		game.startGame();
	}
	
	private void addDetectives() {
		for (Detective detective : detectives) {
			game.addDetective(detective);
		}
	}
	
	public void setStartPositions(String misterXStartPosition, String...detectivesStartPositions) {
		if(detectivesStartPositions.length < game.getDetectives().size()) {
			return;
		}
		setMisterXStartPosition(misterXStartPosition);
		setDetectivesStartPositions(detectivesStartPositions);
		
	}
	
	private void setMisterXStartPosition(String misterXStartPosition) {
		game.setStartPosition(misterX, getCityByName(misterXStartPosition));
		timeout();
	}
	
	private void setDetectivesStartPositions(String[] detectivesStartPositions) {
		int i=0;
		for (Detective detective : game.getDetectives()) {
			game.setStartPosition(detective, getCityByName(detectivesStartPositions[i++]));
			timeout();
		}
	}
	
	public void playRound(String misterXDestination, String...detectivesDestinations) {
		if(detectivesDestinations.length < game.getDetectives().size()) {
			return;
		}
		moveMisterX(misterXDestination);
		moveDetective(detectivesDestinations);
	}
	
	private void moveMisterX(String misterXDestination) {
		game.moveMisterXTo(getCityByName(misterXDestination));
		timeout();
	}
	
	private void moveDetective(String[] detectivesDestinations) {
		int i=0;
		for (Detective detective : game.getDetectives()) {
			game.moveDetectiveTo(detective, getCityByName(detectivesDestinations[i++]));
			timeout();
		}
	}
	
	private City getCityByName(String cityName) {
		return game.getMap().getCityByName(cityName);
	}
	
	public void setTimeout(int timeoutInSeconds) {
		this.timeoutInSeconds = timeoutInSeconds;
	}
	
	private void timeout() {
		try {
			Thread.sleep(timeoutInSeconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
