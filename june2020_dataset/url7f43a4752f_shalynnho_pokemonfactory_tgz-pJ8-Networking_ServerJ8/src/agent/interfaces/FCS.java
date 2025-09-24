package agent.interfaces;

import factory.Order;
import factory.PartType;

public interface FCS {

	public abstract void msgAddKitsToQueue(Order o);

	public abstract void msgStopMakingKit(Order o);

	public abstract void msgStartProduction();

	public abstract void msgBreakLane(int laneNum);

	public abstract void msgAddNewPartType(PartType part);

	public abstract void msgShippedKit();

	public abstract void msgOrderFinished(); // Agents message

}
