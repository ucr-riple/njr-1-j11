package com.github.users.schlabberdog.blocks.solver;

public interface ISolverDelegate {

	public void solverStarted(Solver solver);

	public void solutionImproved(Solver solver, int solSize);

	public void solverDone(Solver solver);
}
