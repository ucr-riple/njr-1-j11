package ch.zhaw.regularLanguages.evolution.candidates;

import java.lang.reflect.InvocationTargetException;

import ch.zhaw.regularLanguages.dfa.DeterministicFiniteAutomaton;
import ch.zhaw.regularLanguages.dfa.State;
import ch.zhaw.regularLanguages.dfa.transformation.TransformDFAToBricsAutomaton;
import ch.zhaw.regularLanguages.evolution.problems.EvolvingProblemSet;
import ch.zhaw.regularLanguages.evolution.problems.ProblemSet;
import ch.zhaw.regularLanguages.language.BooleanWrapper;
import ch.zhaw.regularLanguages.language.CharArrayWrapper;
import dk.brics.automaton.Automaton;

//TODO: Implement :)

public class DFAEvolutionCandidateWithProblemSet<AUTOMATON extends DeterministicFiniteAutomaton & Mutable> extends EvolutionCandidateWithProblemSet<AUTOMATON, dk.brics.automaton.Automaton, CharArrayWrapper, BooleanWrapper>{
	private char[] alphabet;
	
	public DFAEvolutionCandidateWithProblemSet(AUTOMATON obj,
			EvolvingProblemSet<CharArrayWrapper, BooleanWrapper> problemSet) {
		super(obj, problemSet);
		// TODO Auto-generated constructor stub
	}
	
	
	public DFAEvolutionCandidateWithProblemSet(Class<AUTOMATON> classTypeDef, char[] alphabet, AUTOMATON obj, EvolvingProblemSet<CharArrayWrapper, BooleanWrapper> problemSet){
		super(classTypeDef, problemSet);
		setObj(obj);
		this.alphabet = alphabet;
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
		
	@Override
	public Object cloneWithMutation() {
		AUTOMATON newClone = (AUTOMATON)this.getObj().clone();
		EvolvingProblemSet<CharArrayWrapper, BooleanWrapper> clonedPS = (EvolvingProblemSet<CharArrayWrapper, BooleanWrapper>)this.getProblemSet().clone();
		clonedPS.evolve(0.5);
		newClone.mutate(1);
		return new DFAEvolutionCandidateWithProblemSet(getClassTypeDef(), alphabet, newClone, clonedPS);
	}


	@Override
	public int fitness(EvolvingProblemSet<CharArrayWrapper, BooleanWrapper> problemSet) {
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
	public boolean checkValidity(Automaton reference) {
		return reference.equals(new TransformDFAToBricsAutomaton().transform(getObj()));
	}
}
