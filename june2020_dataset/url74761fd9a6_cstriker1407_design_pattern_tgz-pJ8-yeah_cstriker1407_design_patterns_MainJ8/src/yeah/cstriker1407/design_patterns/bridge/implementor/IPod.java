package yeah.cstriker1407.design_patterns.bridge.implementor;

public class IPod extends Product
{
	@Override
	public void beProducted()
	{
		System.out.println("IPod is producted");
	}

	@Override
	public void beSelled()
	{
		System.out.println("IPod is selled");
	}
}
