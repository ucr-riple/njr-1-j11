package yeah.cstriker1407.design_patterns.singleton;


/*
 * 懒加载的动态内部类，当Singleton_demo2被加载时，单例不一顶被初始化，只有Singleton_demo2_Holder被加载时，才会初始化
 * 
 * 
 */
public class Singleton_lazy 
{
	private static class Singleton_lazy_Holder
	{
		static
		{
			System.out.println("Singleton_lazy_Holder loaded");
		}
		
		private static Singleton_lazy instance = new Singleton_lazy();
	}
	
	private Singleton_lazy()
	{
		System.out.println("Singleton_lazy instance alloced");
	}
	
	public static Singleton_lazy getInstance()
	{
		return Singleton_lazy_Holder.instance;
	}
	public void sayHello()
	{
		System.out.println("Hello");
	}
	
	
	public static void donth()
	{}
	static
	{
		System.out.println("Singleton_lazy loaded");
	}
}