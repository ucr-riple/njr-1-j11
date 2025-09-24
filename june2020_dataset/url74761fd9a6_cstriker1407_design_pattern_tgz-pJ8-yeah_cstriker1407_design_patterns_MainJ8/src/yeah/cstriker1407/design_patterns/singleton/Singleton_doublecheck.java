package yeah.cstriker1407.design_patterns.singleton;

public class Singleton_doublecheck
{
	private static volatile Singleton_doublecheck instance = null;
	
	private Singleton_doublecheck(){}
	
	public static Singleton_doublecheck getInstance()
	{
		if (null == instance)
		{
			synchronized (Singleton_doublecheck.class)
			{
				if (null == instance)
				{
					instance = new Singleton_doublecheck();
				}
				return instance;
			}
		}
		return instance;
	}
	
	public void sayHello()
	{
		System.out.println("Hello from Singleton_doublecheck");
	}
}
