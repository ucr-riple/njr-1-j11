package patterns.gof.structural.adapter;

import patterns.gof.helpers.Pattern;

public class Adapter extends FridgeMicrowave implements OldMachine, Pattern {
	@Override
	public void launch() {
		startWarmingCell1();
		startChillingCell2();
	}

	@Override
	public void stop() {
		stopWarmingCell1();
		stopChillingCell2();
	}
}