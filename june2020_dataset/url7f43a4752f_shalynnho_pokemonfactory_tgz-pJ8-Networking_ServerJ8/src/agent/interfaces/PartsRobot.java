package agent.interfaces;

import java.util.List;

import agent.data.Kit;
import agent.data.Part;
import factory.KitConfig;

public interface PartsRobot {

	public abstract void msgHereIsKitConfiguration(KitConfig config);

	public abstract void msgHereAreGoodParts(Nest n, List<Part> goodparts);

	public abstract void msgUseThisKit(Kit k);

	public abstract void msgPickUpPartDone();

	public abstract void msgGivePartToKitDone();

	public abstract void msgSetDropChance(float dChance);

	public abstract void msgDropPartFromArmDone();

	public abstract boolean pickAndExecuteAnAction();

}
