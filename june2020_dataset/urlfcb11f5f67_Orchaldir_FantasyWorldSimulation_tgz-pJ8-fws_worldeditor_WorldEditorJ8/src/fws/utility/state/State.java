package fws.utility.state;

public abstract class State
{
	protected StateMgr manager_;
	
	public abstract String getName();
	
	protected void add(StateMgr manager)
	{
		manager_ = manager;
	}
	
	public void onEnter()
	{

	}

	public void onExit()
	{

	}
	
	public void processKeyboard()
	{
		
	}
	
	public void processMouse()
	{
		
	}
	
	public void render()
	{
		
	}

	public void update()
	{
		
	}
}
