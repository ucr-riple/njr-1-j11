package yeah.cstriker1407.design_patterns.bridge.abstraction;

import yeah.cstriker1407.design_patterns.bridge.implementor.Product;

public class SanzaiFactory extends Factory
{
	public SanzaiFactory(Product product)
	{
		super(product);
	}

	@Override
	public void makeMoney()
	{
		super.makeMoney();
		System.out.println("sanzai factory make money");
	}
}
