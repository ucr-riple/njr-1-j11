package ch.zhaw.regularLanguages.evolution.candidates;

import java.lang.reflect.InvocationTargetException;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;
import ch.zhaw.regularLanguages.dfa.transformation.TransformDFAToBricsAutomaton;
import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;
import ch.zhaw.regularLanguages.language.BooleanWrapper;
import ch.zhaw.regularLanguages.language.CharArrayWrapper;
import dk.brics.automaton.Automaton;


public class DFAEvolutionCandidate<AUTOMATON extends DeterministicFiniteAutomaton & Mutable> extends EvolutionCandidate<AUTOMATON, ProblemSet<CharArrayWrapper, BooleanWrapper>, dk.brics.automaton.Automaton>{
	private char[] alphabet;
	
	public DFAEvolutionCandidate(Class<AUTOMATON> classTypeDef, char[] alphabet){
		super();
		this.alphabet = alphabet;
		setClassTypeDef(classTypeDef);
		setObj(initObj());
	}
	
	public DFAEvolutionCandidate(Class<AUTOMATON> classTypeDef, char[] alphabet, AUTOMATON obj){
		setClassTypeDef(classTypeDef);
		setObj(obj);
		this.alphabet = alphabet;
	}
	
	@Override
	public int fitness(ProblemSet<CharArrayWrapper, BooleanWrapper> problemSet) {
		AUTOMATON obj = getObj();
		
		int c = 0;
		int i = 0;
		for(CharArrayWrapper problem : problemSet.getProblemSet()){
			State state = null;
			state = obj.process(problem.getData());
			boolean isAccepting = obj.isAcceptingState(state);
			if(problemSet.checkSolution(problem, new BooleanWrapper(isAccepting))){
				c++;
			}
			i++;
		}
		return c;
	}

	@Override
	public AUTOMATON initObj() {
		Class<AUTOMATON> classTypeDef = getClassTypeDef();
		try {
			return classTypeDef.getConstructor(new Class[] { Class.forName("[C"), Integer.TYPE}).newInstance(new Object[] {alphabet, 2});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Object cloneWithMutation(){
		AUTOMATON newClone = (AUTOMATON)this.getObj().clone();
		newClone.mutate(1);
		return new DFAEvolutionCandidate(getClassTypeDef(), alphabet, newClone);
	}

	@Override
	public boolean checkValidity(Automaton reference) {
		return reference.equals(new TransformDFAToBricsAutomaton().transform(getObj()));
	}
	
}
