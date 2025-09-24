package com.github.users.schlabberdog.blocks.ui;


import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.solver.ISolutionChecker;

public interface IGame {

	public Board getBoard();

	public ISolutionChecker getChecker();

    String getTitle();
}
