package org.swiftgantt.event;

import java.util.EventObject;

import org.swiftgantt.model.BaseTask;

/**
 * 
 * @author Yuxing Wang
 *
 */
@SuppressWarnings("unchecked")
public class PredecessorChangeEvent extends EventObject {

	
	private BaseTask predecessor;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PredecessorChangeEvent(Object source, BaseTask predecessor) {	
		super(source);
		this.predecessor = predecessor;
	}

	public BaseTask getPredecessor(){
		return predecessor;
	}

}
