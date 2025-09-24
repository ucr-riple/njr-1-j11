package yeah.cstriker1407.design_patterns.singleton;

public class Singleton_hungry
{
	private Singleton_hungry(){}
	private static Singleton_hungry instance = new Singleton_hungry();
	public static Singleton_hungry getInstance()
	{
		return instance;
	}
	public void sayHello()
	{
		System.out.println("Hello From" + this.getClass().getName());
	}
}
