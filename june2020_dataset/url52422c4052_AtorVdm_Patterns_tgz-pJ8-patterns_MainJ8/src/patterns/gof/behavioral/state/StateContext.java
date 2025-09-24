package patterns.gof.behavioral.state;

public class StateContext {
	private Statelike myState;
    public StateContext() {
        setState(new StateOne());
    }
 
    public void setState(Statelike newState) {
        myState = newState;
    }
 
    public void writeName(String name) {
        myState.writeName(this, name);
    }
}