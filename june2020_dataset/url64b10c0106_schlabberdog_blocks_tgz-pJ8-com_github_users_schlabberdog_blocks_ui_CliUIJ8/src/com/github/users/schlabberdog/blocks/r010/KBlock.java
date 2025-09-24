package com.github.users.schlabberdog.blocks.r010;

import com.github.users.schlabberdog.blocks.board.Block;

import java.awt.*;

/* |X
 * |X
 */
public class KBlock extends Block {

    public KBlock() {
        super(1,2);
    }

    @Override
    public char getRepresentation() {
        return '§';
    }

    @Override
    public Color getColor() {
        return Color.green;
    }

    @Override
    public String toString() {
        return " [K]";
    }
}
