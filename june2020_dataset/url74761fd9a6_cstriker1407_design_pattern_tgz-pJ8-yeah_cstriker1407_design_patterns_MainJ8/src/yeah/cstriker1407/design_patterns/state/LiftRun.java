package yeah.cstriker1407.design_patterns.state;

import yeah.cstriker1407.design_patterns.state.LiftStates.ILiftState;

public class LiftRun implements ILiftState
{
	@Override
	public void open()
	{
		System.out.println("state err");
	}

	@Override
	public void close()
	{
		System.out.println("state err");
	}

	@Override
	public void run()
	{
		System.out.println("the door is alreay run");
	}

	@Override
	public void stop()
	{
		System.out.println("the door is to stop");
		LiftStates.currentLiftState = LiftStates.StopState;
	}
}
