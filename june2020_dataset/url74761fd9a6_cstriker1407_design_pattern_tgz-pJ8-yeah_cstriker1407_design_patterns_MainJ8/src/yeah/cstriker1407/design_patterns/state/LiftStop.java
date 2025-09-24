package yeah.cstriker1407.design_patterns.state;

import yeah.cstriker1407.design_patterns.state.LiftStates.ILiftState;

public class LiftStop implements ILiftState
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
		System.out.println("the door is to close");
		LiftStates.currentLiftState = LiftStates.CloseState;
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
		System.out.println("the door is already stop");
	}
}
