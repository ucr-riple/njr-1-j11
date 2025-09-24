package solver.methods;

import solver.SolveStep;
import solver.Sudoku;

public interface SolveMethod {

	SolveStep getStep(Sudoku s);

	String getName();

	void apply(Sudoku s, SolveStep solveStep);


	void printInfo(SolveStep solveStep);


}
