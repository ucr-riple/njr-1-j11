package test;

import solver.SolveStep;
import solver.Solver;
import solver.Sudoku;
import solver.exceptions.InvalidSolutionException;
import solver.exceptions.UnsolvableException;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sudoku s = Sudoku.CIOSCA2();
		

		Solver solver = new Solver();
		solver.initializeCandidates(s);
		
		try {
			solver.solve(s);
		} catch (UnsolvableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		solver.initializeCandidates(s);
//		System.out.println( s.toString(false));
//		while(!s.isSolved()){
//			try {
//				SolveStep ss =  solver.solveStep(s);
//				ss.debug();
//				ss.apply(s);
////				solver.initializeCandidates(s);
//				
//			} catch (UnsolvableException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				break;
//			}
//		}
//		System.out.println( s.toString(true));
//		try {
//			s.checkSolution();
//		} catch (InvalidSolutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


	}

}
