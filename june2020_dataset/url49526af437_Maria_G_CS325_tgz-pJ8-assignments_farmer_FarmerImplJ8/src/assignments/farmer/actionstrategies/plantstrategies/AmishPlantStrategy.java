package assignments.farmer.actionstrategies.plantstrategies;

import assignments.farmer.actionstrategies.PlantStrategy;

public class AmishPlantStrategy implements PlantStrategy{

	@Override
	public void plantInTheSpring() {
		System.out.println("Peas, lettuce, oats.");
		
	}

	@Override
	public void plantInTheSummer() {
		System.out.println("Beans, squash, tomatoes, beets, carrots.");
		
	}

	@Override
	public void plantInTheFall() {
		System.out.println("Late beans, squash, potatoes.");
		
	}

}
