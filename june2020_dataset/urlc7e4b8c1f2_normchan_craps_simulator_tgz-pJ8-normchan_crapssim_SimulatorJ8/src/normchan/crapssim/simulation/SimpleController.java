package normchan.crapssim.simulation;

public class SimpleController extends Controller {
	private int counter = 0;
	
	public boolean isSimulationComplete() {
		counter++;

		if (counter > 10 && manager.getLayout().getBets().isEmpty())
			return true;
		return false;
	}
}
