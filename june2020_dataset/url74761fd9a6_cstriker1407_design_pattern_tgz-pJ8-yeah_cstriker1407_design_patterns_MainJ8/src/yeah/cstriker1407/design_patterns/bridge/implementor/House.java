package yeah.cstriker1407.design_patterns.bridge.implementor;

public class House extends Product
{
	@Override
	public void beProducted()
	{
		System.out.println("House is producted");
	}

	@Override
	public void beSelled()
	{
		System.out.println("House is selled");
	}
}
