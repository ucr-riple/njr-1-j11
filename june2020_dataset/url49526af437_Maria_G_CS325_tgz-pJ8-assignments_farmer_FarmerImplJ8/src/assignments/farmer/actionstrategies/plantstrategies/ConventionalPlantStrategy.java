package assignments.farmer.actionstrategies.plantstrategies;

import assignments.farmer.actionstrategies.PlantStrategy;

public class ConventionalPlantStrategy implements PlantStrategy{

	@Override
	public void plantInTheSpring() {
		System.out.println("No action.");
		
	}

	@Override
	public void plantInTheSummer() {
		System.out.println("Corn.");
		
	}

	@Override
	public void plantInTheFall() {
		System.out.println("No action.");
		
	}

}
