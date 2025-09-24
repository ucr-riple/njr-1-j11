package clarion.system;

/**
 * This interface is implemented by classes that can be trained at runtime within CLARION. 
 * It extends (combines) the InterfaceTrainable, InterfaceHandlesFeedback, and
 * InterfaceExtractsRules interfaces.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be considered trainable at runtime by the CLARION Library.
 * <p>
 * <b>Known Subinterfaces:</b><br>
 * <ul>
 * <li>InterfaceUsesQLearning</li>
 * </ul>
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
public interface InterfaceRuntimeTrainable extends InterfaceTrainable, InterfaceHandlesFeedback, InterfaceExtractsRules {
	
}