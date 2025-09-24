package ch.zhaw.regularLanguages.evolution.problems;

import ch.zhaw.regularLanguages.helpers.Tuple;

public interface ProblemGenerator<P, S> {
	public Tuple<P,S> generateProblem();
}
