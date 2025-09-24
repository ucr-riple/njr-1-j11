package patterns.gof.behavioral.state;

public class StateOne implements Statelike {
	private static int globalCount;
	private int count = 0;
	
    @Override
    public void writeName(final StateContext context, final String name) {
        StateClient.addOutput(name.toUpperCase());
        /* Change state after StateMultipleUpperCase's writeName() gets invoked twice */
        if(++count > globalCount) {
            context.setState(new StateTwo());
            globalCount++;
        }
    }
}