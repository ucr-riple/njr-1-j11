package agent.interfaces;

import agent.data.Kit;

public interface KitRobot {

	/**
	 * Stand will send this to tell the kitrobot how many kits it should expect
	 * to make.
	 * @param total the expected number of kits.
	 */
	public abstract void msgNeedThisManyKits(int total);

	/**
	 * Conveyor sends this to hand the kit off to the kit robot
	 * @param k kit being picked up.
	 */
	public abstract void msgHereIsKit(Kit k);

	/**
	 * Stand sends this when it needs a kit placed onto an empty location.
	 * @param standLocation location to place the kit
	 */
	public abstract void msgNeedKit(int standLocation);

	/**
	 * Conveyor sends this when a kit is ready to be picked up
	 */
	public abstract void msgKitReadyForPickup();

	/**
	 * Conveyor sends this when no kits are left on the conveyor.
	 */
	public abstract void msgNoKitsLeftOnConveyor();

	/**
	 * Stand sends this when a kit needs to be moved to the inspection area of
	 * the stand.
	 * @param k kit that needs to be moved
	 */
	public abstract void msgMoveKitToInspectionArea(Kit k);

	/**
	 * Camera sends this when a kit has passed inspection
	 */
	public abstract void msgKitPassedInspection();
	
    /**
     * Camera sends this when a kit has failed inspection
     */
    public abstract void msgKitFailedInspection();

    /**
     * GUI KitRobot sends this after the Kit to Conveyor animation has been completed.
     */
	public abstract void msgPlaceKitOnConveyorDone();

	/**
	 * GUI KitRobot sends this after the Kit to Inspection Area animation has
	 * been completed.
	 */
	public abstract void msgPlaceKitInInspectionAreaDone();

	/**
	 * GUI KitRobot sends this after Kit to Stand animation has been completed.
	 */
	public abstract void msgPlaceKitOnStandDone();

	public abstract boolean pickAndExecuteAnAction();

}
