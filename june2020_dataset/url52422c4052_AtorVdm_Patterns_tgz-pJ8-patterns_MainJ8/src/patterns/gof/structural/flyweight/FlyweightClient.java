package patterns.gof.structural.flyweight;

import patterns.gof.helpers.Client;

public class FlyweightClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		FlyweightFactory factory = new FlyweightFactory();
		 
		int [] characterCodes = {1,2,3};
		for (int nextCode : characterCodes){
			EnglishCharacter character = factory.getCharacter(nextCode);
			character.printCharacter();
		}
		
		super.main("Flyweight");
	}
}