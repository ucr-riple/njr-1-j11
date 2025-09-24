package normchan.crapssim.simulation;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.exception.BankruptException;

public class Controller {
	protected GameManager manager;
	private int counter = 0;
	private boolean numberPuckOn = false;
	
	public void setManager(GameManager manager) {
		this.manager = manager;
	}
	
	public void reset() {
		counter = 0;
		numberPuckOn = false;
	}

	public void run() {
		manager.getPlayer().sessionStart();
		
		try {
			while (!isSimulationComplete() && !manager.getPlayer().isBroke()) {
				manager.getPlayer().makeBet();
				manager.getLayout().roll();
			}
		} catch (BankruptException e) {
			// TODO: handle this in the course of play and just stop betting and let the rolls play out
			manager.getPlayer().setBalance(0);
		}
		
		manager.getPlayer().sessionComplete();
	}
	
	private boolean isSimulationComplete() {
		if (manager.getLayout().isNumberEstablished())
			numberPuckOn = true;
		else if (numberPuckOn) {
			numberPuckOn = false;
			counter++;
		}

		if (counter > 10 && manager.getLayout().getBets().isEmpty())
			return true;
		return false;
	}
}
