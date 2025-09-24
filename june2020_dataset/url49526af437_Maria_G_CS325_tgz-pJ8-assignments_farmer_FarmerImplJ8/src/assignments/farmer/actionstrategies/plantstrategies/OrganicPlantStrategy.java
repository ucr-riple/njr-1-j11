package assignments.farmer.actionstrategies.plantstrategies;

import assignments.farmer.actionstrategies.PlantStrategy;

public class OrganicPlantStrategy implements PlantStrategy{

	@Override
	public void plantInTheSpring() {
		System.out.println("Peas, lettuce.");
		
	}

	@Override
	public void plantInTheSummer() {
		System.out.println("Beans, squash, tomatoes, carrots, melons, 2nd round of peas and leafy greens.");
		
	}

	@Override
	public void plantInTheFall() {
		System.out.println("Late beans, squash, potatoes, leafy greens.");
		
	}

}
