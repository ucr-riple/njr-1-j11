package com.github.users.schlabberdog.blocks.board.moves;

import com.github.users.schlabberdog.blocks.board.Board;

public interface IMove {

    public void apply(Board b);

    IMove mergeWith(IMove nextMove);
}
