package clarion.system;

import java.util.Collection;

/**
 * This interface is implemented by classes that are trainable within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be capable of being trained "off-line" at a minimum.
 * If you wish to build a class that can be trained at runtime, implement the InterfaceRuntimeTrainable
 * interface instead.
 * <p>
 * <b>Known Subinterfaces:</b><br>
 * <ul>
 * <li>InterfaceRuntimeTrainable</li>
 * </ul>
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstactTrainableImplicitModule</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceTrainable {
	
	/**
	 * Sets the desired output given the collection specified.
	 * @param DesiredOut The desired output to be set.
	 */
    public void setDesiredOutput (Collection <? extends AbstractOutputChunk> DesiredOut);
    
    /**
     * Sets a specific desired output node given the specified chunk.
     * @param DesiredOut The desired output chunk to be set.
     */
    void setDesiredOutput (AbstractOutputChunk DesiredOut);
    
    /**
     * Performs a backward pass, "learning" based on the desired output.
     */
	abstract void backwardPass();
	
	/**
     * Gets the sum of squared errors.
     * @return The sum of squared errors.
     */
    double getSumSqErrors();
}
