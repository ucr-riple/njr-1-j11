package patterns.gof.behavioral.state;

public class StateTwo implements Statelike {
    @Override
    public void writeName(final StateContext context, final String name) {
    	StateClient.addOutput(name.toLowerCase());
        context.setState(new StateOne());
    }
}