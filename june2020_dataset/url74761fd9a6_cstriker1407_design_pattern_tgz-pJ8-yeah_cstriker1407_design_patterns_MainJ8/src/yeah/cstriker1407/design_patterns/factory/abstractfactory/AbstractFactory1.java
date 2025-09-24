package yeah.cstriker1407.design_patterns.factory.abstractfactory;

public class AbstractFactory1
{
	public static void test()
	{
		Provider provider = new WinProvider();
		provider.produce().draw();
		
		provider = new LinuxProvider();
		provider.produce().draw();
	}
}

interface OperateFun
{
	public void draw();
	public void write();
}

class Win implements OperateFun
{
	@Override
	public void draw()
	{
		System.out.println("win draw");
	}
	@Override
	public void write()
	{
		System.out.println("linux draw");
	}
}

class Linux implements OperateFun
{
	@Override
	public void draw()
	{
		System.out.println("linux draw");
	}

	@Override
	public void write()
	{
		System.out.println("linux write");
	}
}

interface Provider
{
	public OperateFun produce();
}

class WinProvider implements Provider
{
	@Override
	public OperateFun produce()
	{
		return new Win();
	}
}
class LinuxProvider implements Provider
{
	@Override
	public OperateFun produce()
	{
		return new Linux();
	}
}

