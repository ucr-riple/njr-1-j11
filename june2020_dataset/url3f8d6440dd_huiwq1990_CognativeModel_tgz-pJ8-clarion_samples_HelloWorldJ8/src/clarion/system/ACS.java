package clarion.system;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class implements the Action Centered Subsystem (ACS) within CLARION. It extends the AbstractSubsystem class.
 * <p>
 * <b>Usage:</b>
 * The ACS is the subsystem that is minimally required in order for a CLARION agent to run. It MUST be fully
 * setup within a CLARION agent before the CLARION agent can perform within a task environment.
 * <p>
 * Note that simply initializing the ACS is not sufficient enough to setup the ACS.
 * At the very least you MUST also add at least one implicit module to the bottom level of
 * the ACS along with all of the actions associated with the output layer of that module.
 * <p>
 * The PRIMARY methods for the ACS are the "learn" and "chooseAction" methods and they do precisely what
 * their names suggest.
 * <p>
 * Note that the ACS is initialized with already built in "default" external, working memory, and goal actions:<br>
 * <ul>
 * <li>"DO_NOTHING_EXTERNAL"</li>
 * <li>"DO_NOTHING_WM"</li> 
 * <li>"DO_NOTHING_GOAL"</li>
 * </ul>
 * It is completely optional if you wish to implement the "DO_NOTHING" actions, however if you do, then you must specify
 * those actions in the output layer of your implicit modules and/or rules in order for them to be used.
 * <p>
 * This class contains MANY parameters for handling different aspects of the ACS from level selection options/methods to
 * triggers for turning on and off various "automatic" aspects of the ACS (such as extraction and refinement). If you are
 * having trouble determining where to locate a parameter you need to set, this is not a bad place to start looking (if the
 * parameter relates to the ACS, of course).
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public final class ACS extends AbstractSubsystem{
	/**The options for level selection (such as fixed or variable)*/
	public enum LevelSelectionOptions {FIXED,VARIABLE};
	/**The various methods that can be used for level selection 
	 * (such as stochastic or combined)*/
	public enum LevelSelectionMethods {STOCHASTIC,COMBINED};
	/**The various different types of modules used for action selection (BL,RER,IRL,FR)*/
	private enum ModuleTypes {BL,RER,IRL,FR, ALL};
	
	/**The RER rule store.*/
	private RuleCollection RERRuleStore = new RuleCollection ();
	/**The IRL rule store.*/
	private RuleCollection IRLRuleStore = new RuleCollection ();
	/**The FR rule store.*/
	private RuleCollection FRRuleStore = new RuleCollection ();
	/**The bottom level implicit modules.*/
	private ImplicitModuleCollection BLImplicitModuleStore = new ImplicitModuleCollection ();
	/**The collection of possible actions*/
	private ActionCollection PossibleActions = new ActionCollection ();
	/**Contains the hidden match all rules for all actions associated with the rules in 
	 * the rule collections.*/
	private HashMap <Object, AbstractRule> MatchAllRules = new HashMap <Object, AbstractRule> ();
	
	/**Points to the working memory from the instance of the CLARION class to which this
	 * instance of the ACS is attached.*/
	protected WorkingMemory WM;
	
	/**Points to the episodic memory from the instance of the CLARION class to which this
	 * instance of the ACS is attached.*/
	protected EpisodicMemory EM;
	
	/**The module type that was last used to choose the action.*/
	private ModuleTypes ModuleUsed;
	
	/**The current input (including current sensory information, current goal, chunks in 
	 * working memory, and drive stimulus).*/
	private DimensionValueCollection CurrentInput;
	/**The chosen action.*/
	private AbstractAction ChosenAction;
	/**The current time stamp.*/
	private Long CurrentTimeStamp;
	
	/**The stochastic selector for rule selection.*/
	public StochasticSelector SELECTOR = new StochasticSelector();
	/**The rule extractor for rule extraction.*/
	public RuleExtractor EXTRACTOR = new RuleExtractor();
	/**The rule refiner for rule refinement.*/
	public RuleRefiner REFINER = new RuleRefiner();
	
	/**The match calculator to use for updating match statistics for ALL components 
	 * located within the ACS.*/
	public AbstractMatchCalculator MatchCalculator = new DefaultMatchCalculator();
	
	/**Specifies the method to use for level selection.*/
	public static LevelSelectionMethods GLOBAL_LEVEL_SELECTION_METHOD = LevelSelectionMethods.STOCHASTIC;
	/**Specifies the option to use for level selection.*/
	public static LevelSelectionOptions GLOBAL_LEVEL_SELECTION_OPTION = LevelSelectionOptions.VARIABLE;
	/**Specifies the method to use for level selection.*/
	public LevelSelectionMethods LEVEL_SELECTION_METHOD = GLOBAL_LEVEL_SELECTION_METHOD;
	/**Specifies the option to use for level selection.*/
	public LevelSelectionOptions LEVEL_SELECTION_OPTION = GLOBAL_LEVEL_SELECTION_OPTION;
	
	/**The C3 constant used for variable level selection.*/
	public static double GLOBAL_VARIABLE_C3 = 1;
	/**The C3 constant used for variable level selection.*/
	public double VARIABLE_C3 = GLOBAL_VARIABLE_C3;
	/**The C4 constant used for variable level selection.*/
	public static double GLOBAL_VARIABLE_C4  = 2;
	/**The C4 constant used for variable level selection.*/
	public double VARIABLE_C4 = GLOBAL_VARIABLE_C4;
	
	/**The probability measure specified by the probability setting module in the MCS for the bottom level of the ACS
	 * when stochastic level selection is used.*/
	public double MCS_BL_PROBABILITY = .5;
	/**The weight measure specified by the probability setting module in the MCS for the bottom level of the ACS
	 * when weighted combination is used.*/
	public double MCS_BL_WEIGHT = .5;
	/**The probability measure specified by the probability setting module in the MCS for the top level of the ACS
	 * when stochastic level selection is used.*/
	public double MCS_TL_PROBABILITY = .5;
	/**The weight measure specified by the probability setting module in the MCS for the top level of the ACS
	 * when weighted combination is used.*/
	public double MCS_TL_WEIGHT = .5;
	
	/**The constant A used to factor in the MCS measure when calculating the probability or weight of for the bottom level
	 * during level selection.*/
	public static double GLOBAL_MCS_A = 0;
	/**The constant B used to factor in the MCS measure when calculating the probability or weight of for the bottom level
	 * during level selection.*/
	public static double GLOBAL_MCS_B = 2;
	/**The constant A used to factor in the MCS measure when calculating the probability or weight of for the bottom level
	 * during level selection.*/
	public double MCS_A = GLOBAL_MCS_A;
	/**The constant B used to factor in the MCS measure when calculating the probability or weight of for the bottom level
	 * during level selection.*/
	public double MCS_B = GLOBAL_MCS_B;
	
	/**The fixed probability of using the bottom level. This is used if the fixed level selection
	 * method is used.*/
	public static double GLOBAL_FIXED_BL_PROBABILITY = .7;
	/**The fixed bottom level weight when level combination is used.*/
	public static double GLOBAL_FIXED_BL_COMB_WEIGHT = .7;
	/**The fixed probability of using the bottom level. This is used if the fixed level selection
	 * method is used.*/
	public double FIXED_BL_PROBABILITY = GLOBAL_FIXED_BL_PROBABILITY;
	/**The fixed bottom level weight when level combination is used.*/
	public double FIXED_BL_COMB_WEIGHT = GLOBAL_FIXED_BL_COMB_WEIGHT;
	
	/**The BL beta when using variable stochastic level selection.*/
	public static double GLOBAL_VARIABLE_BL_BETA = .7;
	/**The BL beta when using the variable weighted combination.*/
	public static double GLOBAL_VARIABLE_BL_COMB_WEIGHT_BETA = .7;
	/**The BL beta when using variable stochastic level selection.*/
	public double VARIABLE_BL_BETA = GLOBAL_VARIABLE_BL_BETA;
	/**The BL beta when using the variable weighted combination.*/
	public double VARIABLE_BL_COMB_WEIGHT_BETA = GLOBAL_VARIABLE_BL_COMB_WEIGHT_BETA;
	
	/**The fixed probability of using an RER rule. This is used if the fixed level selection
	 * method is used.*/
	public static double GLOBAL_FIXED_RER_PROBABILITY = .15;
	/**The fixed RER rule weight when level combination is used.*/
	public static double GLOBAL_FIXED_RER_COMB_WEIGHT = .15;
	/**The fixed probability of using an RER rule. This is used if the fixed level selection
	 * method is used.*/
	public double FIXED_RER_PROBABILITY = GLOBAL_FIXED_RER_PROBABILITY;
	/**The fixed RER rule weight when level combination is used.*/
	public double FIXED_RER_COMB_WEIGHT = GLOBAL_FIXED_RER_COMB_WEIGHT;
	
	/**The RER beta when using variable stochastic level selection.*/
	public static double GLOBAL_VARIABLE_RER_BETA = .15;
	/**The RER beta when using the variable weighted combination.*/
	public static double GLOBAL_VARIABLE_RER_COMB_WEIGHT_BETA = .15;
	/**The RER beta when using variable stochastic level selection.*/
	public double VARIABLE_RER_BETA = GLOBAL_VARIABLE_RER_BETA;
	/**The RER beta when using the variable weighted combination.*/
	public double VARIABLE_RER_COMB_WEIGHT_BETA = GLOBAL_VARIABLE_RER_COMB_WEIGHT_BETA;
	
	/**The fixed probability of using an IRL rule. This is used if the fixed level selection
	 * method is used.*/
	public static double GLOBAL_FIXED_IRL_PROBABILITY = .15;
	/**The fixed IRL rule weight when level combination is used.*/
	public static double GLOBAL_FIXED_IRL_COMB_WEIGHT = .15;
	/**The fixed probability of using an IRL rule. This is used if the fixed level selection
	 * method is used.*/
	public double FIXED_IRL_PROBABILITY = GLOBAL_FIXED_IRL_PROBABILITY;
	/**The fixed IRL rule weight when level combination is used.*/
	public double FIXED_IRL_COMB_WEIGHT = GLOBAL_FIXED_IRL_COMB_WEIGHT;
	
	/**The IRL beta when using variable stochastic level selection.*/
	public static double GLOBAL_VARIABLE_IRL_BETA = .15;
	/**The IRL beta when using the variable weighted combination.*/
	public static double GLOBAL_VARIABLE_IRL_COMB_WEIGHT_BETA = .15;
	/**The IRL beta when using variable stochastic level selection.*/
	public double VARIABLE_IRL_BETA = GLOBAL_VARIABLE_IRL_BETA;
	/**The IRL beta when using the variable weighted combination.*/
	public double VARIABLE_IRL_COMB_WEIGHT_BETA = GLOBAL_VARIABLE_IRL_COMB_WEIGHT_BETA;
	
	/**The fixed probability of using a fixed rule. This is used if the fixed level selection
	 * method is used.*/
	public static double GLOBAL_FIXED_FR_PROBABILITY = 0;
	/**The fixed weight for a fixed rule when level combination is used.*/
	public static double GLOBAL_FIXED_FR_COMB_WEIGHT = 0;
	/**The fixed probability of using a fixed rule. This is used if the fixed level selection
	 * method is used.*/
	public double FIXED_FR_PROBABILITY = GLOBAL_FIXED_FR_PROBABILITY;
	/**The fixed weight for a fixed rule when level combination is used.*/
	public double FIXED_FR_COMB_WEIGHT = GLOBAL_FIXED_FR_COMB_WEIGHT;
	
	/**The FR beta when using variable stochastic level selection.*/
	public static double GLOBAL_VARIABLE_FR_BETA = 0;
	/**The FR beta when using the variable weighted combination.*/
	public static double GLOBAL_VARIABLE_FR_COMB_WEIGHT_BETA = 0;
	/**The FR beta when using variable stochastic level selection.*/
	public double VARIABLE_FR_BETA = GLOBAL_VARIABLE_FR_BETA;
	/**The FR beta when using the variable weighted combination.*/
	public double VARIABLE_FR_COMB_WEIGHT_BETA = GLOBAL_VARIABLE_FR_COMB_WEIGHT_BETA;
	
	
	/**The probability of choosing an external action.*/
	public static double GLOBAL_EXTERNAL_PROBABILITY = 1;
	/**The probability of choosing a goal action.*/
	public static double GLOBAL_GOAL_PROBABILITY = 0;
	/**The probability of choosing a working memory action.*/
	public static double GLOBAL_WM_PROBABILITY = 0;
	/**The probability of choosing an external action.*/
	public double EXTERNAL_PROBABILITY = GLOBAL_EXTERNAL_PROBABILITY;
	/**The probability of choosing a goal action.*/
	public double GOAL_PROBABILITY = GLOBAL_GOAL_PROBABILITY;
	/**The probability of choosing a working memory action.*/
	public double WM_PROBABILITY = GLOBAL_WM_PROBABILITY;
	
	/**Specifies whether or not extraction should be used. Extraction is 
	 * performed by default.*/
	public static boolean GLOBAL_PERFORM_EXTRACTION = true;
	/**Specifies whether or not extraction should be used. Extraction is 
	 * performed by default.*/
	public boolean PERFORM_EXTRACTION = GLOBAL_PERFORM_EXTRACTION;
	/**Specifies whether or not bottom level learning should be performed. Learning is 
	 * performed by default.*/
	public static boolean GLOBAL_PERFORM_BL_LEARNING = true;
	/**Specifies whether or not bottom level learning should be performed. Learning is 
	 * performed by default.*/
	public boolean PERFORM_BL_LEARNING = GLOBAL_PERFORM_BL_LEARNING;
	/**Specifies whether or not refinement should be used for RER rules. Refinement is 
	 * performed by default.*/
	public static boolean GLOBAL_PERFORM_REFINEMENT_RER = true;
	/**Specifies whether or not refinement should be used for RER rules. Refinement is 
	 * performed by default.*/
	public boolean PERFORM_REFINEMENT_RER = GLOBAL_PERFORM_REFINEMENT_RER;
	/**Specifies whether or not refinement should be used for IRL rules. Refinement is 
	 * performed by default.*/
	public static boolean GLOBAL_PERFORM_REFINEMENT_IRL = true;
	/**Specifies whether or not refinement should be used for IRL rules. Refinement is 
	 * performed by default.*/
	public boolean PERFORM_REFINEMENT_IRL = GLOBAL_PERFORM_REFINEMENT_IRL;
	
	/**The match discount factor.*/
	public static double GLOBAL_MATCH_DISCOUNT = .9;
	/**The match discount factor.*/
	public static double MATCH_DISCOUNT = GLOBAL_MATCH_DISCOUNT;
	
	/**The subset of possible actions that were involved in the last action decision making stochastic selection.*/
	private Collection <AbstractAction> shortlist;
	
	/**
	 * Minimally initializes the ACS. This constructor builds a "frame" for this instance of
	 * the ACS from which all desired components can be attached. During initialization this
	 * instance of the ACS will attach itself to the CLARION agent you specify.
	 * <p>
	 * Note that simply calling this constructor is not sufficient enough to setup the ACS.
	 * At the very least you MUST also add at least one neural net to the bottom level of
	 * the ACS along with all of the actions associated with the output layer of that net.
	 * @param Agent The agent to which the ACS is being attached.
	 */
	public ACS (CLARION Agent) 
	{
		super(Agent);
		addAction(new ExternalAction("DO_NOTHING_EXTERNAL"));
		addAction(new WorkingMemoryAction("DO_NOTHING_WM"));
		addAction(new GoalAction("DO_NOTHING_GOAL"));
	}
	
	/**
	 * Chooses an action given the current input (which includes sensory information, the 
	 * current goal, and any chunks in working memory) and the previously chosen action.
	 * @param CurrInput The current input to the ACS.
	 * @param TimeStamp The current time stamp.
	 * @return The chosen action.
	 */
	protected AbstractAction chooseAction (DimensionValueCollection CurrInput, long TimeStamp)
	{
		if(EM != null)
			ChosenAction = EM.getPerformedAction(CurrentTimeStamp);
		CurrentTimeStamp = TimeStamp;
		CurrentInput = CurrInput;
		//Reset the activations and selection measures of the possible actions
		for(AbstractAction r : PossibleActions.values())
		{
			r.setActivation(r.MINIMUM_ACTIVATION_THRESHOLD);
			r.setBLSelectionMeasure(r.MINIMUM_ACTIVATION_THRESHOLD);
			r.setTLSelectionMeasure(r.MINIMUM_ACTIVATION_THRESHOLD);
		}
		
		LinkedList <AbstractRule> usageCandidates = new LinkedList<AbstractRule> ();
		
		//Compute the BL selection measures
		for(AbstractImplicitModule i : BLImplicitModuleStore)
		{
			if(i.checkEligibility())
			{
				i.setInput(CurrentInput);
				i.forwardPass();
				for(AbstractOutputChunk o : i.Output.values())
				{
					PossibleActions.get(o.getID()).setBLSelectionMeasure(o.getActivation());
				}
			}
		}
		//Compute the TL selection measures and/or combine the top and bottom level measures.
		if(LEVEL_SELECTION_METHOD == LevelSelectionMethods.COMBINED)
		{
			performCombination(usageCandidates);
			ModuleUsed = ModuleTypes.ALL;
		}
		else
		{
			RuleCollection chosenType = selectType ();
			if(chosenType != null)
			{
				boolean foundMatch = false;
				for(AbstractAction a : PossibleActions.values())
				{
					Collection <AbstractRule> rc = chosenType.getRules(a);
					for(AbstractRule r : rc)
					{
						r.setCurrentInput(CurrentInput);
						if(r.checkEligibility(TimeStamp))
						{
							foundMatch = true;
							usageCandidates.add(r);
							if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
							{
								double rs = r.getSupport(CurrentInput);
								if(rs > a.getTLSelectionMeasure())
									a.setTLSelectionMeasure(rs);
							}
							else
							{
								double ru = r.getUtility();
								if(ru > a.getTLSelectionMeasure())
									a.setTLSelectionMeasure(ru);
							}
						}
					}
					
					a.setFinalSelectionMeasure(a.getTLSelectionMeasure());
				}
				//If no rule was found in the top level that matches the current input, 
				//default back to the bottom level
				if(!foundMatch)
				{
					ModuleUsed = ModuleTypes.BL;
					for(AbstractAction a : PossibleActions.values())
						a.setFinalSelectionMeasure(a.getBLSelectionMeasure());
				}
			}
			else
			{
				ModuleUsed = ModuleTypes.BL;
			}
		}
		//Adjust the selection measure to account for persistence
		for(AbstractAction a : PossibleActions.values())
		{
			if(a.equals(ChosenAction))
				a.setFinalSelectionMeasure(a.adjustSelectionMeasure(a.getFinalSelectionMeasure(), a.FULL_ACTIVATION_LEVEL));
			else 
				a.setFinalSelectionMeasure(a.adjustSelectionMeasure(a.getFinalSelectionMeasure(), a.MINIMUM_ACTIVATION_THRESHOLD));
		}
		//Select an action type from which to choose.
		shortlist = null;
		double p = Math.random();
		double pp = 1;
		pp -= EXTERNAL_PROBABILITY;
		if(p >= pp)
		{
			shortlist = PossibleActions.getExternalActions();
		}
		else
		{
			pp -= WM_PROBABILITY;
			if(p >= pp)
			{
				shortlist = PossibleActions.getWMActions();
			}
			else
			{
				pp -= GOAL_PROBABILITY;
				if(p >= pp)
				{
					shortlist = PossibleActions.getGoalActions();
				}
			}
		}
		//Stochastically decide an action.
		if(shortlist == null)
		{
			//This statement should NEVER be true, but if it does, then the ACS will return the default external 
			//"DO_NOTHING" action.
			ChosenAction = PossibleActions.get("DO_NOTHING_EXTERNAL");
			shortlist = new LinkedList <AbstractAction> ();
			shortlist.add(ChosenAction);
		}
		else
		{
			ChosenAction = (AbstractAction)SELECTOR.select(shortlist);
		}
		
		if(ModuleUsed != ModuleTypes.BL)
		{
			for(AbstractRule r : usageCandidates)
			{
				if(ChosenAction.equals(r.getAction()))
				{
					r.addTimeStamp(CurrentTimeStamp);
				}
			}
		}
		
		for(AbstractImplicitModule i : BLImplicitModuleStore)
		{
			AbstractOutputChunk o = i.getOutput(ChosenAction.getID());
			if(o != null)
				i.setChosenOutput(o);
		}
		return ChosenAction;
	}
	
	/**
	 * Selects a rule type to use on the top level when performing action decision making with stochastic
	 * level selection. If the top level is not to be used, this method returns null.
	 * @return The rule collection to use on the top level or null if the top level is not to be used.
	 */
	private RuleCollection selectType ()
	{
		double pBL;
		double pRER;
		double pIRL;
		double pFR;
		if(LEVEL_SELECTION_OPTION == LevelSelectionOptions.FIXED)
		{
			pBL = FIXED_BL_PROBABILITY * (MCS_A + (MCS_B * MCS_BL_PROBABILITY));
			pRER = FIXED_RER_PROBABILITY;
			pIRL = FIXED_IRL_PROBABILITY;
			pFR = FIXED_FR_PROBABILITY;
		}
		else
		{
			double srBL = VARIABLE_BL_BETA * ((VARIABLE_C3 + BLImplicitModuleStore.PM)/(VARIABLE_C4 + BLImplicitModuleStore.PM + BLImplicitModuleStore.NM));
			double srRER = VARIABLE_RER_BETA * ((VARIABLE_C3 + RERRuleStore.PM)/(VARIABLE_C4 + RERRuleStore.PM + RERRuleStore.NM));
			double srIRL = VARIABLE_IRL_BETA * ((VARIABLE_C3 + IRLRuleStore.PM)/(VARIABLE_C4 + IRLRuleStore.PM + IRLRuleStore.NM));
			double srFR = VARIABLE_FR_BETA * ((VARIABLE_C3 + FRRuleStore.PM)/(VARIABLE_C4 + FRRuleStore.PM + FRRuleStore.NM));
			pBL = srBL/(srBL + srRER + srIRL + srFR);
			pBL = pBL * (MCS_A + (MCS_B * MCS_BL_PROBABILITY));
			pRER = srRER/(srBL + srRER + srIRL + srFR);
			pIRL = srIRL/(srBL + srRER + srIRL + srFR);
			pFR = srFR/(srBL + srRER + srIRL + srFR);
		}
		double normalizer = pBL + pRER + pIRL + pFR;
		pBL = pBL/normalizer;
		pRER = pRER/normalizer;
		pIRL = pIRL/normalizer;
		pFR = pFR/normalizer;
		double p = Math.random();
		double pp = 1;
		pp -= pRER;
		if(p >= pp)
		{
			ModuleUsed = ModuleTypes.RER;
			return RERRuleStore;
		}
		pp -= pIRL;
		if(p >= pp)
		{
			ModuleUsed = ModuleTypes.IRL;
			return IRLRuleStore;
		}
		pp -= pFR;
		if(p >= pp)
		{
			ModuleUsed = ModuleTypes.FR;
			return FRRuleStore;
		}
		return null;
	}
	
	/**
	 * Combines the outcomes of the top and bottom levels when level combination is being used.
	 */
	private void performCombination (LinkedList <AbstractRule> usageCandidates)
	{
		double wBL;
		double wRER;
		double wIRL;
		double wFR;
		if(LEVEL_SELECTION_OPTION == LevelSelectionOptions.FIXED)
		{
			wBL = FIXED_BL_COMB_WEIGHT * (MCS_A + (MCS_B * MCS_BL_WEIGHT));
			wRER = FIXED_RER_COMB_WEIGHT;
			wIRL = FIXED_IRL_COMB_WEIGHT;
			wFR = FIXED_FR_COMB_WEIGHT;
		}
		else
		{
			double srBL = VARIABLE_BL_COMB_WEIGHT_BETA * ((VARIABLE_C3 + BLImplicitModuleStore.PM)/(VARIABLE_C4 + BLImplicitModuleStore.PM + BLImplicitModuleStore.NM));
			double srRER = VARIABLE_RER_COMB_WEIGHT_BETA * ((VARIABLE_C3 + RERRuleStore.PM)/(VARIABLE_C4 + RERRuleStore.PM + RERRuleStore.NM));
			double srIRL = VARIABLE_IRL_COMB_WEIGHT_BETA * ((VARIABLE_C3 + IRLRuleStore.PM)/(VARIABLE_C4 + IRLRuleStore.PM + IRLRuleStore.NM));
			double srFR = VARIABLE_FR_COMB_WEIGHT_BETA * ((VARIABLE_C3 + FRRuleStore.PM)/(VARIABLE_C4 + FRRuleStore.PM + FRRuleStore.NM));
			wBL = srBL/(srBL + srRER + srIRL + srFR);
			wBL = wBL * (MCS_A + (MCS_B * MCS_BL_WEIGHT));
			wRER = srRER/(srBL + srRER + srIRL + srFR);
			wIRL = srIRL/(srBL + srRER + srIRL + srFR);
			wFR = srFR/(srBL + srRER + srIRL + srFR);
		}
		
		for(AbstractAction a : PossibleActions.values())
		{
			//RER Rules
			double vRER = 0;
			Collection <AbstractRule> rc = RERRuleStore.getRules(a);
			if(rc != null)
			{
				for(AbstractRule r : rc)
				{
					r.setCurrentInput(CurrentInput);
					if(r.checkEligibility(CurrentTimeStamp))
					{
						usageCandidates.add(r);
						if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
						{
							double rs = r.getSupport(CurrentInput);
							if(rs > vRER)
								vRER = rs;
						}
						else
						{
							double ru = r.getUtility();
							if(ru > vRER)
								vRER = ru;
						}
					}
				}
			}
			//IRL Rules
			double vIRL = 0;
			rc = IRLRuleStore.getRules(a);
			if(rc != null)
			{
				for(AbstractRule r : rc)
				{
					r.setCurrentInput(CurrentInput);
					if(r.checkEligibility(CurrentTimeStamp))
					{
						usageCandidates.add(r);
						if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
						{
							double rs = r.getSupport(CurrentInput);
							if(rs > vIRL)
								vIRL = rs;
						}
						else
						{
							double ru = r.getUtility();
							if(ru > vIRL)
								vIRL = ru;
						}
					}
				}
			}
			//Fixed Rules
			double vFR = 0;
			rc = FRRuleStore.getRules(a);
			if(rc != null)
			{
				for(AbstractRule r : rc)
				{
					r.setCurrentInput(CurrentInput);
					if(r.checkEligibility(CurrentTimeStamp))
					{
						usageCandidates.add(r);
						if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
						{
							double rs = r.getSupport(CurrentInput);
							if(rs > vFR)
								vFR = rs;
						}
						else
						{
							double ru = r.getUtility();
							if(ru > vFR)
								vFR = ru;
						}
					}
				}
			}
			//Combine
			a.setTLSelectionMeasure((wRER * vRER) + (wIRL * vIRL) + (wFR * vFR));
			a.setFinalSelectionMeasure(a.getTLSelectionMeasure() + (wBL * a.getBLSelectionMeasure()));
		}
	}
	
	/**
	 * Performs bottom level learning, rule refinement, and match statistic updates based on the 
	 * specified new input. If this method is used, learning is performed without any feedback.
	 * @param NewInput The new input that was perceived.
	 * @param TimeStamp The current time stamp.
	 */
	protected void learn (DimensionValueCollection NewInput, long TimeStamp)
	{
		if(EM != null)
			ChosenAction = EM.getPerformedAction(CurrentTimeStamp);
		updateInputSpace(NewInput.values());
		if(ChosenAction != null)
		{
			updateMatchAllStats(ChosenAction, ChosenAction.getFinalSelectionMeasure(), MatchCalculator);
			updateRules(ChosenAction.getFinalSelectionMeasure(), TimeStamp);
			updateBottomLevel(NewInput, ChosenAction.getFinalSelectionMeasure());
		}
	}
	
	/**
	 * Performs bottom level learning, rule refinement, and match statistic updates based on the 
	 * specified new input.
	 * @param NewInput The new input that was perceived.
	 * @param feedback The feedback received.
	 * @param TimeStamp The current time stamp.
	 */
	protected void learn (DimensionValueCollection NewInput, double feedback, long TimeStamp)
	{
		if(EM != null)
			ChosenAction = EM.getPerformedAction(CurrentTimeStamp);
		updateInputSpace(NewInput.values());
		if(ChosenAction != null)
		{
			updateMatchAllStats(ChosenAction, feedback, MatchCalculator);
			updateRules(feedback, TimeStamp);
			updateBottomLevel(NewInput, feedback);
		}
	}
	
	/**
	 * Updates the bottom level implicit representations (if they support online learning) given the 
	 * new sensory information and feedback (if any was received). This method also updates the
	 * match statistics for the bottom level components and the bottom level module itself.
	 * <p>
	 * If any external rules can be extracted from the bottom level, this method extracts them.
	 * @param NewInput The new input.
	 * @param feedback The feedback.
	 */
	private void updateBottomLevel (DimensionValueCollection NewInput, double feedback)
	{
		InterfaceStochasticallySelectable max = null;
		HashMap <AbstractImplicitModule, HashMap<AbstractOutputChunk,InterfaceStochasticallySelectable>> sl = 
			new HashMap<AbstractImplicitModule, HashMap<AbstractOutputChunk,InterfaceStochasticallySelectable>> ();
		for(AbstractImplicitModule i : BLImplicitModuleStore)
		{
			if(i.checkEligibility())
			{
				sl.put(i, new HashMap<AbstractOutputChunk, InterfaceStochasticallySelectable>());
				for(AbstractAction a : shortlist)
				{
					AbstractOutputChunk o = i.getOutput(a.getID());
					if(o != null)
					{
						sl.get(i).put(o, new GenericStochasticObject (o.getActivation()));
						if(o.getID().equals(ChosenAction.getID()) && (max == null || o.getActivation() > max.getFinalSelectionMeasure()))
							max = sl.get(i).get(o);
					}
				}
				if(i instanceof InterfaceHandlesFeedback && i.Output.containsKey(ChosenAction.getID()))
				{
					((InterfaceHandlesFeedback)i).setFeedback(feedback);
					if(i instanceof InterfaceHandlesNewInput)
						((InterfaceHandlesNewInput)i).setNewInput(NewInput.values());
					if(PERFORM_EXTRACTION && EXTRACTOR.checkExtractionCriterion(i))
					{
						AbstractRule R = RuleExtractor.extractRule(i,ChosenAction);
						if(!RERRuleStore.contains(R) && !RERRuleStore.Children.contains(R))
						{
							((RefineableRule)R).setMatchAll(getMatchAllRule(R.getAction()));
							RERRuleStore.add(R);
						}
						AbstractRule up = RERRuleStore.get(R.getCondition(), R.getAction());
						if(up == null)
							up = RERRuleStore.Children.get(R.getCondition(), R.getAction());
						up.addTimeStamp(CurrentTimeStamp);
					}
					if(i instanceof InterfaceTrainable && PERFORM_BL_LEARNING)
						((InterfaceTrainable)i).backwardPass();
					
					if(i instanceof InterfaceTracksMatchStatistics)
					{
						if(i instanceof InterfaceHasMatchCalculator)
							((InterfaceTracksMatchStatistics)i).updateMatchStatistics(((InterfaceHasMatchCalculator)i).getMatchCalculator());
						else
							((InterfaceTracksMatchStatistics)i).updateMatchStatistics(MatchCalculator);
					}
				}
			}
		}
		
		BLImplicitModuleStore.setFeedback(feedback);
		if(LEVEL_SELECTION_METHOD == ACS.LevelSelectionMethods.STOCHASTIC && ModuleUsed == ModuleTypes.BL)
		{
			BLImplicitModuleStore.updateMatchStatistics(MatchCalculator);
		}
		else
		{
			if(LEVEL_SELECTION_METHOD == ACS.LevelSelectionMethods.COMBINED)
			{
				LinkedList <InterfaceStochasticallySelectable> slc = new LinkedList <InterfaceStochasticallySelectable> ();
				for(HashMap <AbstractOutputChunk, InterfaceStochasticallySelectable> h : sl.values())
					slc.addAll(h.values());
				if(MatchCalculator.isPositive(feedback, BLImplicitModuleStore.POSITIVE_MATCH_THRESHOLD))
				{
					BLImplicitModuleStore.setPM(BLImplicitModuleStore.getPM() + SELECTOR.getBoltzmannProbability(slc, max));
				}
				else
					BLImplicitModuleStore.setNM(BLImplicitModuleStore.getNM() + SELECTOR.getBoltzmannProbability(slc, max));
			}
		}
	}
	
	/**
	 * Updates the match statistics for the rules in the rule store (if its type was used) and tries to refine those 
	 * rules in the rule store whose type (i.e. RER/IRL/FR) was used.
	 * @param feedback The feedback.
	 * @param TimeStamp The current time stamp.
	 */
	private void updateRules (double feedback, long TimeStamp)
	{
		RERRuleStore.Variations.updateMatchStatistics(CurrentInput, ChosenAction, feedback, MatchCalculator, TimeStamp);
		IRLRuleStore.Variations.updateMatchStatistics(CurrentInput, ChosenAction, feedback, MatchCalculator, TimeStamp);
		//REFINE RER Rules
		if(ModuleUsed == ModuleTypes.RER || ModuleUsed == ModuleTypes.ALL)
		{
			InterfaceStochasticallySelectable max = null;
			HashMap <AbstractRule, InterfaceStochasticallySelectable> sl = new HashMap<AbstractRule, InterfaceStochasticallySelectable> ();
			for(AbstractAction a : shortlist)
			{
				Collection <AbstractRule> slr = RERRuleStore.getRules(a);
				if(slr != null)
				{
					for(AbstractRule r : slr)
					{
						r.setCurrentInput(CurrentInput);
						if(r.checkEligibility())
						{
							if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
								sl.put(r,new GenericStochasticObject (r.getSupport(CurrentInput)));
							else
								sl.put(r,new GenericStochasticObject (r.getUtility()));
						}
					}
				}
			}
			Collection <AbstractRule> rc = RERRuleStore.getRules(ChosenAction);
			if(rc != null)
			{
				LinkedList <AbstractRule> newAdditions = new LinkedList <AbstractRule> ();
				LinkedList <AbstractRule> delRules = new LinkedList <AbstractRule> ();
				for(AbstractRule r : rc)
				{
					r.setCurrentInput(CurrentInput);
					if(r.checkEligibility())
					{
						if(sl.containsKey(r) && (max == null || sl.get(r).getFinalSelectionMeasure() > max.getFinalSelectionMeasure()))
							max = sl.get(r);
						r.setFeedback(feedback);
						if(r instanceof InterfaceHasMatchCalculator)
							r.updateMatchStatistics(((InterfaceHasMatchCalculator)r).getMatchCalculator());
						else
							r.updateMatchStatistics(MatchCalculator);
						if(r instanceof RefineableRule && PERFORM_REFINEMENT_RER)
						{	
							AbstractRule nr = REFINER.generalize(r);
							if(!nr.equals(r))
								newAdditions.add(nr.clone());
							else
							{
								nr = REFINER.specialize(r);
								if(nr != null && !nr.equals(r))
								{
									newAdditions.add(nr.clone());
									delRules.add(r);
								}
								else
								{
									if(nr == null)
										delRules.add(r);
								}
							}
						}	
					}
					if((r instanceof InterfaceDeleteable && ((InterfaceDeleteable)r).checkDeletion()) ||
							(r instanceof InterfaceDeleteableByDensity && ((InterfaceDeleteableByDensity)r).checkDeletionByDensity(TimeStamp)))
						delRules.add(r);
				}
				for(AbstractRule r : delRules)
				{
					RERRuleStore.remove(r);
				}
				
				for(AbstractRule r : newAdditions)
				{
					RERRuleStore.add(r);
				}
				
				for(AbstractRule r : delRules)
				{
					if(r.getChildren() != null)
					{
						for(AbstractRule cr : r.getChildren())
						{
							RERRuleStore.Children.remove(cr);
							RERRuleStore.add(cr);
						}
					}
				}
			}
			
			RERRuleStore.setFeedback(feedback);
			if(LEVEL_SELECTION_METHOD == ACS.LevelSelectionMethods.STOCHASTIC)
			{
				RERRuleStore.updateMatchStatistics(MatchCalculator);
			}
			else
			{
				if(max != null && MatchCalculator.isPositive(feedback, RERRuleStore.POSITIVE_MATCH_THRESHOLD))
				{
					RERRuleStore.setPM(RERRuleStore.getPM() + SELECTOR.getBoltzmannProbability(sl.values(), max));
				}
				else
					RERRuleStore.setNM(RERRuleStore.getNM() + SELECTOR.getBoltzmannProbability(sl.values(), max));
			}
		}
		//Refine IRL Rules
		if(ModuleUsed == ModuleTypes.IRL || ModuleUsed == ModuleTypes.ALL)
		{
			InterfaceStochasticallySelectable max = null;
			HashMap <AbstractRule, InterfaceStochasticallySelectable> sl = new HashMap<AbstractRule, InterfaceStochasticallySelectable> ();
			for(AbstractAction a : shortlist)
			{
				Collection <AbstractRule> slr = IRLRuleStore.getRules(a);
				if(slr != null)
				{
					for(AbstractRule r : slr)
					{
						r.setCurrentInput(CurrentInput);
						if(r.checkEligibility())
						{
							if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
								sl.put(r,new GenericStochasticObject (r.getSupport(CurrentInput)));
							else
								sl.put(r,new GenericStochasticObject (r.getUtility()));
						}
					}
				}
			}
			Collection <AbstractRule> rc = IRLRuleStore.getRules(ChosenAction);
			if(rc != null)
			{
				LinkedList <AbstractRule> newAdditions = new LinkedList <AbstractRule> ();
				LinkedList <AbstractRule> delRules = new LinkedList <AbstractRule> ();
				for(AbstractRule r : rc)
				{
					r.setCurrentInput(CurrentInput);
					if(r.checkEligibility())
					{
						if(sl.containsKey(r) && (max == null || sl.get(r).getFinalSelectionMeasure() > max.getFinalSelectionMeasure()))
							max = sl.get(r);
						r.setFeedback(feedback);
						if(r instanceof InterfaceHasMatchCalculator)
							r.updateMatchStatistics(((InterfaceHasMatchCalculator)r).getMatchCalculator());
						else
							r.updateMatchStatistics(MatchCalculator);
						if(r instanceof RefineableRule && PERFORM_REFINEMENT_IRL)
						{	
							AbstractRule nr = REFINER.generalize(r);
							if(!nr.equals(r))
								newAdditions.add(nr.clone());
							else
							{
								nr = REFINER.specialize(r);
								if(nr != null && !nr.equals(r))
								{
									newAdditions.add(nr.clone());
									delRules.add(r);
								}
								else
								{
									if(nr == null)
										delRules.add(r);
								}
							}
						}	
					}
					if((r instanceof InterfaceDeleteable && ((InterfaceDeleteable)r).checkDeletion()) ||
							(r instanceof InterfaceDeleteableByDensity && ((InterfaceDeleteableByDensity)r).checkDeletionByDensity(TimeStamp)))
						delRules.add(r);
				}
				for(AbstractRule r : delRules)
				{
					IRLRuleStore.remove(r);
				}
				
				for(AbstractRule r : newAdditions)
				{
					IRLRuleStore.add(r);
				}
				
				for(AbstractRule r : delRules)
				{
					if(r.getChildren() != null)
					{
						for(AbstractRule cr : r.getChildren())
						{
							IRLRuleStore.Children.remove(cr);
							IRLRuleStore.add(cr);
						}
					}
				}
			}
			
			IRLRuleStore.setFeedback(feedback);
			if(LEVEL_SELECTION_METHOD == ACS.LevelSelectionMethods.STOCHASTIC)
			{
				IRLRuleStore.updateMatchStatistics(MatchCalculator);
			}
			else
			{
				if(max != null && MatchCalculator.isPositive(feedback, IRLRuleStore.POSITIVE_MATCH_THRESHOLD))
				{
					IRLRuleStore.setPM(IRLRuleStore.getPM() + SELECTOR.getBoltzmannProbability(sl.values(), max));
				}
				else
					IRLRuleStore.setNM(IRLRuleStore.getNM() + SELECTOR.getBoltzmannProbability(sl.values(), max));
			}
		}
		//Update statistics for FR Rules
		if(ModuleUsed == ModuleTypes.FR || ModuleUsed == ModuleTypes.ALL)
		{
			InterfaceStochasticallySelectable max = null;
			HashMap <AbstractRule, InterfaceStochasticallySelectable> sl = new HashMap<AbstractRule, InterfaceStochasticallySelectable> ();
			for(AbstractAction a : shortlist)
			{
				Collection <AbstractRule> slr = FRRuleStore.getRules(a);
				if(slr != null)
				{
					for(AbstractRule r : slr)
					{
						r.setCurrentInput(CurrentInput);
						if(r.checkEligibility())
						{
							if(r.SELECTION_TYPE == AbstractRule.SelectionTypes.SUPPORT)
								sl.put(r,new GenericStochasticObject (r.getSupport(CurrentInput)));
							else
								sl.put(r,new GenericStochasticObject (r.getUtility()));
						}
					}
				}
			}
			Collection <AbstractRule> rc = FRRuleStore.getRules(ChosenAction);
			if(rc != null)
			{
				for(AbstractRule r : rc)
				{
					r.setCurrentInput(CurrentInput);
					if(r.checkEligibility())
					{
						if(sl.containsKey(r) && (max == null || sl.get(r).getFinalSelectionMeasure() > max.getFinalSelectionMeasure()))
							max = sl.get(r);
						r.setFeedback(feedback);
						if(r instanceof InterfaceHasMatchCalculator)
							r.updateMatchStatistics(((InterfaceHasMatchCalculator)r).getMatchCalculator());
						else
							r.updateMatchStatistics(MatchCalculator);
					}
				}
			}
			
			FRRuleStore.setFeedback(feedback);
			if(LEVEL_SELECTION_METHOD == ACS.LevelSelectionMethods.STOCHASTIC)
			{
				FRRuleStore.updateMatchStatistics(MatchCalculator);
			}
			else
			{
				if(max != null && MatchCalculator.isPositive(feedback, FRRuleStore.POSITIVE_MATCH_THRESHOLD))
				{
					FRRuleStore.setPM(FRRuleStore.getPM() + SELECTOR.getBoltzmannProbability(sl.values(), max));
				}
				else
					FRRuleStore.setNM(FRRuleStore.getNM() + SELECTOR.getBoltzmannProbability(sl.values(), max));
			}
		}
	}
	
	/**
	 * Get the last action chosen by the ACS. This method is protected and meant only for use
	 * inside the CLARION Library.
	 * @return The last action chosen.
	 */
	protected AbstractAction getChosenAction ()
	{
		return ChosenAction;
	}
	
	/**
	 * Resets the chosen action. This method is protected and meant only for use
	 * inside the CLARION Library.
	 */
	protected void resetChosenAction() {
		ChosenAction = null;
	}
	
	/**
	 * Gets all of the bottom level implicit modules. The collection returned is unmodifiable 
	 * and is meant for reporting the internal state only.
	 * @return An unmodifiable collection of all of the bottom level implicit modules.
	 */
	public Collection <AbstractImplicitModule> getBLImplicitModules ()
	{
		return Collections.unmodifiableCollection(BLImplicitModuleStore);
	}
	
	/**
	 * Gets the collection holding the bottom level implicit modules.
	 * <p>
	 * Note: This method returns an ACTUAL object located within the ACS. Therefore, any changes made to it COULD 
	 * have an effect on how the ACS handles the implicit modules. Therefore, it is advised that you be VERY careful when
	 * using the implicit module collection returned by this method.
	 * @return The implicit module collection.
	 */
	public ImplicitModuleCollection getBLImplicitModuleStore ()
	{
		return BLImplicitModuleStore;
	}
	
	/**
	 * Gets the collection of possible actions that can be performed.
	 * <p>
	 * Note: This method returns an ACTUAL object located within the ACS. Therefore, any changes made to it COULD 
	 * have an effect on how the ACS handles action-decision making. Therefore, it is advised that you be VERY careful
	 * when using the action collection returned by this method.
	 * @return The collection of possible actions.
	 */
	public ActionCollection getPossibleActions ()
	{
		return PossibleActions;
	}
	
	/**
	 * Gets all of the RER rules in the rule store.
	 * @return A collection of all of the RER rules in the ACS.
	 */
	public Collection <AbstractRule> getRERRules ()
	{
		return RERRuleStore.getRules();
	}
	
	/**
	 * Gets the subset of the rule store that holds the RER rules.
	 * <p>
	 * Note: This method returns an ACTUAL object located within the ACS. Therefore, any changes made to it COULD 
	 * have an effect on how the ACS handles the RER rules. Therefore, it is advised that you be VERY careful when
	 * using the rule collection returned by this method.
	 * @return The RER rule collection.
	 */
	public RuleCollection getRERRuleStore ()
	{
		return RERRuleStore;
	}
	
	/**
	 * Gets all of the IRL rules in the rule store.
	 * @return A collection of all of the IRL rules in the ACS.
	 */
	public Collection <AbstractRule> getIRLRules ()
	{
		return IRLRuleStore.getRules();
	}
	
	/**
	 * Gets the subset of the rule store that holds the IRL rules.
	 * <p>
	 * Note: This method returns an ACTUAL object located within the ACS. Therefore, any changes made to it COULD 
	 * have an effect on how the ACS handles the IRL rules. Therefore, it is advised that you be VERY careful when
	 * using the rule collection returned by this method.
	 * @return The IRL rule collection.
	 */
	public RuleCollection getIRLRuleStore ()
	{
		return IRLRuleStore;
	}
	
	/**
	 * Gets all of the fixed rules in the rule store.
	 * @return A collection of all of the fixed rules in the ACS.
	 */
	public Collection <AbstractRule> getFixedRules ()
	{
		return FRRuleStore.getRules();
	}
	
	/**
	 * Gets the subset of the rule store that holds the FR rules.
	 * <p>
	 * Note: This method returns an ACTUAL object located within the ACS. Therefore, any changes made to it COULD 
	 * have an effect on how the ACS handles the FR rules. Therefore, it is advised that you be VERY careful when
	 * using the rule collection returned by this method.
	 * @return The FR rule collection.
	 */
	public RuleCollection getFixedRuleStore ()
	{
		return FRRuleStore;
	}
	
	/**
	 * Adds an action to the list of possible actions and generates a match all rule for
	 * the specified action. If the action is already in the list of possible actions, 
	 * it will not be added.
	 * <p>
	 * This method should ONLY be called during initialization of the ACS.
	 * @param act The external action to add.
	 */
	public void addAction (AbstractAction act)
	{
		if(!PossibleActions.containsKey(act.getID()))
		{
			AbstractAction a = act.clone();
			PossibleActions.put(a.getID(),a);
			generateMatchAllRule(a);
		}
	}
	
	/**
	 * Adds a collection of actions to the list of possible actions and generates
	 * a match all rule for all the actions that were added. If any of the actions
	 * in the collection are already in the list of possible actions, they will not be added.
	 * <p>
	 * This method should ONLY be called during initialization of the ACS.
	 * @param acts The collection of actions to add.
	 */
	public void addActions (Collection <? extends AbstractAction> acts)
	{
		for(AbstractAction a : acts)
			addAction(a);
	}
	
	/**
	 * Adds an implicit module to the bottom level of the ACS. This method should ONLY
	 * be called during initialization of the ACS.
	 * <p>
	 * Note that if the specified implicit module is a neural network, it should be 
	 * pre-trained (if necessary) before it is added to the ACS.
	 * <p>
	 * If you are using goals or specialized working memory chunks, remember that the 
	 * input layer of the implicit module must contain nodes for any dimension-value pairs 
	 * within those chunks that differ from the sensory information space.
	 * <p>
	 * If any of the actions in the output are not specified within the collection of possible 
	 * actions, this method will add those actions to the collection of possible actions. In addition,
	 * if the input layer of the implicit module contains dimension-value pairs that are new (i.e. they have not
	 * been specified within the input space), this method will add those dimension-value pairs to the 
	 * input space.
	 * @param im The implicit module to add to the bottom level.
	 * @throws IllegalArgumentException If the specified implicit module contains and output chunk on the output
	 * layer that extends from something other than AbstractAction.
	 */
	public void addBLModule (AbstractImplicitModule im) throws IllegalArgumentException
	{
		for(AbstractOutputChunk o : im.Output.values())
			if(!(o instanceof AbstractAction))
				throw new IllegalArgumentException ("The implicit module contains " +
						"an output chunk on the output layer that extends from something " +
						"other than AbstractAction.");
		BLImplicitModuleStore.add(im);
		updateInputSpace(im.getInput());
		for(AbstractOutputChunk o : im.Output.values())
		{
			if(o.getID().equals("DO_NOTHING_EXTERNAL"))
				PossibleActions.USE_DO_NOTHING_EXTERNAL = true;
			else if(o.getID().equals("DO_NOTHING_WM"))
				PossibleActions.USE_DO_NOTHING_WM = true;
			else if(o.getID().equals("DO_NOTHING_GOAL"))
				PossibleActions.USE_DO_NOTHING_GOAL = true;
			else
				addAction((AbstractAction)o);
		}
	}
	
	/**
	 * Adds a collection of implicit modules to the bottom level of the ACS. This method should ONLY
	 * be called during initialization of the ACS.
	 * <p>
	 * Note that if any of the specified implicit modules is a neural network, it should be 
	 * pre-trained (if necessary) before it is added to the ACS.
	 * <p>
	 * If you are using goals or specialized working memory chunks, remember that the 
	 * input layer of the implicit modules must contain nodes for any dimension-value pairs 
	 * within those chunks that differ from the sensory information space.
	 * <p>
	 * If any of the actions in the output are not specified within the collection of possible 
	 * actions, this method will add those actions to the collection of possible actions. In addition,
	 * if the input layer of any of the implicit modules contain dimension-value pairs that are new (i.e. 
	 * they have not been specified within the input space), this method will add those 
	 * dimension-value pairs to the input space.
	 * @param ims The implicit modules to add to the bottom level.
	 */
	public void addBLModules (Collection <? extends AbstractImplicitModule> ims)
	{
		for(AbstractImplicitModule i : ims)
			addBLModule(i);
	}
	
	/**
	 * Adds a rule to the rule store. The rule will be placed in the appropriate rule store given its type
	 * (RER,IRL, or FR). If the rule is not of a recognized type, the rule will not be added.
	 * <p>
	 * If the specified rule is covered by any rules currently in the rule store, then the rule will be 
	 * added to the collection of child rules for that rule. If the specified rule is equal to any rule in the 
	 * rule store (or any of the children of the rules in the rule store) the rule will not be added.
	 * <p>
	 * If the action of the rule is not specified within the collection of possible actions, 
	 * this method will add that action to the collection of possible actions. In addition, if the rule contains
	 * dimension-value pairs in the condition of the rule that are new (i.e. they have not
	 * been specified within the input space), this method will add those dimension-value pairs to the 
	 * input space.
	 * <p>
	 * If you are using goals or specialized working memory chunks, remember that the 
	 * condition of a rule that you want to address those chunks must contain nodes for 
	 * any dimension-value pairs within those chunks that differ from the sensory information 
	 * space.
	 * @param R The rule to add.
	 */
	public void addRule (AbstractRule R)
	{
		if(R.getCondition() != null)
			updateInputSpace(R.getCondition().values());
		
		R.resetChildren();
		
		if(R.getAction().getID().equals("DO_NOTHING_EXTERNAL"))
			PossibleActions.USE_DO_NOTHING_EXTERNAL = true;
		else if(R.getAction().getID().equals("DO_NOTHING_WM"))
			PossibleActions.USE_DO_NOTHING_WM = true;
		else if(R.getAction().getID().equals("DO_NOTHING_GOAL"))
			PossibleActions.USE_DO_NOTHING_GOAL = true;
		else
			addAction(R.getAction());
		
		R.rAction = PossibleActions.get(R.getAction().getID());
		
		if(R instanceof AbstractIRLRule)
		{
			((AbstractIRLRule)R).resetVariations();
			((AbstractIRLRule)R).setMatchAll(getMatchAllRule(R.getAction()));
			IRLRuleStore.add(R);
		}
		else if(R instanceof RefineableRule)
		{
			((RefineableRule)R).resetVariations();
			((RefineableRule)R).setMatchAll(getMatchAllRule(R.getAction()));
			RERRuleStore.add(R);
		}
		else if(R instanceof AbstractFixedRule)
		{
			FRRuleStore.add(R);			
		}
	}
	
	/**
	 * Adds a collection of rules to the rule store. If any of the rules in the collection
	 * are covered by a rule currently in the rule store, that rule will be added to the
	 * collection of child rules for that rule. If the specified rule is equal to any rule in
	 * the rule store (or any of the children of the rules in the rule store) the rule will
	 * not be added.
	 * <p>
	 * If the action of a rule is not specified within the collection of possible actions, 
	 * this method will add that action to the collection of possible actions and generate
	 * a match all rule for the specified action. In addition, if a rule contains
	 * dimension-value pairs in the condition of the rule that are new (i.e. they have not
	 * been specified within the input space for the ACS), this method will add those
	 * dimension-value pairs to the input space of the ACS.
	 * <p>
	 * If you are using goals or specialized working memory chunks, remember that the 
	 * condition of a rule that you want to address those chunks must contain nodes for 
	 * any dimension-value pairs within those chunks that differ from the sensory information 
	 * space.
	 * @param R The collection of rules to add.
	 */
	public void addRules (Collection <? extends AbstractRule> R)
	{
		for(AbstractRule r : R)
			addRule(r);
	}
	
	/**
	 * Generates a match all rule associated with the specified action and adds it to the list
	 * of match all rules.
	 * @param act The action for which a match all rule is to be generated.
	 */
	private void generateMatchAllRule (AbstractAction act)
	{
		GeneralizedConditionChunk c = new GeneralizedConditionChunk ();
		for(Dimension d : InputSpace.values())
		{
			if(!d.containsKey(Drive.TypicalInputs.STIMULUS))
				c.put(d.getID(),d.clone());
		}
		MatchAllRules.put(act.getID(), new RefineableRule (c,act));
	}
	
	/**
	 * Gets the match all rule associated with the specified action. If the action does
	 * not have a match all rule, this method returns null. While it MAY be possible for
	 * this method to return null, it SHOULD never happen.
	 * @param act The action associated with the match all rule you wish to get.
	 * @return The match all rule for the specified action.
	 */
	private AbstractRule getMatchAllRule (AbstractAction act)
	{
		return MatchAllRules.get(act.getID());
	}
	
	/**
	 * Updates the positive or negative match statistics for the match all rule corresponding 
	 * to the chosen action.
	 * @param ChosenAction The chosen action.
	 * @param feedback The feedback.
	 */
	private void updateMatchAllStats(AbstractAction ChosenAction, double feedback, AbstractMatchCalculator MatchCalculator)
	{
		AbstractRule ma = MatchAllRules.get(ChosenAction.getID());
		ma.setFeedback(feedback);
		ma.updateMatchStatistics(MatchCalculator);
	}
	
	/**
	 * Performs the appropriate end of episode instructions for the ACS. This method is called by the
	 * CLARION class when its endEpisode method is called.
	 * @param Input A collection of various information to be used for ending the episode.
	 * @param TimeStamp The current time stamp.
	 */
	protected void endEpisode (DimensionValueCollection Input, long TimeStamp)
	{
		RERRuleStore.discountMatchStatistics(MATCH_DISCOUNT);
		IRLRuleStore.discountMatchStatistics(MATCH_DISCOUNT);
		FRRuleStore.discountMatchStatistics(MATCH_DISCOUNT);
		BLImplicitModuleStore.discountMatchStatistics();
		
		for(AbstractRule i : MatchAllRules.values())
		{
			i.setPM(i.getPM() * MATCH_DISCOUNT);
			i.setNM(i.getNM() * MATCH_DISCOUNT);
		}
	}

	/**
	 * Attaches the ACS to the specified CLARION agent.
	 * @param Agent The agent to wish this ACS will be attached.
	 */
	protected void attachSelfToAgent(CLARION Agent) {
		Agent.attachACS(this);
	}
	
	protected void updateInputSpace (Collection <Dimension> c)
	{
		for(Dimension d : c)
		{
			Dimension inputdim = InputSpace.get(d.getID());
			if(inputdim == null)
			{
				inputdim = d.clone();
				for(Value v : inputdim.values())
					v.setActivation(v.FULL_ACTIVATION_THRESHOLD);
				InputSpace.put(inputdim.getID(),inputdim);
			}
			else
			{
				for(Value v : d.values())
				{
					if(!inputdim.containsKey(v.getID()))
					{
						Value av = v.clone();
						av.setActivation(av.FULL_ACTIVATION_THRESHOLD);
						inputdim.put(av.getID(),av);
						
						//Update match-all rules
						for(AbstractRule ma : MatchAllRules.values())
						{
							GeneralizedConditionChunk cond = ma.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
							ma.resetMatchStatistics();
						}
						
						av = v.clone();
						av.setActivation(av.MINIMUM_ACTIVATION_THRESHOLD);
						
						//Update RER Rules (including children)
						Collection <AbstractRule> rc = RERRuleStore.getRules();						
						for(AbstractRule r : rc)
						{
							GeneralizedConditionChunk cond = r.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
						}
						Collection <AbstractRule> rcc = RERRuleStore.getChildren();
						for(AbstractRule r : rcc)
						{
							GeneralizedConditionChunk cond = r.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
						}
						RERRuleStore.clear();
						RERRuleStore.Children.clear();
						RERRuleStore.Variations.clear();
						RERRuleStore.addAll(rc);
						RERRuleStore.addAll(rcc);
						
						//Update IRL Rules (including children)
						rc = IRLRuleStore.getRules();						
						for(AbstractRule r : rc)
						{
							GeneralizedConditionChunk cond = r.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
						}
						rcc = IRLRuleStore.getChildren();
						for(AbstractRule r : rcc)
						{
							GeneralizedConditionChunk cond = r.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
						}
						IRLRuleStore.clear();
						IRLRuleStore.Children.clear();
						IRLRuleStore.Variations.clear();
						IRLRuleStore.addAll(rc);
						IRLRuleStore.addAll(rcc);
						
						//Update FR Rules (including children)
						rc = FRRuleStore.getRules();						
						for(AbstractRule r : rc)
						{
							GeneralizedConditionChunk cond = r.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
						}
						rcc = FRRuleStore.getChildren();
						for(AbstractRule r : rcc)
						{
							GeneralizedConditionChunk cond = r.getCondition();
							if(cond != null)
							{
								Dimension rd = cond.get(d.getID());
								if(rd != null)
								{
									rd.put(av.getID(), av.clone());
								}
							}
						}
						FRRuleStore.clear();
						FRRuleStore.Children.clear();
						FRRuleStore.addAll(rc);
						FRRuleStore.addAll(rcc);
					}
				}
			}
		}
	}
}
