package com.github.users.schlabberdog.blocks.w32;

import com.github.users.schlabberdog.blocks.board.Block;


import java.awt.*;


public class RedBlock extends Block {

    public RedBlock() {
        super(2,2);
    }

    @Override
    public char getRepresentation() {
        return '*';
    }

    @Override
    public Color getColor() {
        return Color.red;
    }

    @Override
    public String toString() {
        return "[Red]";
    }

}
