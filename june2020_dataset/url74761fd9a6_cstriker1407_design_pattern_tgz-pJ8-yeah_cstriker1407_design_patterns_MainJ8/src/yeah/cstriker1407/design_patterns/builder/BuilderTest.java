package yeah.cstriker1407.design_patterns.builder;

/*
 * 父类设计成抽象类，供子类来继承。
 */
abstract class CarModel
{
	private boolean bIsAlarm = false;
	
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
	private boolean isAlarm()
	{
		return bIsAlarm;
	}
	public final void setAlarm(boolean isAlarm)
	{
		this.bIsAlarm = isAlarm;
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
}


abstract class CarBuilder
{
	public abstract void setAlarm(boolean isAlarm);
	public abstract CarModel getCar();	
}

class ACarBuilder extends CarBuilder
{
	private ACar aCar = new ACar();
	@Override
	public void setAlarm(boolean isAlarm)
	{
		aCar.setAlarm(isAlarm);
	}
	@Override
	public CarModel getCar()
	{
		return aCar;
	}
}
class BCarBuilder extends CarBuilder
{
	private BCar bCar = new BCar();
	@Override
	public void setAlarm(boolean isAlarm)
	{
		bCar.setAlarm(isAlarm);
	}
	@Override
	public CarModel getCar()
	{
		return bCar;
	}
}

class BuilderDirector
{
	private ACarBuilder aCarBuilder = new ACarBuilder();
	private BCarBuilder bCarBuilder = new BCarBuilder();
	
	public ACar getACar()
	{
		aCarBuilder.setAlarm(true);
		return (ACar) aCarBuilder.getCar();
	}
	
	public BCar getBCar()
	{
		bCarBuilder.setAlarm(false);
		return (BCar) bCarBuilder.getCar();
	}
}

public class BuilderTest
{
	public static void test()
	{
		BuilderDirector builderDirector = new BuilderDirector();
		builderDirector.getACar().run();
		builderDirector.getBCar().run();
	}
}

