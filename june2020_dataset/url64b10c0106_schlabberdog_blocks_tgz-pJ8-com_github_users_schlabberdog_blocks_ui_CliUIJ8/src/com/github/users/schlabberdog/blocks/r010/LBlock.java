package com.github.users.schlabberdog.blocks.r010;

import com.github.users.schlabberdog.blocks.board.Block;

import java.awt.*;

/* |XX
 */
public class LBlock extends Block {

    public LBlock() {
        super(2,1);
    }

    @Override
    public char getRepresentation() {
        return '=';
    }

    @Override
    public Color getColor() {
        return Color.yellow;
    }

    @Override
    public String toString() {
        return " [L]";
    }
}
