package yeah.cstriker1407.design_patterns.bridge.abstraction;

import yeah.cstriker1407.design_patterns.bridge.implementor.House;

public class HouseFactory extends Factory
{
	public HouseFactory(House house)
	{
		super(house);
	}

	@Override
	public void makeMoney()
	{
		super.makeMoney();
		System.out.println("house factory make money");
	}
}
