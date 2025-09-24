package ori.mas.core;

import java.util.List;

public interface Mind {

	public void percept(Percept percept);
	public void percept(Iterable<Percept> percepts);
	public Actor nextActor();

	public Agent agent();
	public void setAgent(Agent a);

	public Mind clone();

};

