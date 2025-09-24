package patterns.gof.creational.builder;

public class ChocolatePieBuilder extends PieBuilder {
	@Override
	protected void buildDough() {
		getPie().setDough("4 eggs + 70g of sugar + 2 spoons of flour +"
				+ " 2 spoons of starch + pinch of salt + 2 bags of vanilla +"
				+ " 2g of baking powder");
	}

	@Override
	protected void buildFilling() {
		getPie().setFilling("chocolate chips + cocoa powder");
	}
}