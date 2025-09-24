package patterns.gof.structural.facade;

import patterns.gof.helpers.Client;

public class FacadeClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		(new Facade()).startComputer();
		
		super.main("Facade");
	}
}