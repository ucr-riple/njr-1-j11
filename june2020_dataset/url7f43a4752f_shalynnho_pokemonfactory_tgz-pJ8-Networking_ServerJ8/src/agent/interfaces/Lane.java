package agent.interfaces;

import DeviceGraphics.PartGraphics;
import agent.data.Part;
import factory.PartType;

public interface Lane {

	public abstract void msgINeedPart(PartType type);

	public abstract void msgHereIsPart(Part p);

	public abstract void msgPurgeParts();

	public abstract void msgGiveMePart();

	public abstract void msgReceivePartDone(PartGraphics part);

	public abstract void msgGivePartToNestDone(PartGraphics part);

	public abstract void msgPurgeDone();

	public abstract boolean pickAndExecuteAnAction();

	public abstract void msgChangeAmplitude();

	public abstract void msgFixYourself();

	public abstract void msgBreakThis();

}