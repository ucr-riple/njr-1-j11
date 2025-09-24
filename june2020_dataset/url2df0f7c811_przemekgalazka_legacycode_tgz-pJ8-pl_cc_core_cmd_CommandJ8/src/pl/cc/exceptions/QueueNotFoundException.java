package pl.cc.exceptions;

import pl.cc.real.RealQueue;

public class QueueNotFoundException extends Exception {
	RealQueue queue;
	
	public QueueNotFoundException(RealQueue queue){
		this.queue = queue;
	}
	
	public String getMessage(){
		return new String("Nie znaleziono kolejki: "+queue.toString());
	}
}
