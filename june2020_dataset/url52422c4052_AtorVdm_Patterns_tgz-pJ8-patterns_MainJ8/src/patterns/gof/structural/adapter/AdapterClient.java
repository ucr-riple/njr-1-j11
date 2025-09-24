package patterns.gof.structural.adapter;

import java.util.ArrayList;
import java.util.List;

import patterns.gof.helpers.Client;

public class AdapterClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		List<OldMachine> machines = new ArrayList<OldMachine>();
		machines.add(new CoffeeMachine());
		machines.add(new Microwave());
		machines.add(new Adapter());
		
		for (OldMachine machine : machines) {
			machine.launch();
		}
		
		addOutput("all machines are working");
		
		for (OldMachine machine : machines) {
			machine.stop();
		}
		
		addOutput("all machines were stopped");
		
		super.main("Adapter");
	}
}