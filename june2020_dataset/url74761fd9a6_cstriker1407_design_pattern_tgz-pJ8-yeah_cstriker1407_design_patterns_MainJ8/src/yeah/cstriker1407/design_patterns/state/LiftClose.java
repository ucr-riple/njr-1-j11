package yeah.cstriker1407.design_patterns.state;

import yeah.cstriker1407.design_patterns.state.LiftStates.ILiftState;

public class LiftClose implements ILiftState
{
	@Override
	public void open()
	{
		System.out.println("the door is to open");
		LiftStates.currentLiftState = LiftStates.OpenState;
	}

	@Override
	public void close()
	{
		System.out.println("the door is already close");
	}

	@Override
	public void run()
	{
		System.out.println("the door is to run");
		LiftStates.currentLiftState = LiftStates.RunState;
	}

	@Override
	public void stop()
	{
		System.out.println("the door is to stop");
		LiftStates.currentLiftState = LiftStates.StopState;
	}
}
