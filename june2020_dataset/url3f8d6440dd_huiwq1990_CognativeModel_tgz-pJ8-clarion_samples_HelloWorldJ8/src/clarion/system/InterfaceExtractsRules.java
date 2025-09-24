package clarion.system;

/**
 * This interface is implemented by classes that do bottom-up rule extraction within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be considered capable of performing rule extraction by the CLARION Library.
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstractRuntimeTrainableBPNet</li>
 * <li>AbstractRuntimeTrainableImplicitModule</li>
 * <li>QBPNet</li>
 * <li>SimplifiedQBPNet</li>
 * <li>TableLookup</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public interface InterfaceExtractsRules {
	
	/**
	 * Checks to see if the extraction of a rule should occur.
	 * @return True if extraction should occur, otherwise false.
	 */
	boolean checkExtraction();
}
