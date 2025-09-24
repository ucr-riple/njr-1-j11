package com.github.users.schlabberdog.blocks.r010;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.board.ImBlock;
import com.github.users.schlabberdog.blocks.mccs.Coord;
import com.github.users.schlabberdog.blocks.solver.ISolutionChecker;
import com.github.users.schlabberdog.blocks.ui.IGame;

public class R010Game implements IGame, ISolutionChecker {

	private final Block kblock = new KBlock();


	@Override
	public Board getBoard() {
		Board board = new Board(6,7);
		board.insertBlockAt(new ImBlock(1, 3), 0, 0);
		board.insertBlockAt(new ImBlock(1, 1), 0, 6);
		board.insertBlockAt(new ImBlock(1, 3), 5, 0);
		board.insertBlockAt(new ImBlock(2, 2), 2, 0);

		board.insertBlockAt(kblock, 4, 0);

		board.insertBlockAt(new LBlock(), 2, 3);
		board.insertBlockAt(new LBlock(), 3, 4);
		board.insertBlockAt(new LUBlock(), 1, 2);
		board.insertBlockAt(new RUBlock(), 4, 3);

		board.insertBlockAt(new RLBlock(), 1, 5);

		return board;
	}

	@Override
	public ISolutionChecker getChecker() {
		return this;
	}

    @Override
    public String getTitle() {
        return "Rätsel 010";
    }

	@Override
	public boolean checkBoard(Board board) {
		//gelöst ist das ganze, wenn sich der kblock mit origin bei 1,0 befindet
		Coord kcoord = board.getBlockCoord(kblock);
		return kcoord.x == 1 && kcoord.y == 0;
	}
}
