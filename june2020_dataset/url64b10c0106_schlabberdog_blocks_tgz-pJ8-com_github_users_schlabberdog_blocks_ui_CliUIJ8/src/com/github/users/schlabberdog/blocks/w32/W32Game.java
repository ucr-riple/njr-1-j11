package com.github.users.schlabberdog.blocks.w32;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.mccs.Coord;
import com.github.users.schlabberdog.blocks.solver.ISolutionChecker;
import com.github.users.schlabberdog.blocks.ui.IGame;

public class W32Game implements IGame, ISolutionChecker {

	private final Block rblock = new RedBlock();

	@Override
	public Board getBoard() {
		Board board = new Board(4,6);
		board.insertBlockAt(new GreenBlock(),0,0);
		board.insertBlockAt(new GreenBlock(),0,2);
		board.insertBlockAt(new GreenBlock(),0,4);
		board.insertBlockAt(new GreenBlock(),3,0);
		board.insertBlockAt(new GreenBlock(),3,2);
		board.insertBlockAt(new YellowBlock(),1,2);
		board.insertBlockAt(new YellowBlock(),1,3);
		board.insertBlockAt(new BlueBlock(),1,4);
		board.insertBlockAt(new BlueBlock(),2,4);
		board.insertBlockAt(new BlueBlock(),3,4);
		board.insertBlockAt(new BlueBlock(),3,5);

		board.insertBlockAt(rblock,1,0);

		return board;
	}

	@Override
	public ISolutionChecker getChecker() {
		return this;
	}

    @Override
    public String getTitle() {
        return "Web-Rätsel w32";
    }

	@Override
	public boolean checkBoard(Board board) {
		Coord rcoord = board.getBlockCoord(rblock);
		//gelöst ist das ganze, wenn sich der rblock mit origin bei 1,4 befindet
		return (rcoord.x == 1 && rcoord.y == 4);
	}
}
