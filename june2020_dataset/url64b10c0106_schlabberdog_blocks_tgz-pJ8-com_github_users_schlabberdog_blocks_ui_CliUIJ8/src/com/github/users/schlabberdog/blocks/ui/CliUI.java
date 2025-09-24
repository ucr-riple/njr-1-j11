package com.github.users.schlabberdog.blocks.ui;

import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.board.BoardSave;
import com.github.users.schlabberdog.blocks.board.moves.IMove;
import com.github.users.schlabberdog.blocks.r010.R010Game;
import com.github.users.schlabberdog.blocks.solver.ISolverDelegate;
import com.github.users.schlabberdog.blocks.solver.Solver;

import java.util.List;

public class CliUI implements ISolverDelegate {

	private Solver solver;

	final Board replayBoard;
	final BoardSave initialState;

	private List<IMove> moves = null;

	public CliUI() {
		IGame game = new R010Game();
		Board board = game.getBoard();

		solver = new Solver(board,game.getChecker());

		replayBoard = board.copy();
		initialState = replayBoard.getSave();

		solver.setDelegate(this);

	}

	public void solve() {

		long start = System.currentTimeMillis();
		solver.solve();
		long end = System.currentTimeMillis();

		System.out.println("§§§");
		System.out.println("Best solution:");

		if(moves != null) {
			replayBoard.applySave(initialState);
			replayBoard.print(System.out);
			for (int i = 0; i < moves.size(); i++) {
				System.out.println(String.format("%4d: %s", i + 1, moves.get(i)));
				replayBoard.applyMove(moves.get(i));
				replayBoard.print(System.out);

			}
		}

		System.out.println("***");
		System.out.println("Worst Stack Depth: "+solver.getWorstStack());
		System.out.println("Solutions found: "+solver.getSolutionCount());
		System.out.println("Boards checked: "+solver.getCheckCount());
		System.out.println("Solution improved "+solver.getSolutionImprovedCount()+" times");
		System.out.println("Best solution: "+solver.getBestPathLength()+" steps");

		System.out.println("===");
		System.out.println(String.format("Time taken: %,.3f sec.",((end-start)/1000d)));
		System.out.println("Done");
	}

    public static void main(String[] args) {

		CliUI cli = new CliUI();
	    cli.solve();

    }

	private void setMoveList(List<IMove> moves) {
		this.moves = moves;
	}

	@Override
	public void solverStarted(Solver solver) {
		//nicht verwendet in CLI, da synchron
	}

	@Override
	public void solutionImproved(Solver solver, int solSize) {
		System.out.println("<(Better) Solution Found in: " + solSize + " moves> (tried: " + solver.getCheckCount() + ")(solutions: " + solver.getSolutionCount() + ")");

		List<IMove> steps = solver.getStepList();

		setMoveList(steps);

	}

	@Override
	public void solverDone(Solver solver) {
		//nicht verwendet bei CLI, da synchron
	}
}
