package clarion.system;

/**
 * This interface is implemented by classes that track time within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will track time. The time stamps that are tracked by 
 * the classes implementing this interface are most often used for calculating base level activation
 * (BLA), or for reporting purposes.
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstractAction</li>
 * <li>AbstractChunk</li>
 * <li>AbstractExplicitModule</li>
 * <li>AbstractFixedRule</li>
 * <li>AbstractIRLRule</li>
 * <li>AbstractOutputChunk</li>
 * <li>AbstractRule</li>
 * <li>RefineableRule</li>
 * <li>DimensionlessOutputChunk</li>
 * <li>DimensionValueCollection</li>
 * <li>DriveStrength</li>
 * <li>ExternalAction</li>
 * <li>Goal</li>
 * <li>GoalAction</li>
 * <li>WorkingMemoryAction</li>
 * <li>Value</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public interface InterfaceTracksTime {
	
	double getBLA(long TimeStamp);
	
	double getNormalizedBLA(long TimeStamp);
	
	void addTimeStamp (long stamp);
}
