package yeah.cstriker1407.design_patterns.responsechain;

public class ResponsechainTest
{
	public static void test()
	{
		ChainHandler0 c0 = new ChainHandler0();
		ChainHandler1 c1 = new ChainHandler1();
		ChainHandler2 c2 = new ChainHandler2();
		c0.setNext(c1);
		c1.setNext(c2);
		
		c0.process(0);
		c0.process(1);
		c0.process(2);
		c0.process(3);
	}
}

class ChainHandler0 extends ChainHandler
{
	public ChainHandler0()
	{
		super(0);
	}

	@Override
	protected void showInfo(int target)
	{
		System.out.println("Handler0 hanler->" + target);
	}
}

class ChainHandler1 extends ChainHandler
{
	public ChainHandler1()
	{
		super(1);
	}

	@Override
	protected void showInfo(int target)
	{
		System.out.println("Handler1 hanler->" + target);
	}
}

class ChainHandler2 extends ChainHandler
{
	public ChainHandler2()
	{
		super(2);
	}

	@Override
	protected void showInfo(int target)
	{
		System.out.println("Handler2 hanler->" + target);
	}
}

abstract class ChainHandler
{
	private int maxNum;
	private ChainHandler next;
	
	protected ChainHandler(int maxNum)
	{
		super();
		this.maxNum = maxNum;
	}

	public void setNext(ChainHandler next)
	{
		this.next = next;
	}
	
	protected abstract void showInfo(int target);
	
	public final void process(int target)
	{
		if (target <= maxNum)
		{
			showInfo(target);
		}else
		{
			if (next != null)
			{
				next.process(target);
			}else
			{
				System.out.println("NO handler");
			}
		}
	}
}
