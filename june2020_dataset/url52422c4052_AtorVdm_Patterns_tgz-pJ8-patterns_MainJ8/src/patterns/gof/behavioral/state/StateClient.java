package patterns.gof.behavioral.state;

import patterns.gof.helpers.Client;

public class StateClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		final StateContext sc = new StateContext();
		 
        for (int i = 0; i < 10; i++) {
        	sc.writeName("Name" + i);
        }
		
		super.main("State");
	}
}