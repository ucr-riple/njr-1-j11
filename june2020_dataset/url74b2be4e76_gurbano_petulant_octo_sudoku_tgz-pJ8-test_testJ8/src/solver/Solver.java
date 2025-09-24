package solver;

import java.util.ArrayList;

import solver.exceptions.UnsolvableException;
import solver.methods.BUG;
import solver.methods.HiddenSingleBlock;
import solver.methods.HiddenSingleCol;
import solver.methods.HiddenSingleRow;
import solver.methods.LockedCandidatesPointing;
import solver.methods.NakedSingle;
import solver.methods.SolveMethod;
import solver.methods.naked.n.NakedNCol;
import solver.methods.naked.n.NakedNRow;
import solver.methods.naked.pair.NakedPairBlock;
import solver.methods.naked.pair.NakedPairCol;
import solver.methods.naked.pair.NakedPairRow;

public class Solver {
	private static SolveMethod[] methods ={
		new NakedSingle(),
		new HiddenSingleCol(),
		new HiddenSingleRow(),
		new HiddenSingleBlock(),
		new LockedCandidatesPointing(),
		new NakedPairBlock(),
		new NakedPairCol(),
		new NakedPairRow(),
		new NakedNCol(),
		new NakedNRow(),
		new BUG()
		
	};


	public void solve(Sudoku s) throws UnsolvableException{
		while (!s.isSolved()){
			SolveStep ss = this.solveStep(s);
			ss.debug();
			ss.apply(s);
		}
	}

	public SolveStep solveStep(Sudoku s) throws UnsolvableException {
		SolveStep ss = null;
		for (int i = 0; i < methods.length; i++) {
			SolveMethod m = methods[i];
			ss =  m.getStep(s);
			if (ss != null)
				break;
		}
		if (ss==null)
			throw new UnsolvableException("Unsolvable");
		else
			return ss;

	}

	public void initializeCandidates(Sudoku s) {
		for (int r=0;r<s.getSize();r++){ //row
			for (int c=0;c<s.getSize();c++){ //cols
				s.getCell(r, c).setCandidates(new ArrayList<Integer>());
				if (s.getCell(r, c).isSolved()){
					
				}else{
					for (int n=1;n<=9;n++){
						if (
								!s.getRow(r).contains(n) 
								&&
								!s.getCol(c).contains(n)
								&&
								!s.getBlock(r,c).contains(n)
						){
							s.getCell(r, c).addCandidate(n);
						}
					}					
				}
			}
		}

	}

}
