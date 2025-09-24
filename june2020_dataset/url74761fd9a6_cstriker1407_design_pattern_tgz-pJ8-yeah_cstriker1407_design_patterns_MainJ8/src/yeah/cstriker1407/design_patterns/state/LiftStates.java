package yeah.cstriker1407.design_patterns.state;

//状态模式把所研究的对象的行为包装在不同的状态对象里，每一个状态对象都属于一个抽象状态类的一个子类。状态模式的意图是让一个对象在其内部状态改变的时候，其行为也随之改变。
public class LiftStates
{
	public static interface ILiftState
	{
		public void open();
		public void close();
		public void run();
		public void stop();
	}
	public static final ILiftState OpenState = new LiftOpen();
	public static final ILiftState CloseState = new LiftClose();
	public static final ILiftState RunState = new LiftRun();
	public static final ILiftState StopState = new LiftStop();
	
	/* 简单起见，使用static */
	public static ILiftState currentLiftState;
	
	
	/* 简单起见，使用static
	 * 正常状态运行
	 */
	public static void normal()
	{
		currentLiftState = OpenState;
		currentLiftState.open();
		currentLiftState.close();
		currentLiftState.run();
		currentLiftState.stop();
	}
	
	/* 简单起见，使用static 
	 * 检测状态运行
	 */
	public static void test()
	{
		currentLiftState = OpenState;
		currentLiftState.open();
		currentLiftState.close();
		currentLiftState.open();
		currentLiftState.close();
		currentLiftState.open();
		currentLiftState.close();
		
		currentLiftState.close();
		currentLiftState.run();
		currentLiftState.stop();
		currentLiftState.run();
		currentLiftState.stop();
		currentLiftState.run();
		currentLiftState.stop();
	}
	
}



