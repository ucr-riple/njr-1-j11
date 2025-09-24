package clarion.system;

/**
 * This interface is implemented by classes that track match statistics within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be able to update its match statistics at each 
 * time step based on feedback it received (if provided).
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstractFixedRule</li>
 * <li>AbstractIRLRule</li>
 * <li>AbstractRule</li>
 * <li>AbstractRuntimeTrainableBPNet</li>
 * <li>AbstractRuntimeTrainableImplicitModule</li>
 * <li>ImplicitModuleCollection</li>
 * <li>QBPNet</li>
 * <li>RefineableRule</li>
 * <li>RuleCollection</li>
 * <li>SimplifiedQBPNet</li>
 * <li>TableLookup</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceTracksMatchStatistics {
	
	/**
	 * Updates the match statistics using the specified match calculator.
	 * @param MatchCalculator The match calculator to use for updating the match statistics.
	 */
	void updateMatchStatistics (AbstractMatchCalculator MatchCalculator);
	
	/**
	 * Gets the positive match (PM) statistic.
	 * @return the PM statistic.
	 */
	double getPM ();
	
	/**
	 * Gets the negative match (NM) statistic.
	 * @return the NM statistic.
	 */
	double getNM ();
	
	/**
	 * Sets the positive match (PM) statistic.
	 * @param pm The value to set for the PM statistic.
	 */
	void setPM (double pm);
	
	/**
	 * Sets the negative match (NM) statistic.
	 * @param nm The value to set for the NM statistic.
	 */
	void setNM (double nm);
	
	/**
	 * Increments the positive match (PM) statistic.
	 */
	void incrementPM();
	
	/**
	 * Increments the negative match (NM) statistic.
	 */
	void incrementNM();
	
	/**
	 * Resets the match statistics.
	 */
	void resetMatchStatistics ();
}
