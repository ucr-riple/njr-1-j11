package patterns.gof.structural.adapter;

public class CoffeeMachine implements OldMachine {
	@Override
	public void launch() {
		AdapterClient.addOutput("coffee machine has been launched");
	}

	@Override
	public void stop() {
		AdapterClient.addOutput("coffee machine has been stopped");
	}
}