package clarion.system;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

/**
 * This class implements a stochastic selector within CLARION. This class is final and, therefore, cannot cannot
 * be extended.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is used to by the various subsystems of the CLARION Library to select an object from a collection
 * of objects that implement the InterfaceStochasticallySelectable interface. It uses a soft-max Boltzmann 
 * distribution to choose the object based on the "final selection measure" of that stochastically selectable object.
 * <p>
 * <b>Classes that currently instantiate a stochastic selector are:</b><br>
 * <ul>
 * <li>ACS</li>
 * <li>GoalSelectionModule</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4
 * @author Nick Wilson
 */
public final class StochasticSelector
{
	/**The threshold for Boltzmann selection.*/
    public static double GLOBAL_THRESHOLD = 0;
    /**The threshold for Boltzmann selection.*/
    public double THRESHOLD = GLOBAL_THRESHOLD;
    /**The temperature for Boltzmann selection.*/
    public static double GLOBAL_TEMPERATURE = .1;
    /**The temperature for Boltzmann selection.*/
    public double TEMPERATURE = GLOBAL_TEMPERATURE;
    
    /** 
     * Given a collection of stochastically selectable objects, this method chooses an object from that collection
     * using a Boltzmann distribution.
     * @param s The array of stochastically selectable objects from which to choose.
     * @return The chosen object.
     */
    public InterfaceStochasticallySelectable select( Collection <? extends InterfaceStochasticallySelectable> s)
    {
    	double NormalizationFactor = 0;
    	double BoltzmannSum = 0;
    	ArrayList <InterfaceStochasticallySelectable> inputArr = new ArrayList <InterfaceStochasticallySelectable> (s);
    	
    	for (InterfaceStochasticallySelectable c : inputArr)
        {
        	NormalizationFactor += c.getFinalSelectionMeasure();
        }
    	
    	Collections.sort(inputArr);
    	Collections.reverse(inputArr);
        // fill in the input array of Boltzmann distribution.
        for (InterfaceStochasticallySelectable c : inputArr)
        {
        	BoltzmannSum += Math.exp( (c.getFinalSelectionMeasure()/NormalizationFactor) / TEMPERATURE );
        }
        
        // make a decision according to Boltzmann distribution.
        int decision = 0;
        double lottery = Math.random() * BoltzmannSum;
        double currentSum = Math.exp((inputArr.get(0).getFinalSelectionMeasure()/NormalizationFactor) / TEMPERATURE);
    	while( ( lottery - currentSum ) > THRESHOLD  && decision < inputArr.size())
    	{
    		++decision;
            currentSum += Math.exp((inputArr.get(decision).getFinalSelectionMeasure()/NormalizationFactor) / TEMPERATURE);
        }

        return inputArr.get(decision);
    }
    
    /**
     * This method calculates a Boltzmann distribution for a collection of stochastically selectable objects
     * but does not go so far as to perform the actual selection. The method is used mainly for things like 
     * level combination as the match statistics are partially updated based on the Boltzmann probability (each
     * items in the collection's measure after it has been update using the Boltzmann equation.
     * @param s The collection of stochastically selectable objects.
     * @return A collection of stochastically selectable objects with adjusted Boltzmann probabilities.
     */
    public Collection <? extends InterfaceStochasticallySelectable> performBoltzmannDistribution (Collection <? extends InterfaceStochasticallySelectable> s)
    {
    	double BoltzmannSum = 0;
    	
    	for (InterfaceStochasticallySelectable c : s)
        {
    		c.setFinalSelectionMeasure(Math.exp( (c.getFinalSelectionMeasure()) / TEMPERATURE ));
        	BoltzmannSum += c.getFinalSelectionMeasure();
        }
    	
    	for (InterfaceStochasticallySelectable c : s)
    	{
    		c.setFinalSelectionMeasure(c.getFinalSelectionMeasure() / BoltzmannSum);
    	}
    	
    	return s;
    }
    
    /**
     * Gets the BoltzmannProbability of a specified stochastically selectable object given a collection of 
     * stochastically selectable objects. The specified object must be in the specified collection or this
     * method will throw an exception.
     * @param s The collection of stochastically selectable objects.
     * @param ss The object (within the collection) whose Boltzmann probability your wish to get.
     * @return The Boltzmann probability of the specified stochastically selectable object.
     * @throws IllegalArgumentException If the specified stochastic object is not contained within the
     * specified collection.
     */
    public double getBoltzmannProbability (Collection <? extends InterfaceStochasticallySelectable> s, InterfaceStochasticallySelectable ss) throws IllegalArgumentException
    {
    	if(!s.contains(ss))
    		throw new IllegalArgumentException ("The specified stochastically selectable object whose Boltzmann probability " +
    				"you wish to find MUST be contained within the specified collection.");
    	
    	double BoltzmannSum = 0;
    	
    	for (InterfaceStochasticallySelectable c : s)
        	BoltzmannSum += Math.exp( (c.getFinalSelectionMeasure()) / TEMPERATURE );
    	
    	return Math.exp( (ss.getFinalSelectionMeasure()) / TEMPERATURE ) / BoltzmannSum;
    }
}