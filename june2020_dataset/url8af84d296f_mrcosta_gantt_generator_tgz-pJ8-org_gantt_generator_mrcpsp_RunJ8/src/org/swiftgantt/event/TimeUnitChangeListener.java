package org.swiftgantt.event;

import java.util.EventListener;


public interface TimeUnitChangeListener extends EventListener{
	
	public void timeUnitChanged(TimeUnitChangeEvent e);
	

}
