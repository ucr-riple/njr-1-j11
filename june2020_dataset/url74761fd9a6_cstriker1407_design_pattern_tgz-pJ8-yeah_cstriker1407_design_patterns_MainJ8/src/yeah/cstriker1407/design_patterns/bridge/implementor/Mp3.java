package yeah.cstriker1407.design_patterns.bridge.implementor;

public class Mp3 extends Product
{
	@Override
	public void beProducted()
	{
		System.out.println("Mp3 is producted");
	}

	@Override
	public void beSelled()
	{
		System.out.println("Mp3 is selled");
	}
}
