package clarion.system;

/**
 * This interface is implemented by classes that are stochastically selectable within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface can be used for selection by the stochastic selector in CLARION.
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstractAction</li>
 * <li>AbstractOutputChunk</li>
 * <li>DimensionlessOutputChunk</li>
 * <li>DriveStrength</li>
 * <li>ExternalAction</li>
 * <li>GenericStochasticObject</li>
 * <li>Goal</li>
 * <li>GoalAction</li>
 * <li>WorkingMemoryAction</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceStochasticallySelectable extends Comparable<InterfaceStochasticallySelectable>{

	/**
	 * Gets the final selection measure used for stochastic selection.
	 * @return The final selection measure.
	 */
	double getFinalSelectionMeasure ();
	
	/**
	 * Sets the final selection measure.
	 * @param Measure The value to set for the final selection measure.
	 */
	void setFinalSelectionMeasure (double Measure);
	
	boolean equals (Object s);
}
