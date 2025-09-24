package normchan.crapssim.engine;

import java.util.Observable;
import java.util.Observer;

import normchan.crapssim.engine.event.GameEvent;
import normchan.crapssim.engine.event.NewBetEvent;

public class Logger implements Observer {
	private GameManager gameManager;
	
	public Logger(GameManager gameManager) {
		super();
		this.gameManager = gameManager;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof NewBetEvent) {
			((NewBetEvent)arg).getBet().addObserver(this);
		}

		if (gameManager.isLogGameDetails()) {
			System.out.println(((GameEvent)arg).getMessage());
		}
	}

}
