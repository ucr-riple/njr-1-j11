package com.github.users.schlabberdog.blocks.r010;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.board.moves.IMove;
import com.github.users.schlabberdog.blocks.board.moves.Move;
import com.github.users.schlabberdog.blocks.mccs.Coord;
import com.github.users.schlabberdog.blocks.mccs.Rect;

import java.awt.*;
import java.util.ArrayList;

/* |XX
 * | X
 */
public class RLBlock extends Block {

    public RLBlock() {
        super(2,2);
    }


    @Override
    public void printOntoMap(Coord pos,char[][] m) {
        char c = getRepresentation();
        m[pos.y][pos.x] = c;
        m[pos.y][pos.x+1] = c;
        m[pos.y+1][pos.x+1] = c;
    }

    @Override
    public void addAlts(Coord pos, Board board, ArrayList<IMove> alts) {
        //oben
        {
            if(pos.y > 0 && !board.intersectsWithRect(new Rect(pos.x,pos.y-1,2,1)))
                alts.add(new Move(this,0,-1,"RLBlock Up"));
        }
        //links
        {
            if(pos.x > 0 && board.getBlockCovering(pos.x - 1, pos.y) == null && board.getBlockCovering(pos.x, pos.y + 1) == null)
                alts.add(new Move(this,-1,0,"RLBlock Left"));
        }
        //rechts
        {
            if(pos.x+width < board.width && !board.intersectsWithRect(new Rect(pos.x+2,pos.y,1,2)))
                alts.add(new Move(this,1,0,"RLBlock Right"));
        }
        //unten
        {
            if(pos.y+height < board.height && board.getBlockCovering(pos.x, pos.y + 1) == null && board.getBlockCovering(pos.x + 1, pos.y + 2) == null)
                alts.add(new Move(this,0,1,"RLBlock Down"));
        }
    }

    @Override
    public String toString() {
        return "[RL]";
    }

    @Override
    public Color getColor() {
        return Color.cyan;
    }

    @Override
    public Rect[] getRectSet(Coord pos) {
        return new Rect[]{
                new Rect(pos.x  ,pos.y,2,1),
                new Rect(pos.x+1,pos.y,1,2)
        };
    }

    @Override
    public char getRepresentation() {
        return 'R';
    }

}
