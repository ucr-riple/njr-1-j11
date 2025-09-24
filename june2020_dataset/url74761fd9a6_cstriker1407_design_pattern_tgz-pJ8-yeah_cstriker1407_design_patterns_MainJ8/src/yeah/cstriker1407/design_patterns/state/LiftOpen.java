package yeah.cstriker1407.design_patterns.state;

import yeah.cstriker1407.design_patterns.state.LiftStates.ILiftState;

public class LiftOpen implements ILiftState
{
	@Override
	public void open()
	{
		System.out.println("the door is already open");
	}

	@Override
	public void close()
	{
		System.out.println("the door is to close");
		LiftStates.currentLiftState = LiftStates.CloseState;
	}

	@Override
	public void run()
	{
		System.out.println("state err");
	}

	@Override
	public void stop()
	{
		System.out.println("the door is to stop");
		LiftStates.currentLiftState = LiftStates.StopState;
	}
}
