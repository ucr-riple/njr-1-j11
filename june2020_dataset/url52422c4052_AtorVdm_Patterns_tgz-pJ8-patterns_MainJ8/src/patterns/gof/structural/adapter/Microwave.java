package patterns.gof.structural.adapter;

public class Microwave implements OldMachine {
	@Override
	public void launch() {
		AdapterClient.addOutput("microwave has been launched");
	}

	@Override
	public void stop() {
		AdapterClient.addOutput("microwave has been stopped");
	}
}