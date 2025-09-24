package yeah.cstriker1407.design_patterns.bridge.abstraction;

import yeah.cstriker1407.design_patterns.bridge.implementor.Product;

public abstract class Factory
{
	private Product product;
	
	public Factory(Product product)
	{
		super();
		this.product = product;
	}

	public void makeMoney()
	{
		product.beProducted();
		product.beSelled();
	}
}
