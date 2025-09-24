package clarion.system;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * This class implements an implicit module collection within CLARION. It extends the LinkedHashSet class
 * and implements the InterfaceTracksMatchStatistics and InterfaceHandlesFeedback interfaces.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container for holding implicit modules within some of the CLARION subsystems.
 * <p>
 * This collection keeps track of collection-wide match statistics that are used to for variable level 
 * selection within the ACS as well as for performance monitoring and reporting purposes.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class ImplicitModuleCollection extends LinkedHashSet <AbstractImplicitModule> implements InterfaceTracksMatchStatistics, InterfaceHandlesFeedback{
	private static final long serialVersionUID = -6837619313448902892L;
	
	/**The match discount factor.*/
	public static double GLOBAL_MATCH_DISCOUNT = .9;
	/**The match discount factor.*/
	public static double MATCH_DISCOUNT = GLOBAL_MATCH_DISCOUNT;
	
	/**The immediate feedback (if given).*/
	private double Feedback;
	/**The positive match counter.*/
	protected double PM = 0;
	/**The negative match counter.*/
	protected double NM = 0;
	/**The threshold that must be passed to meet the positive match criterion.*/
	public static double GLOBAL_POSITIVE_MATCH_THRESHOLD = .9;
    /**The threshold that must be passed to meet the positive match criterion.*/
    public double POSITIVE_MATCH_THRESHOLD = GLOBAL_POSITIVE_MATCH_THRESHOLD;
	
	/**
	 * Initializes an implicit module collection.
	 */
	public ImplicitModuleCollection ()
	{
		super();
	}
	
	/**
	 * Initializes an implicit module collection with the collection of 
	 * implicit modules specified.
	 * @param ims The implicit modules for the collection.
	 */
	public ImplicitModuleCollection (Collection <? extends AbstractImplicitModule> ims)
	{
		super();
		addAll(ims);
	}
	
	/**
	 * Adds an implicit module to the collection. If the specified implicit module is already in the collection, 
	 * this method will throw an exception.
	 * @param im The implicit module to add.
	 * @return True if the implicit module was added to the collection.
	 * @throws IllegalArgumentException If the specified implicit module is already in the collection.
	 */
	public boolean add (AbstractImplicitModule im) throws IllegalArgumentException
	{
		if(contains(im))
			throw new IllegalArgumentException ("The specified implicit module is already in " +
					"the collection.");
		return super.add(im);
	}
	
	/**
	 * Adds a collection of implicit modules to this collection.
	 * @param ims The implicit modules to add.
	 * @return True if the implicit modules were successfully added to the collection.
	 */
	public boolean addAll (Collection <? extends AbstractImplicitModule> ims)
	{
		boolean rettrue = false;
		for(AbstractImplicitModule i : ims)
		{
			if(add(i))
				rettrue = true;
		}
		return rettrue;
	}
	
	/**
	 * Discounts the positive and negative match statistics for all modules in the 
	 * collection.
	 */
	public void discountMatchStatistics()
	{
		for(AbstractImplicitModule i : this)
		{
			if(i instanceof InterfaceTracksMatchStatistics)
			{
				((InterfaceTracksMatchStatistics)i).setPM(((InterfaceTracksMatchStatistics)i).getPM() * MATCH_DISCOUNT);
				((InterfaceTracksMatchStatistics)i).setNM(((InterfaceTracksMatchStatistics)i).getNM() * MATCH_DISCOUNT);
			}
		}
		PM *= MATCH_DISCOUNT;
		NM *= MATCH_DISCOUNT;
	}
	
	/**
	 * Gets the positive match statistic.
	 * @return The positive match statistic.
	 */
	public double getPM ()
	{
		return PM;
	}
	
	/**
	 * Gets the negative match statistic.
	 * @return The negative match statistic.
	 */
	public double getNM ()
	{
		return NM;
	}
	
	/**
	 * Sets the positive match statistic. This method can be used to update the 
	 * positive match statistic if the user wishes to provide their own match criterion 
	 * function.
	 * @param pm The value to set as the positive match statistic.
	 */
	public void setPM (double pm)
	{
		PM = pm;
	}
	
	/**
	 * Sets the negative match statistic. This method can be used to update the 
	 * negative match statistic if the user wishes to provide their own match criterion 
	 * function.
	 * @param nm The value to set as the negative match statistic.
	 */
	public void setNM (double nm)
	{
		NM = nm;
	}
	
	/**
     * Gets the feedback. This method is only used if feedback is being provided.
     * @return The feedback.
     */
    public double getFeedback()
    {
    	return Feedback;
    }
    
    /**
     * Sets the feedback. This method should be called before the updateMatchStatistics method is called. 
     * This method is only used if feedback is being provided.
     * @param feedback The value of the feedback.
     */
    public void setFeedback (double feedback)
    {
    	Feedback = feedback;
    }
	
	/**
	 * Updates the positive or negative match statistics based on the feedback.
	 * <p>
	 * This update is usually performed after the feedback has been set.
	 * @param MatchCalculator The match calculator to use to determine positivity.
	 */
	public void updateMatchStatistics (AbstractMatchCalculator MatchCalculator)
	{
		if (MatchCalculator.isPositive(Feedback, POSITIVE_MATCH_THRESHOLD))
		{
			++PM;
		}
		else
			++NM;
	}
	
	/**
	 * Resets the match statistics.
	 */
	public void resetMatchStatistics ()
	{
		PM = 0;
		NM = 0;
	}
	
	/**
	 * Increments the positive match statistic. This method can be used to update the 
	 * positive match statistic if the user wishes to provide their own match criterion 
	 * function.
	 */
	public void incrementPM()
	{
		++PM;
	}
	
	/**
	 * Increments the negative match statistic. This method can be used to update the 
	 * negative match statistic if the user wishes to provide their own match criterion 
	 * function.
	 */
	public void incrementNM()
	{
		++NM;
	}
    
	/**
	 * This method does nothing as it is not used by the CLARION Library for this collection.
	 * @return False
	 */
	public boolean checkMatchCriterion() {
		return false;
	}
	
	/**
	 * Checks to see if the collection contains an implicit module that is equal to the 
	 * specified implicit module.
	 * @param im The implicit module object you wish to find within this collection.
	 * @return True if the collection contains an implicit module equal to the 
	 * implicit module specified, otherwise false.
	 */
	public boolean contains (Object im)
	{
		for (AbstractImplicitModule i : this)
		{
			if(i.equals(im))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the number of implicit modules in the collection.
	 */
	public int size ()
	{
		return super.size();
	}
}
