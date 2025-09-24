package patterns.gof.behavioral.memento;

import patterns.gof.helpers.Client;

public class MementoClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
 
        originator.setState("on");
        addOutput("state is " + originator.getState());
        caretaker.setMemento(originator.saveState());
 
        originator.setState("off");
        addOutput("state is " + originator.getState());
 
        originator.restoreState(caretaker.getMemento());
        addOutput("state is " + originator.getState());
		
		super.main("Memento");
	}
}