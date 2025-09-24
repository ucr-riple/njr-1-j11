package fws.utility.state;

import java.util.HashMap;
import java.util.Map;

public class StateMgr
{
	private Map<String, State> states_ = new HashMap<String, State>();
	private State active_state_;
	
	public void add(State state)
	{
		if(state == null)
			throw new IllegalArgumentException("state is null!");

		states_.put(state.getName(), state);
		state.add(this);
	}
	
	public State get(String name)
	{
		if(name == null)
			throw new IllegalArgumentException("name is null!");

		return states_.get(name);
	}
	
	public State getActive()
	{
		return active_state_;
	}

	public boolean setActive(String name)
	{
		if(name == null)
			throw new IllegalArgumentException("name is null!");

		State state = states_.get(name);

		if(state == null)
		{
			return false;
		}
		else if(state != active_state_)
		{
			if(active_state_ != null)
			{
				active_state_.onExit();
			}

			active_state_ = state;
			active_state_.onEnter();
		}

		return true;
	}
	
	// call active state's functions
	
	public void processKeyboard()
	{
		if(active_state_ != null)
		{
			active_state_.processKeyboard();
		}
	}
	
	public void processMouse()
	{
		if(active_state_ != null)
		{
			active_state_.processMouse();
		}
	}
	
	public void render()
	{
		if(active_state_ != null)
		{
			active_state_.render();
		}
	}

	public void update()
	{
		if(active_state_ != null)
		{
			active_state_.update();
		}
	}
}
