package patterns.gof.structural.adapter;

public class FridgeMicrowave {
	public void startWarmingCell1() {
		AdapterClient.addOutput("new microwave is warming");
	}
	
	public void stopWarmingCell1() {
		AdapterClient.addOutput("new microwave is not warming anymore");
	}
	
	public void startChillingCell2() {
		AdapterClient.addOutput("new microwave is chilling");
	}
	
	public void stopChillingCell2() {
		AdapterClient.addOutput("new microwave is not chilling anymore");
	}
}