package clarion.system;

/**
 * This class implements a rule extractor within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is used to handle the extraction of rules from the bottom level implicit modules
 * in ACS (mainly).
 * <p>
 * <b>Classes that currently instantiate a rule extractor are:</b><br>
 * <ul>
 * <li>ACS</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public final class RuleExtractor {
	/**The probability of extracting a rule*/
	public static double GLOBAL_EXTRACTION_PROBABILITY = 1;
	/**The probability of extracting a rule*/
	public double EXTRACTION_PROBABILITY = GLOBAL_EXTRACTION_PROBABILITY;
    
    /**
     * Checks to see if the criteria for rule extraction has been satisfied given a specified
     * implicit module. This method uses the implicit module's checkExtraction function
     * in order to determine if extraction should be performed.
     * @param im The implicit modules to check.
     * @return True if the extraction criteria has been satisfied, otherwise false.
     */
	public boolean checkExtractionCriterion(AbstractImplicitModule im)
	{
		if(im instanceof InterfaceExtractsRules && ((InterfaceExtractsRules)im).checkExtraction() && Math.random() < EXTRACTION_PROBABILITY)
			return true;
		else
			return false;
	}
	
	/**
	 * Extracts a rule from the specified implicit module. If a dimension within the input layer of the implicit
	 * module contains no activated values, then that dimension will be set to accept any value for the extracted rule. 
	 * All of the values in that dimension will be fully activated (i.e. the dimension will be completely generalized).
	 * @param im The implicit module from which to extract a rule.
	 * @param Act The action for the rule to be created.
	 * @return The extracted rule.
	 */
	public static AbstractRule extractRule(AbstractImplicitModule im, AbstractAction Act)
	{
		GeneralizedConditionChunk c = new GeneralizedConditionChunk ();
		for(Dimension i : im.getInput())
		{
			Dimension d = i.clone();
			for(Value v : d.values())
			{
				if(v.isActivated())
					v.setActivation(v.FULL_ACTIVATION_THRESHOLD);
			}
			c.put(d.getID(),d);
			if(d.getNumFullyActivatedVals() == 0)
			{
				for(Value v : d.values())
					v.setActivation(v.FULL_ACTIVATION_THRESHOLD);
			}
		}
		AbstractRule r = new RefineableRule (c, Act);
		return r;
	}
}
