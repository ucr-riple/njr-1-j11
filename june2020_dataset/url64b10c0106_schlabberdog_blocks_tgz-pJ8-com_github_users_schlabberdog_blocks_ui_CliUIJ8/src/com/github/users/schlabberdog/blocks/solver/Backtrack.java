package com.github.users.schlabberdog.blocks.solver;

import com.github.users.schlabberdog.blocks.board.BoardSave;
import com.github.users.schlabberdog.blocks.board.moves.IMove;

import java.util.ArrayList;

class Backtrack {
    public final BoardSave initialState;
    public final ArrayList<IMove> alternatives;
    public IMove selected = null;

    public Backtrack(BoardSave initialState, ArrayList<IMove> alternatives) {
        this.initialState = initialState;
        this.alternatives = alternatives;
    }
}
