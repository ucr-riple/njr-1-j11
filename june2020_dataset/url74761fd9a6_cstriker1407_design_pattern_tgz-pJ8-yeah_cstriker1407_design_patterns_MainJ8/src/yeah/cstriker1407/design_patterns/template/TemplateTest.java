package yeah.cstriker1407.design_patterns.template;

/*
 * 父类设计成抽象类，供子类来继承。
 */
abstract class CarModel
{
	/* start stop alarm 对外部调用者来讲不可见，可以设计为protected */
	protected abstract void start();
	protected abstract void stop();
	protected abstract void alarm();
	/* run 设置为final，可以将业务固化，防止子类重写 */
	public final void run()
	{
		start();
		if (isAlarm())
		{
			alarm();
		}
		stop();
	}
	/* 所谓的钩子方法，子类可以实现，这样可以实现业务的可定制性。   */
	protected boolean isAlarm()
	{
		return true;
	}
}

class ACar extends CarModel
{
	@Override
	protected void start()
	{
		System.out.println("A Car Start");
	}
	@Override
	protected void stop()
	{
		System.out.println("A Car Stop");
	}
	@Override
	protected void alarm()
	{
		System.out.println("A Car Alarm");
	}
	@Override
	protected boolean isAlarm()
	{
		return false;
	}
}

class BCar extends CarModel
{
	@Override
	protected void start()
	{
		System.out.println("B Car Start");
	}
	@Override
	protected void stop()
	{
		System.out.println("B Car Stop");
	}
	@Override
	protected void alarm()
	{
		System.out.println("B Car Alarm");
	}
	@Override
	protected boolean isAlarm()
	{
		return true;
	}
}

public class TemplateTest
{
	public static void test()
	{
		ACar aCar = new ACar();
		aCar.run();
		BCar bCar = new BCar();
		bCar.run();
	}
}
